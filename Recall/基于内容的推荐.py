"""
基于内容的推荐
"""
import codecs
import csv
import json
from collections import Counter
from functools import reduce
import numpy as np
import pandas as pd
import warnings

warnings.filterwarnings('ignore')  # 警告扰人，手动封存
from gensim.corpora import Dictionary
from gensim.models import TfidfModel


def get_movie_dataset():
    # 加载电影标签
    _tags = pd.read_csv('./data/tags.csv', usecols=range(1, 3)).dropna()
    tags = _tags.groupby('movieId').agg(list)
    # 加载电影信息
    movies = pd.read_csv('./data/movies.csv', index_col='movieId')
    # 将类别词分开
    movies['genres'] = movies['genres'].apply(lambda x: x.split('|'))
    # 为每部电影匹配标签数据
    movies_index = set(movies.index) & set(tags.index)
    new_tags = tags.loc[list(movies_index)]
    ret = movies.join(new_tags)
    # 构建电影数据集
    movie_dataset = pd.DataFrame(map(
        lambda x: (x[0], x[1], x[2], x[2] + x[3]) if x[3] is not np.nan else (x[0], x[1], x[2], []),
        ret.itertuples()),
        columns=['movieId', 'title', 'genres', 'tags'])
    movie_dataset.set_index('movieId', inplace=True)
    return movie_dataset


def create_movie_profile(dataset):
    """
    使用TF-IDF提取TOP-N关键词，构建电影画像
    :param dataset: 电影数据集
    :return:
    """
    tags = dataset['tags'].values
    # 建立词袋并统计词频
    bags = Dictionary(tags)
    # 返回每条数据对应的索引和词频
    corpus = [bags.doc2bow(line) for line in tags]
    # 训练TF-IDF模型
    model = TfidfModel(corpus)

    _movie_profile = []
    for i, data in enumerate(dataset.itertuples()):
        movie_id = data[0]
        title = data[1]
        genres = data[2]
        vector = model[corpus[i]]
        # 按照TF-IDF值获取TOP-N关键词
        top_n_movie_tags = sorted(vector, key=lambda x: x[1], reverse=True)[:30]

        # 根据关键词提取对应的名称
        top_n_tags_weights = dict(map(lambda x: (bags[x[0]], x[1]), top_n_movie_tags))
        # 将类别词添加进去，并设置权重为1.0
        for g in genres:
            top_n_tags_weights[g] = 1.0
        top_n_tags = [item[0] for item in top_n_tags_weights.items()]
        _movie_profile.append((movie_id, title, top_n_tags, top_n_tags_weights))
    movie_profile = pd.DataFrame(_movie_profile, columns=['movieId', 'title', 'profile', 'weights'])
    movie_profile.set_index('movieId', inplace=True)
    return movie_profile


def create_inverted_table(profile):
    """
    建立tag-物品的倒排索引
    :param profile: 电影画像
    :return:
    """
    inverted_table = {}
    for movie_id, weights in profile['weights'].iteritems():
        for tag, weight in weights.items():
            temp = inverted_table.get(tag, [])
            temp.append((movie_id, weight))
            inverted_table.setdefault(tag, temp)
    return inverted_table


def create_user_profile(movie_profile):
    """
    构建用户画像
    1. 提取用户观看列表
    2. 根据观看列表和物品画像为用户匹配关键词并统计词频
    3. 根据词频排序，最多保留TOP-K个关键词，作为用户的标签
    :return:
    """
    watch_record = pd.read_csv('./data/train_ratings_80.csv', usecols=range(2),
                               dtype={'userId': np.int32, 'movieId': np.int32})
    watch_record = watch_record.groupby('userId').agg(list)
    user_profile = {}
    for user_id, movie_ids in watch_record.itertuples():
        record_movie_profile = movie_profile.loc[list(movie_ids)]
        counter = Counter(reduce(lambda x, y: list(x) + list(y), record_movie_profile['profile'].values))
        # 用户兴趣
        interest_words = counter.most_common(50)
        max_count = interest_words[0][1]
        interest_words = [(word, round(count / max_count, 4)) for word, count in interest_words]
        user_profile[user_id] = interest_words
    return user_profile


def get_recommendation(user_profile, inverted_table):
    """
    获取所有用户的电影推荐列表
    :param user_profile: 用户画像
    :param inverted_table: tag-物品的倒排索引
    :return:
    """
    recommendation = {}
    for user_id, interest_words in user_profile.items():
        result = {}
        for interest_word, interest_weight in interest_words:
            related_movies = inverted_table[interest_word]
            for movie_id, related_weight in related_movies:
                temp = result.get(movie_id, [])
                temp.append(interest_weight)  # 只考虑用户的兴趣程度
                # temp.append(related_weight)  # 只考虑兴趣词与电影的关联程度
                # temp.append(interest_weight * related_weight)  # 二者都考虑
                result.setdefault(movie_id, temp)
        ret = map(lambda x: (x[0], sum(x[1])), result.items())
        ret = sorted(ret, key=lambda x: x[1], reverse=True)[:20]
        recommendation[user_id] = ret
        print(user_id, ret)
    return recommendation


def save_dict_into_csv(_dict, outfile_path):
    f = open(outfile_path, 'w', newline='', encoding="utf-8")
    csv_writer = csv.writer(f)
    csv_writer.writerow(['userId', 'movieIdList'])
    for key in _dict.keys():
        movieIdList = ''
        for movie in _dict.get(key):
            movieIdList += str(movie[0]) + '|'
        movieIdList.strip('|')
        csv_writer.writerow([key, movieIdList])


if __name__ == '__main__':
    movie_dataset = get_movie_dataset()
    movie_profile = create_movie_profile(movie_dataset)
    inverted_table = create_inverted_table(movie_profile)
    user_profile = create_user_profile(movie_profile)
    recommendation = get_recommendation(user_profile, inverted_table)

    save_dict_into_csv(recommendation, './recommendMovies1.csv')
