import requests
import zipfile
from io import BytesIO

# Opendart API 엔드포인트 URL
url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "36509c5feed29d3468703dc40d3a64536d7cf944"

# 요청 파라미터 설정
params = {
    "crtfc_key": api_key,
    "corp_code": "00126308",
    "bsns_year": "2023",
    "reprt_code" : "11012",
    "fs_div" : "OFS"
}

response = requests.get(url, params=params)

if response.status_code == 200:
    company_info = response.json()
    print(company_info)
else:
    print("API 호출 실패:", response.status_code)