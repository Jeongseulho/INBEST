import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "crawling.settings")

import django
django.setup()

from financialData.models import Company, FinancialStatement, FinancialStatement_2022, FinancialStatement_2021

url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "36509c5feed29d3468703dc40d3a64536d7cf944"

# 모든 Company 모델의 company_code를 가져옴
company_codes = Company.objects.values_list('company_code', flat=True).order_by('seq')[1500:2000]
print(company_codes)
count = 1
for company_code in company_codes:
    # 요청 파라미터 설정
    params = {
        "crtfc_key": api_key,
        "corp_code": company_code,
        "bsns_year": "2021",
        "reprt_code" : "11012",
        "fs_div" : "OFS"
    }

    response = requests.get(url, params=params)

    if response.status_code == 200:
        statement = response.json()

        try:
            company = Company.objects.get(company_code=company_code)
            financial_statement_dict = {"company_seq": company}

            # print(statement)
            print(count)
            count += 1

            handled_revenue = False
            handled_net_income = False

            for item in statement.get('list', []):
                account_name = item.get('account_nm')
                amount_str = item.get('thstrm_amount', '0')

                # 빈 문자열인 경우 처리
                if not amount_str:
                    continue

                amount = float(amount_str)

                if (account_name == "수익(매출액)" or account_name == "매출액" or account_name == "영업수익") and not handled_revenue:
                    financial_statement_dict["revenue"] = amount
                    handled_revenue = True  

                elif (account_name == "당기순이익" or account_name == "반기순이익" or account_name == "당기순이익(손실)" or account_name == "반기순이익(손실)" or account_name == "당기순손익") and not handled_net_income:
                    financial_statement_dict["net_income"] = amount
                    handled_net_income = True

                fs_instance, created = FinancialStatement_2021.objects.update_or_create(
                company_seq = financial_statement_dict["company_seq"],
                defaults=financial_statement_dict
            )

        except Company.DoesNotExist:
            print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")

    else:
        print(f"API 호출 실패 (company_code: {company_code}):", response.status_code)