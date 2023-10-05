import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "crawling.settings")

import django
django.setup()

from financialData.models import Company, FinancialStatement, FinancialStatement_2022, FinancialStatement_2021

url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "ac30ceeeb8ac94447e5e065876a5783d79e5b3ec"

# 모든 Company 모델의 company_code를 가져옴
company_codes = Company.objects.values_list('company_code', flat=True).order_by('seq')[2640:2641]
print(company_codes)
count = 1
for company_code in company_codes:
    # 요청 파라미터 설정
    params = {
        "crtfc_key": api_key,
        "corp_code": company_code,
        "bsns_year": "2022",
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

            # account_nm 값이 같은경우가 있어 맨처음 만나는 종합정보만 얻음
            handled_non_total_assets = False
            handled_total_liabilities = False
            handled_revenue = False
            handled_operating_profit = False
            handled_net_income = False

            for item in statement.get('list', []):
                account_name = item.get('account_nm')
                amount_str = item.get('thstrm_amount', '0')

                # 빈 문자열인 경우 처리
                if not amount_str:
                    continue

                amount = float(amount_str)

                if account_name == "자산총계" and not handled_non_total_assets:
                    financial_statement_dict["total_assets"] = amount
                    handled_non_total_assets = True

                elif account_name == "부채총계" and not handled_total_liabilities:
                    financial_statement_dict["total_liabilities"] = amount   
                    handled_total_liabilities = True  

                elif (account_name == "수익(매출액)" or account_name == "매출액" or account_name == "영업수익") and not handled_revenue:
                    financial_statement_dict["revenue"] = amount
                    handled_revenue = True  

                elif (account_name == "영업이익" or account_name == "영업이익(손실)" or account_name == "영업손실" or account_name == "영업비용") and not handled_operating_profit:
                    if account_name == "영업손실":
                        financial_statement_dict["operating_profit"] = -abs(amount)
                        handled_operating_profit = True 
                    else:
                        financial_statement_dict["operating_profit"] = amount
                        handled_operating_profit = True
            
                elif (account_name == "당기순이익" or account_name == "반기순이익" or account_name == "당기순이익(손실)" or account_name == "반기순이익(손실)" or account_name == "당기순손익") and not handled_net_income:
                    financial_statement_dict["net_income"] = amount
                    handled_net_income = True
                
                fs_instance, created = FinancialStatement_2022.objects.update_or_create(
                company_seq = financial_statement_dict["company_seq"],
                defaults=financial_statement_dict
            )


        except Company.DoesNotExist:
            print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")

    else:
        print(f"API 호출 실패 (company_code: {company_code}):", response.status_code)