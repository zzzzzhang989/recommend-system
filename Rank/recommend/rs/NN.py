import pandas as pd
from tensorflow.keras import layers, models

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

# 定义模型  ReLU(x)=max(0, x) Sigmoid(x)= 1/(1+e^-x)
model = models.Sequential()
model.add(layers.Dense(128, activation="relu", input_shape=(12,)))
model.add(layers.Dense(64, activation="relu"))
model.add(layers.Dense(1, activation="sigmoid"))

# 编译模型
model.compile(optimizer="adam", loss="binary_crossentropy", metrics=['accuracy', 'AUC'])

# 训练模型
history = model.fit(X_train, y_train, epochs=5, batch_size=32, validation_data=(X_valid, y_valid))
model.summary()

# 保存模型
model.save('models/NN/2', save_format='tf')
