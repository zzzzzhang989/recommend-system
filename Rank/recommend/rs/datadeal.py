import csv
import heapq

import numpy as np
import pandas as pd
import pickle
from gensim.models import TfidfModel
from collections import Counter
from functools import reduce


def get_movie_dataset():
    _tags = pd.read_csv("raw_data/tags.csv", usecols=range(1, 3)).dropna()
    tags = _tags.groupby("movieId").agg(list)

    # 加载电影列表数据集
    movies = pd.read_csv("raw_data/movies.csv", index_col="movieId")
    # 将类别词分开
    movies["genres"] = movies["genres"].apply(lambda x: x.split("|"))
    # 为每部电影匹配对应的标签数据，如果没有将会是NAN
    movies_index = set(movies.index) & set(tags.index)
    new_tags = tags.loc[list(movies_index)]
    ret = movies.join(new_tags)
    # 构建电影数据集，包含电影Id、电影名称、类别、标签四个字段
    # 如果电影没有标签数据，那么就替换为空列表
    # map(fun,可迭代对象)
    movie_dataset = pd.DataFrame(
        map(
            lambda x: (x[0], x[1], x[2], x[2] + x[3])
            if x[3] is not np.nan else (x[0], x[1], x[2], []),
            ret.itertuples())
        , columns=["movieId", "title", "genres", "tags"]
    )

    movie_dataset.set_index('movieId', inplace=True)

    # 除去 (no genres listed)
    for row in movie_dataset.itertuples():
        if row[3] is np.nan:
            continue
        for tag in row[3]:
            if tag == '(no genres listed)':
                movie_dataset.loc[row[0], 'tags'] = []
        for tag in row[2]:
            if tag == '(no genres listed)':
                movie_dataset.loc[row[0], 'genres'] = []
    # 测试
    '''
    test = False
    for row in movie_dataset.itertuples():
        if row[3] is np.nan:
            continue
        for tag in row[3]:
            if tag == '(no genres listed)':
                print('error 1')
                test = True
                break
        if test:
            break
    '''
    return movie_dataset


# 字典按照value排序
def getTopK(dict_, K=10):
    keys = list(dict_.keys())
    for key_i in range(len(keys)):
        for key_j in range(key_i + 1, len(keys)):
            if dict_[keys[key_i]] < dict_[keys[key_j]]:
                temp = keys[key_i]
                keys[key_i] = keys[key_j]
                keys[key_j] = temp

    resu_ = dict()
    for i in range(K):
        if len(keys) > i:
            resu_[keys[i]] = dict_[keys[i]]
    return resu_


def create_movie_profile(movie_dataset):
    '''
    使用tfidf，分析提取topn关键词
    :param movie_dataset:
    :return:
    '''
    dataset = movie_dataset["tags"].values

    from gensim.corpora import Dictionary
    # 根据数据集建立词袋，并统计词频，将所有词放入一个词典，使用索引进行获取
    dct = Dictionary(dataset)
    # 根据将每条数据，返回对应的词索引和词频
    corpus = [dct.doc2bow(line) for line in dataset]
    # 训练TF-IDF模型，即计算TF-IDF值
    model = TfidfModel(corpus)

    _movie_profile = []
    for i, data in enumerate(movie_dataset.itertuples()):
        mid = data[0]
        title = data[1]
        genres = data[2]
        vector = model[corpus[i]]
        movie_tags = sorted(vector, key=lambda x: x[1], reverse=True)[:5]
        topN_tags_weights = dict(map(lambda x: (dct[x[0]], x[1]), movie_tags))
        # 将类别词的添加进去，并设置权重值为1.0
        for g in genres:
            topN_tags_weights[g] = 1.0
        topN_tags_weights = getTopK(topN_tags_weights, 5)
        topN_tags = [i[0] for i in topN_tags_weights.items()]
        _movie_profile.append((mid, title, topN_tags, topN_tags_weights))

    movie_profile = pd.DataFrame(_movie_profile, columns=["movieId", "title", "profile", "weights"])
    movie_profile.set_index("movieId", inplace=True)
    return movie_profile


def create_user_profile(movie_profile):
    """
    构建用户画像
    1. 提取用户观看列表
    2. 根据观看列表和物品画像为用户匹配关键词并统计词频
    3. 根据词频排序，最多保留TOP-K个关键词，作为用户的标签
    :return:
    """
    watch_record = pd.read_csv('raw_data/ratings.csv', usecols=range(2),
                               dtype={'userId': np.int32, 'movieId': np.int32})
    watch_record = watch_record.groupby('userId').agg(list)
    user_profile = {}
    for user_id, movie_ids in watch_record.itertuples():
        record_movie_profile = movie_profile.loc[list(movie_ids)]
        counter = Counter(reduce(lambda x, y: list(x) + list(y), record_movie_profile['profile'].values))
        # 用户兴趣
        interest_words = counter.most_common(6)
        max_count = interest_words[0][1]
        interest_words = [(word, round(count / max_count, 4)) for word, count in interest_words]
        user_profile[user_id] = interest_words
    return user_profile


def load_obj(name):
    with open(name + '.pkl', 'rb') as f:
        return pickle.load(f)


def get_movie_profile():
    movie_profiles = create_movie_profile(get_movie_dataset())['weights']
    movie_tag = [[], [], [], [], [], []]
    tag_dict = load_obj('tag_dict')
    count = 0
    for index, data in movie_profiles.iteritems():
        index = 0
        for index, tag in enumerate(data.keys()):
            movie_tag[index].append(int(tag_dict[tag]))
        if len(data.keys()) == 0:
            index = -1
        for index2 in range(index + 1, 5):
            movie_tag[index2].append(0)
        count = count + 1
    print(count)
    print(len(movie_tag[0]))
    data = pd.DataFrame(columns=['movieId', 'tag1', 'tag2', 'tag3', 'tag4', 'tag5'], dtype=int)
    score_avg = pd.read_csv('profile/sorted_movie_avg.csv')
    avg_dict = {}
    for row in score_avg.itertuples():
        avg_dict[row[1]] = row[2]
    data['movieId'] = list(movie_profiles.index)
    avg_list = []
    for movieId in data['movieId']:
        if movieId in list(avg_dict.keys()):
            avg_list.append(float(avg_dict.get(movieId)))
        else:
            avg_list.append(0.00)
    data['score_avg'] = avg_list
    data['tag1'] = movie_tag[0]
    data['tag2'] = movie_tag[1]
    data['tag3'] = movie_tag[2]
    data['tag4'] = movie_tag[3]
    data['tag5'] = movie_tag[4]
    print(len(data))
    print(len(score_avg))
    data.to_csv('profile_movie.csv', index=False)


def get_user_profile():
    movie_profile = create_movie_profile(get_movie_dataset())
    user_profile = create_user_profile(movie_profile)
    del movie_profile
    user_tag = [[], [], [], [], [], []]
    tag_dict = load_obj('tag_dict')
    for user_id in user_profile.keys():
        interest_words = user_profile.get(user_id)
        index = 0
        for index, interest_word in enumerate(interest_words):
            user_tag[index].append(int(tag_dict[interest_word[0]]))
        for index2 in range(index + 1, 5):
            user_tag[index2].append(0)

    data = pd.DataFrame(columns=['userId', 'tag1', 'tag2', 'tag3', 'tag4', 'tag5'], dtype=int)
    data['userId'] = list(user_profile.keys())

    score_avg = pd.read_csv('profile/sorted_user_avg.csv')
    avg_dict = {}
    for row in score_avg.itertuples():
        avg_dict[row[1]] = row[2]
    avg_list = []
    for userId in data['userId']:
        if userId in list(avg_dict.keys()):
            avg_list.append(float(avg_dict.get(userId)))
        else:
            avg_list.append(0.00)
    data['score_avg'] = avg_list
    data['tag1'] = user_tag[0]
    data['tag2'] = user_tag[1]
    data['tag3'] = user_tag[2]
    data['tag4'] = user_tag[3]
    data['tag5'] = user_tag[4]
    data.to_csv('profile_user.csv', index=False)


def save_obj(obj, name):
    with open(name + '.pkl', 'wb') as f:
        pickle.dump(obj, f, pickle.HIGHEST_PROTOCOL)


def generate_tag_dictionary():
    tags = pd.read_csv('raw_data/tags.csv')
    genres = pd.read_csv('raw_data/movies.csv')
    count = 1
    mydict = {}
    for tag in tags['tag']:
        if tag not in list(mydict.keys()):
            mydict[tag] = count
            count = count + 1

    for genre_str in genres['genres']:
        if genre_str == '(no genres listed)':
            print('(no genres listed)')
            continue
        for genre in genre_str.split('|'):
            if genre not in list(mydict.keys()):
                mydict[genre] = count
                count = count + 1
    print(count - 1)
    save_obj(mydict, 'tag_dict')


def add_labels(dataset):
    recommend_list = pd.read_csv('data/recall/recommend_movies.csv')
    recommend_list.info()
    recommend_dict = {}
    for user_id, movie_ids in recommend_list.values.tolist():
        recommend_dict[user_id] = list(map(int, movie_ids.split('|')))

    label_list = []
    for user_id, movie_id, rating in dataset.values:
        if user_id in recommend_dict.keys() and movie_id in recommend_dict[user_id]:
            label_list.append(1)
        else:
            label_list.append(0)

    dataset['label'] = label_list


def add_tags(dataset):
    user_data = pd.read_csv('data/profile/profile_user.csv', index_col="userId")
    movie_data = pd.read_csv('data/profile/profile_movie.csv', index_col="movieId")
    user_tag = [[], [], [], [], [], [], []]
    movie_tag = [[], [], [], [], [], [], []]

    for row in dataset.itertuples():
        user_tag[0].append(user_data.loc[row[1], 'tag1'])
        user_tag[1].append(user_data.loc[row[1], 'tag2'])
        user_tag[2].append(user_data.loc[row[1], 'tag3'])
        user_tag[3].append(user_data.loc[row[1], 'tag4'])
        user_tag[4].append(user_data.loc[row[1], 'tag5'])
        user_tag[5].append(user_data.loc[row[1], 'score_avg'])

        movie_tag[0].append(movie_data.loc[row[2], 'tag1'])
        movie_tag[1].append(movie_data.loc[row[2], 'tag2'])
        movie_tag[2].append(movie_data.loc[row[2], 'tag3'])
        movie_tag[3].append(movie_data.loc[row[2], 'tag4'])
        movie_tag[4].append(movie_data.loc[row[2], 'tag5'])
        movie_tag[5].append(movie_data.loc[row[2], 'score_avg'])

    dataset['userTag1'] = user_tag[0]
    dataset['userTag2'] = user_tag[1]
    dataset['userTag3'] = user_tag[2]
    dataset['userTag4'] = user_tag[3]
    dataset['userTag5'] = user_tag[4]
    dataset['user_avg_score'] = user_tag[5]

    dataset['movieTag1'] = movie_tag[0]
    dataset['movieTag2'] = movie_tag[1]
    dataset['movieTag3'] = movie_tag[2]
    dataset['movieTag4'] = movie_tag[3]
    dataset['movieTag5'] = movie_tag[4]
    dataset['movie_avg_score'] = movie_tag[5]


def merge_all():
    dataset = {}
    df1 = pd.read_csv('1.csv', header=None, delimiter='&', names=['userId', 'movieIdList'], dtype={'movieIdList': str})
    for row in df1.itertuples():
        items = row[2].strip('|').split('|')
        if row[1] not in list(dataset.keys()):
            movieIdList = []
            for item in items:
                movieIdList.append(int(item.split(',')[0]))
            dataset[row[1]] = movieIdList

    del df1
    df2 = pd.read_csv('2.csv', dtype={'movieIdList': str})
    for row in df2.itertuples():
        items = row[2].split('|')
        movieIdList = []
        for item in items:
            movieIdList.append(int(item.split(',')[0]))
        if row[1] not in list(dataset.keys()):
            dataset[row[1]] = movieIdList
        else:
            dataset[row[1]] = sorted(list(set(dataset[row[1]] + movieIdList)))

    del df2
    df3 = pd.read_csv('3.csv', dtype={'movieId': str})
    for row in df3.itertuples():
        items = row[2].split('|')
        movieIdList = []
        for item in items:
            movieIdList.append(int(item.split(',')[0]))
        if row[1] not in list(dataset.keys()):
            dataset[row[1]] = movieIdList
        else:
            dataset[row[1]] = sorted(list(set(dataset[row[1]] + movieIdList)))
    del df3
    df4 = pd.read_csv('4.csv', dtype={'movieIdList': str})
    for row in df4.itertuples():
        items = row[2].strip('|').split('|')
        movieIdList = []
        for item in items:
            movieIdList.append(int(item.split(',')[0]))
        if row[1] not in list(dataset.keys()):
            dataset[row[1]] = movieIdList
        else:
            dataset[row[1]] = sorted(list(set(dataset[row[1]] + movieIdList)))

    del df4
    f = open('5.csv', 'w', newline='', encoding="utf-8")
    csv_writer = csv.writer(f)
    csv_writer.writerow(['userId', 'movieIdList'])
    for userId in dataset.keys():
        movieIdList_string = ''
        for movieId in dataset.get(userId):
            movieIdList_string += str(movieId) + '|'
        movieIdList_string = movieIdList_string.strip('|')
        csv_writer.writerow([userId, movieIdList_string])


def add_header(filename):
    return pd.read_csv(filename, usecols=[0, 1, 2], header=None, names=['userId', 'movieId', 'rating'])


def remove_rating():
    train = pd.read_csv('train.csv')
    del train['userId']
    del train['movieId']
    del train['rating']
    train.to_csv('train.csv', index=False)

    valid = pd.read_csv('valid.csv')
    del valid['userId']
    del valid['movieId']
    del valid['rating']
    valid.to_csv('valid.csv', index=False)

    test = pd.read_csv('test.csv')
    del test['userId']
    del test['movieId']
    del test['rating']
    test.to_csv('test.csv', index=False)


def get_recommend_result():
    import tensorflow as tf
    model = tf.keras.models.load_model('0')
    user_profile = pd.read_csv('data/profile/profile_user.csv', index_col="userId")
    movie_profile = pd.read_csv('data/profile/profile_movie.csv')
    f = open('final_recommend.csv', 'w', newline='', encoding="utf-8")
    csv_writer = csv.writer(f)
    csv_writer.writerow(['userId', 'recommendList'])
    for userId in user_profile.index:
        x_inputs = []
        for index, movieId in enumerate(movie_profile['movieId']):
            x_input = [user_profile.loc[userId, 'score_avg'], movie_profile.loc[index, 'score_avg'],
                       user_profile.loc[userId, 'tag1'] / 73051,
                       user_profile.loc[userId, 'tag2'] / 73051,
                       user_profile.loc[userId, 'tag3'] / 73051,
                       user_profile.loc[userId, 'tag4'] / 73051,
                       user_profile.loc[userId, 'tag5'] / 73051,
                       movie_profile.loc[index, 'tag1'] / 73051,
                       movie_profile.loc[index, 'tag2'] / 73051,
                       movie_profile.loc[index, 'tag3'] / 73051,
                       movie_profile.loc[index, 'tag4'] / 73051,
                       movie_profile.loc[index, 'tag5'] / 73051]
            x_inputs.append(x_input)
        predictions = model.predict(np.array(x_inputs)).flatten()
        indexes = heapq.nlargest(5, range(len(predictions)), predictions.__getitem__)
        movieIdList = ''
        for index in indexes:
            movieIdList += str(movie_profile.loc[index, 'movieId']) + '|'
        csv_writer.writerow([userId, movieIdList.strip('|')])


def get_20_result():
    import tensorflow as tf
    dataset = pd.read_csv('raw_data/test_ratings.csv')
    # 测试集
    test = pd.read_csv('test1.csv')
    test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
          'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
        test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
              'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
    X_test = test[['user_avg_score', 'movie_avg_score',
                   'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
                   'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
    new_model = tf.keras.models.load_model('models')
    predictions = new_model.predict(X_test).flatten()
    dataset['prediction'] = predictions
    dataset.to_csv('test_prediction_result.csv', index=False)


get_20_result()

# get_movie_profile()
# get_user_profile()


# dataset = pd.read_csv('raw_data/train_ratings_80.csv')
# add_labels(dataset)
# add_tags(dataset)
# dataset.to_csv('train.csv', index=False)
#
# dataset = pd.read_csv('raw_data/train_ratings_20.csv')
# add_labels(dataset)
# add_tags(dataset)
# dataset.to_csv('valid.csv', index=False)
#
# dataset = pd.read_csv('raw_data/test_ratings.csv')
# add_labels(dataset)
# add_tags(dataset)
# dataset.to_csv('test.csv', index=False)
#
# remove_rating()