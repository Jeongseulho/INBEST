import requests
import zipfile
from io import BytesIO

import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()

# Opendart API 엔드포인트 URL
url = "https://opendart.fss.or.kr/api/company.json"  # JSON 형식의 기업 개황 정보를 가져오는 엔드포인트
api_key = "36509c5feed29d3468703dc40d3a64536d7cf944"

# 요청 파라미터 설정
params = {
    "crtfc_key": api_key,
    "corp_code": "00264529",  # 가져올 기업의 고유번호 (corp_code)를 여기에 넣으세요
}

response = requests.get(url, params=params)

if response.status_code == 200:
    company_info = response.json()
    print(company_info)
else:
    print("API 호출 실패:", response.status_code)