# 测试集
import joblib
import pandas as pd

test = pd.read_csv('data/test/test.csv')
test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
      'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
          'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_test = test[['user_avg_score', 'movie_avg_score',
               'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
               'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
y_test = test['label'].values

from sklearn import metrics

lr = joblib.load('models/LR/model.pkl')

y_hat = lr.predict(X_test)
print(y_hat)
accuracy = metrics.accuracy_score(y_test, y_hat)
print("Logistic Regression模型特实际正确率：%.3f" % accuracy)
