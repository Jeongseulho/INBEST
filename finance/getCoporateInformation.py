import requests
import zipfile
from io import BytesIO

# Opendart API 엔드포인트 URL
url = "https://opendart.fss.or.kr/api/corpCode.xml"

api_key = "ac30ceeeb8ac94447e5e065876a5783d79e5b3ec"

# 요청 파라미터 설정
params = {
    "crtfc_key": api_key,
}


# API 호출
response = requests.get(url, params=params)

# HTTP 상태 코드 확인
if response.status_code == 200:
    # 바이너리 데이터를 메모리에 로드
    zip_data = BytesIO(response.content)

    # 압축 해제
    with zipfile.ZipFile(zip_data, 'r') as zip_file:
        # 압축 해제된 XML 파일을 읽음
        with zip_file.open(zip_file.namelist()[0]) as xml_file:
            xml_data = xml_file.read()

    # XML 파싱 라이브러리를 사용하여 고유번호 정보 추출
    from xml.etree import ElementTree as ET
    root = ET.fromstring(xml_data)
    corp_codes = root.findall(".//list")

    # 상장 기업 정보를 저장할 리스트
    listed_companies = []

    # 고유번호 출력 (사업자등록번호 또는 공시대상회사번호)
    for corp_code in corp_codes:
        corp_name = corp_code.find('corp_name').text
        stock_code = corp_code.find('stock_code').text

        # 상장된 기업만 파싱
        if len(stock_code) != 1:
                listed_companies.append({
                    "corp_name": corp_name,
                    "corp_code": corp_code.find('corp_code').text,
                    "stock_code": stock_code
                })
    for company in listed_companies:
         print(f"회사명 : {company['corp_name']}, 고유번호 : {company['corp_code']}, 주식코드 : {company['stock_code']} ")
    
    print(len(listed_companies))


else:
    print("API 호출 실패:", response.status_code)
