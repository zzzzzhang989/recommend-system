import numpy as np
import pandas as pd
import tensorflow as tf
import json
import requests

# 测试集
test = pd.read_csv('test.csv')
test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
      'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] = \
    test[['userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
          'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']] / 73051
X_test = test[['user_avg_score', 'movie_avg_score',
               'userTag1', 'userTag2', 'userTag3', 'userTag4', 'userTag5',
               'movieTag1', 'movieTag2', 'movieTag3', 'movieTag4', 'movieTag5']].values[0:62541]

from time import time

time1 = time()

headers = {'content-type': 'application/json'}

data = json.dumps({"signature_name": "serving_default", "instances": X_test.tolist()})

json_response = requests.post(
    url='http://localhost:8501/v1/models/finalmodel:predict',
    data=data, headers=headers
)

time2 = time()
print(time2 - time1)
pred = np.array(json.loads(json_response.text)['predictions'])
print(pred)
