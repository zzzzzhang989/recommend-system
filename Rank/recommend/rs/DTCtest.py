import numpy as np
import pandas as pd
import sklearn.metrics
import tensorflow as tf

# 测试集
from sklearn.metrics import accuracy_score

test = pd.read_csv('data/test/test.csv')
test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
      'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
          'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_test = test[['user_avg_score', 'movie_avg_score',
               'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
               'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
y_test = test['label'].values


def grabTree(filename):
    import pickle
    fr = open(filename, 'rb')
    return pickle.load(fr)


dtc = grabTree('models/DTC/model.dot')

y_predict = dtc.predict(X_test)

# 打印预测结果
print('===================预测值=======================')
print(y_predict)
# 打印真实值
print('===================真实值=======================')
realz = np.array(y_test).ravel()
print(realz)
Accuracy = accuracy_score(realz, y_predict)
print('准确率为：{:.2f}%'.format(Accuracy * 100))
