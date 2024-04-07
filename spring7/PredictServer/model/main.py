# package import
# installation: fastapi, uvicorn
from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel # DTO처럼 정보 담을 모델
from starlette.responses import JSONResponse # JSON으로 반환

import pickle
import numpy as np
import pandas as pd

## Model 생성 - 값을 받을 파라메터들을 가진 모델
class Item(BaseModel):
    petalLength: float
    petalWidth: float
    sepalLength: float
    sepalWidth: float

## app 개발
app = FastAPI()
# 경로와 요청 방법 설정
@app.post(path="/", status_code=201)
def myiris(item: Item):
    # 1) 저장되어있는 pickle 파일 로딩
    with open('data.pickle', 'rb') as f:
        model = pickle.load(f)

        # 2) 받은 item 객체를 딕셔너리 타입으로
        dicted = dict(item)  

        # 3) 낱개의 데이터로 받기
        petalLength = dicted['petalLength']
        petalWidth = dicted['petalWidth']
        sepalLength = dicted['sepalLength']
        sepalWidth = dicted['sepalWidth']

        target = ['setosa', 'versicolor', 'virginica']
        # 4) 2차원 넘파이 배열로
        ary = np.array([[sepalLength, sepalWidth, petalLength, petalWidth]])

        # 5) 예측하기
        pred = model.predict(ary)
        result = {'predict_result' : target[pred[0]]}

        print('========= 예측 반환값 ==========', pred)
        print('========= 예측 결과값 ==========', result)

    return JSONResponse(result)

# 포트번호 할당 및 uvicorn 서버 구동
if __name__ == '__main__':
    uvicorn.run(app, host="127.0.0.1", port=8000)

# 유비콘 서버 구동 명령문 : uvicorn main:app --reload