import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "crawling.settings")

import django
django.setup()

from financialData.models import FinancialProduct
from collections import defaultdict


api_url = "http://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json"
auth_key = "0ac9cc1bfc28a2d6c6babfdd6cff77e3"

# API 요청을 위한 파라미터 설정
# 020000 : 제1금융
# 030300 : 제2금융
params = {
    "auth": auth_key,
    "topFinGrpNo": "030300",
    "pageNo": "4"
}

# API 호출 및 데이터 가져오기
response = requests.get(api_url, params=params)
data = response.json()

base_list = data["result"]["baseList"]
option_list = data["result"]["optionList"]

# Create a dictionary to store base items by their fin_prdt_cd
base_items_dict = {item["fin_prdt_cd"]: item for item in base_list}

# "optionList"의 내용을 기반으로 여러 개의 FinancialProduct을 생성
for option_item in option_list:
    fin_prdt_cd = option_item["fin_prdt_cd"]
    intr_rate_type_nm = option_item["intr_rate_type_nm"]
    save_trm = int(option_item["save_trm"])
    # "intr_rate"와 "intr_rate2" 값이 None인 경우 0.0으로 초기화
    intr_rate = float(option_item["intr_rate"]) if option_item["intr_rate"] is not None else 0.0
    intr_rate2 = float(option_item["intr_rate2"]) if option_item["intr_rate2"] is not None else 0.0
    
    # 해당 "fin_prdt_cd"를 가진 "baseList" 항목을 찾기
    base_item = base_items_dict.get(fin_prdt_cd, {})
    
    # FinancialProduct 모델에 데이터 추가
    financial_product = FinancialProduct(
        fin_prdt_cd=fin_prdt_cd,
        kor_co_nm=base_item.get("kor_co_nm", ""),
        fin_prdt_nm=base_item.get("fin_prdt_nm", ""),
        co_type_nm='예금',
        bank_type_nm='2금융권',
        intr_rate_type_nm=intr_rate_type_nm,
        save_trm=save_trm,
        intr_rate=intr_rate,
        intr_rate2=intr_rate2,
        join_way=base_item.get("join_way", ""),
        mtrt_int=base_item.get("mtrt_int", ""),
        spcl_cnd=base_item.get("spcl_cnd", ""),
        join_member=base_item.get("join_member", ""),
        etc_note=base_item.get("etc_note", ""),
        max_limit=base_item.get("max_limit", None),
    )
    financial_product.save()