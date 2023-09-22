import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()

from financialData.models import Company, FinancialStatement

url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "ac30ceeeb8ac94447e5e065876a5783d79e5b3ec"

# 모든 Company 모델의 company_code를 가져옴
company_codes = Company.objects.values_list('company_code', flat=True)[1000:1001]
print(company_codes)
count = 0
for company_code in company_codes:
    # 요청 파라미터 설정
    params = {
        "crtfc_key": api_key,
        "corp_code": company_code,
        "bsns_year": "2023",
        "reprt_code" : "11012",
        "fs_div" : "OFS"
    }
    # print(company_code)
    # print('여기도 안온다고?')

    response = requests.get(url, params=params)

    if response.status_code == 200:
        statement = response.json()

        try:
            company = Company.objects.get(company_code=company_code)
            financial_statement_dict = {"company_seq": company}

            # print(statement)
            print(count)
            count += 1

            for item in statement.get('list', []):
                account_name = item.get('account_nm')
                amount = int(item.get('thstrm_amount', '0'))
                print(item)

                if account_name == "유동자산":
                    financial_statement_dict["current_assets"] = amount
                    print(amount)
                    print('유동자산 호출됨')

                elif account_name == "비유동자산":
                    financial_statement_dict["non_current_assets"] = amount
                    print(amount)
                    print('비유동자산 호출됨')

            fs_instance = FinancialStatement(**financial_statement_dict)
            fs_instance.save()

        except Company.DoesNotExist:
            print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")
    else:
        print(f"API 호출 실패 (company_code: {company_code}):", response.status_code)