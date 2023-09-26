import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()

from financialData.models import Company, FinancialStatement, FinancialStatement_2022, FinancialStatement_2021

url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"  
api_key = "ac30ceeeb8ac94447e5e065876a5783d79e5b3ec"

# 모든 Company 모델의 company_code를 가져옴
company_codes = Company.objects.values_list('company_code', flat=True)[:500]
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
            handled_current_assets = False
            handled_non_current_assets = False
            handled_non_total_assets = False
            handled_current_liabilities = False
            handled_non_current_liabilities = False
            handled_total_liabilities = False
            handled_capital = False
            handled_total_equity = False
            handled_revenue = False
            handled_gross_profit = False
            handled_operating_profit = False
            handled_non_operating_income = False
            handled_non_operating_expenses = False
            handled_income_before_tax = False
            handled_income_tax_expense = False
            handled_net_income = False

            handled_total_asset_growth_rate = False 
            handled_revenue_asset_growth_rate = False
            handled_net_income_growth_rate = False
            handled_operating_profit_margin = False
            handled_roe = False
            handled_roic= False
            handled_debt_to_equity_ratio = False 
            handled_interest_coverage_ratio = False
            handled_debt_dependency = False
            handled_accounts_receivable_turnover = False
            handled_inventory_turnover = False
            handled_total_asset_turnover = False

            for item in statement.get('list', []):
                account_name = item.get('account_nm')
                amount_str = item.get('thstrm_amount', '0')

                # 빈 문자열인 경우 처리
                if not amount_str:
                    continue

                amount = float(amount_str)

                if account_name == "유동자산" and not handled_current_assets:
                    financial_statement_dict["current_assets"] = amount
                    handled_current_assets = True

                elif account_name == "비유동자산" and not handled_non_current_assets:
                    financial_statement_dict["non_current_assets"] = amount
                    handled_non_current_assets = True

                elif account_name == "자산총계" and not handled_non_total_assets:
                    financial_statement_dict["total_assets"] = amount
                    handled_non_total_assets = True
                
                elif account_name == "유동부채" and not handled_current_liabilities:
                    financial_statement_dict["current_liabilities"] = amount
                    handled_current_liabilities = True
                    
                elif account_name == "비유동부채" and not handled_non_current_liabilities:
                    financial_statement_dict["non_current_liabilities"] = amount   
                    handle_non_current_liabilities = True

                elif account_name == "부채총계" and not handled_total_liabilities:
                    financial_statement_dict["total_liabilities"] = amount   
                    handled_total_liabilities = True  

                elif account_name == "자본금" and not handled_capital:  
                    financial_statement_dict["capital"] = amount   
                    handled_capital = True  

                elif account_name == "자본총계" and not handled_total_equity:   
                    financial_statement_dict['total_equity'] = amount    
                    handled_total_equity = True    

                elif account_name == "수익(매출액)" and not handled_revenue:
                    financial_statement_dict["revenue"] = amount
                    handled_revenue = True  

                elif account_name == "매출총이익" and not handled_gross_profit:
                    financial_statement_dict["gross_profit"] = amount
                    handled_gross_profit = True  

                elif account_name == "영업이익" and not handled_operating_profit:
                    financial_statement_dict["operating_profit"] = amount
                    handled_operating_profit = True 

                elif account_name == "영업외수익" and not handled_non_operating_income:
                    financial_statement_dict["non_operating_income"] = amount
                    handled_non_operating_income = True
                
                elif account_name == "영업외비용" and not handled_non_operating_expenses:
                    financial_statement_dict["non_operating_expenses"] = amount
                    handled_non_operating_expenses = True

                elif account_name == "법인세비용차감전순이익(손실)" and not handled_income_before_tax:
                    financial_statement_dict["income_before_tax"] = amount
                    handled_income_before_tax = True


                elif account_name == "법인세비용" and not handled_income_tax_expense:
                    financial_statement_dict["income_tax_expense"] = amount
                    handled_income_tax_expense = True

                elif account_name == "당기순이익" and not handled_net_income:
                    financial_statement_dict["net_income"] = amount
                    handled_net_income = True

                elif account_name == "총자산증가율" and not handled_total_asset_growth_rate:
                    financial_statement_dict["total_asset_growth_rate"] = amount
                    handled_total_asset_growth_rate = True

                elif account_name == "매출액자산증가율" and not handled_revenue_asset_growth_rate:
                    financial_statement_dict["revenue_asset_growth_rate"] = amount
                    handled_revenue_asset_growth_rate = True

                elif account_name == "순이익증가율" and not handled_net_income_growth_rate:
                    financial_statement_dict["net_income_growth_rate"] = amount
                    handled_net_income_growth_rate = True

                elif account_name == "영업이익률" and not handled_operating_profit_margin:
                    financial_statement_dict["operating_profit_margin"] = amount
                    handled_operating_profit_margin = True

                elif account_name == "ROE" and not handled_roe:
                    financial_statement_dict["roe"] = amount
                    handled_roe = True

                elif account_name == "ROIC" and not handled_roic:
                    financial_statement_dict["roic"] = amount
                    handled_roic = True

                elif account_name == "부채비율" and not handled_debt_to_equity_ratio:
                    financial_statement_dict["debt_to_equity_ratio"] = amount
                    handled_debt_to_equity_ratio = True

                elif account_name == "이자보상배수" and not handled_interest_coverage_ratio:
                    financial_statement_dict["interest_coverage_ratio"] = amount
                    handled_interest_coverage_ratio = True

                elif account_name == "차입금의존도" and not handled_debt_dependency:
                    financial_statement_dict["debt_dependency"] = amount
                    handled_debt_dependency = True

                elif account_name == "매출채권회전율" and not handled_accounts_receivable_turnover:
                    financial_statement_dict["accounts_receivable_turnover"] = amount
                    handled_accounts_receivable_turnover = True
                
                elif account_name == "재고자산회전율" and not handled_inventory_turnover:
                    financial_statement_dict["inventory_turnover"] = amount
                    handled_inventory_turnover = True

                elif account_name == "총자본회전율" and not handled_total_asset_turnover:
                    financial_statement_dict["total_asset_turnover"] = amount
                    handled_total_asset_turnover = True

                
            # 'company_seq' 동일한 FinancialStatement 인스턴스 찾아서 생성 or 수정
            fs_instance, created = FinancialStatement_2022.objects.update_or_create(
                company_seq = financial_statement_dict["company_seq"],
                defaults=financial_statement_dict
            )

        except Company.DoesNotExist:
            print(f"Company 인스턴스가 없습니다. (company_code: {company_code})")
    else:
        print(f"API 호출 실패 (company_code: {company_code}):", response.status_code)