import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()

from financialData.models import Company, FinancialStatement

url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "36509c5feed29d3468703dc40d3a64536d7cf944"

# 모든 Company 모델의 company_code를 가져옴
company_codes = Company.objects.values_list('company_code', flat=True)[:1]

for company_code in company_codes:
    # 요청 파라미터 설정
    params = {
        "crtfc_key": api_key,
        "corp_code": company_code,
        "bsns_year": "2023",
        "reprt_code" : "11012",
        "fs_div" : "OFS"
    }   

    response = requests.get(url, params=params)

    if response.status_code == 200:
        statement = response.json()

        for item in statement.get('list', []):
            account_name = item.get('account_nm')
            amount = item.get('thstrm_amount')

            if account_name == "유동자산":
                # 해당 계정명이 "유동자산"인 경우 current_assets 필드에 저장
                try:
                    company = Company.objects.get(company_code=company_code)
                    financial_statement = FinancialStatement(
                        company_seq=company,
                        current_assets=amount,
                    )
                    financial_statement.save()
                except Company.DoesNotExist:
                    print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")

            elif account_name == "비유동자산":
                # 해당 계정명이 "비유동자산"인 경우 non_current_assets 필드에 저장
                try:
                    company = Company.objects.get(company_code=company_code)
                    financial_statement = FinancialStatement(
                        company_seq=company,
                        non_current_assets=amount,
                    )
                    financial_statement.save()
                except Company.DoesNotExist:
                    print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")
                    
    else:
        print(f"API 호출 실패 (company_code: {company_code}):", response.status_code)
