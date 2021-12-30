import pandas as pd
# 算法库
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import GridSearchCV

# 评估用到的库
from sklearn.metrics import accuracy_score
from sklearn.metrics import f1_score
from sklearn.metrics import recall_score
from sklearn.metrics import precision_score
# 预测

import numpy as np

# 训练集
train = pd.read_csv('data/train/train.csv')
train[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
       'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    train[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
           'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_train = train[['user_avg_score', 'movie_avg_score',
                 'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
                 'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
y_train = train['label'].values
print('X_train', X_train.shape)
print('y_train', y_train.shape)

# 验证集
valid = pd.read_csv('data/train/valid.csv')
valid[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
       'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    valid[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
           'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_valid = valid[['user_avg_score', 'movie_avg_score',
                 'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
                 'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
y_valid = valid['label'].values

# 训练模型
dtc = DecisionTreeClassifier()
dtc.fit(X_train, y_train)
y_predict = dtc.predict(X_valid)

# 模型评分：准确率，查全率，查准率，F1得分
accuracyScore = accuracy_score(y_valid, y_predict)
recallScore = recall_score(y_valid, y_predict)
precisionScore = precision_score(y_valid, y_predict)
f1Score = f1_score(y_valid, y_predict)
print("DecisionTreeClassifier Results")
print("Accuracy      :", accuracyScore)
print("Recall        :", recallScore)
print("Precision     :", precisionScore)
print("F1 Score      :", f1Score)

param = {'max_depth': [1, 3, 5, 7, 9]}
# 采用网格搜索进行参数调优
gsearch = GridSearchCV(estimator=dtc, param_grid=param, cv=5, scoring='f1')
gsearch.fit(X=X_train, y=y_train)
print("最优参数：{}".format(gsearch.best_params_))
print("最优模型：{}".format((gsearch.best_estimator_)))
print("模型最高分：{:.3f}".format(gsearch.score(X_valid, y_valid)))

# 选择最优模型进行预测
dtc = DecisionTreeClassifier(class_weight=None, criterion='gini', max_depth=9,
                             max_features=None, max_leaf_nodes=None,
                             min_samples_leaf=1, min_samples_split=2,
                             min_weight_fraction_leaf=0.0, random_state=None,
                             splitter='random')
dtc.fit(X_train, y_train)


def storeTree(inputTree, filename):
    import pickle
    fw = open(filename, 'wb')
    pickle.dump(inputTree, fw)
    fw.close()


def grabTree(filename):
    import pickle
    fr = open(filename, 'rb')
    return pickle.load(fr)


storeTree(dtc, 'models/DTC/model.dot')
# dtc = grabTree('model.dot')

y_predict = dtc.predict(X_valid)

# 打印预测结果
print('===================预测值=======================')
print(y_predict)
# 打印真实值
print('===================真实值=======================')
realz = np.array(y_valid).ravel()
print(realz)
Accuracy = accuracy_score(realz, y_predict)
print('准确率为：{:.2f}%'.format(Accuracy * 100))
