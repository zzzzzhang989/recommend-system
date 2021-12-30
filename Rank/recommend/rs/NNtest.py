import numpy as np
import pandas as pd
import sklearn.metrics
import tensorflow as tf

# 测试集
test = pd.read_csv('data/test/test.csv')
test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
      'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
          'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_test = test[['user_avg_score', 'movie_avg_score',
               'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
               'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values
y_test = test['label'].values

new_model = tf.keras.models.load_model('models/NN/2')

test_loss, test_accuracy, test_auc = new_model.evaluate(X_test, y_test)
print('\n\nTest Loss {}, Test Accuracy {}, Test AUC {}'.format(test_loss, test_accuracy, test_auc))