# 导入必要的几个包
import joblib
import pandas as pd
from sklearn.linear_model import LogisticRegression

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

# 逻辑回归模型，C=1e5表示目标函数。
lr = LogisticRegression(C=1e5)
lr = lr.fit(X_train, y_train)

print("Logistic Regression模型训练集的准确率：%.3f" % lr.score(X_train, y_train))
print("Logistic Regression模型验证集的准确率：%.3f" % lr.score(X_valid, y_valid))

joblib.dump(lr, 'models/LR/model.pkl')
