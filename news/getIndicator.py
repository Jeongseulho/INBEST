import os
import django

# Django 설정 모듈을 설정합니다.
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
django.setup()

from financialData.models import Company, CompanyIndicators, FinancialStatement

companies = Company.objects.all()
cnt = 1
for company in companies:
    print(cnt)
    cnt += 1
    try:
        # Company 모델에서 해당 회사의 company_seq 값을 가져옵니다.
        company_seq = company.seq

        # FinancialStatement 모델에서 해당 회사의 정보를 가져옵니다.
        financial_statement = FinancialStatement.objects.get(company_seq=company_seq)

        # 가져온 데이터를 사용하여 CompanyIndicators 모델의 필드를 업데이트합니다.
        debt_to_equity_ratio = financial_statement.debt_to_equity_ratio
        revenue = financial_statement.revenue
        capital = financial_statement.capital
        total_asset_growth_rate = financial_statement.total_asset_growth_rate
        operating_profit_margin = financial_statement.operating_profit_margin
        revenue_asset_growth_rate = financial_statement.revenue_asset_growth_rate
        net_income_growth_rate = financial_statement.net_income_growth_rate

        # CompanyIndicators 모델에서 해당 company_seq에 해당하는 인스턴스를 가져오거나 생성합니다.
        indicators, created = CompanyIndicators.objects.get_or_create(company_seq=company)

        # 가져온 데이터를 CompanyIndicators 모델의 필드에 저장합니다.
        indicators.stability = debt_to_equity_ratio

        if revenue is not None and capital is not None:
            indicators.size = revenue + capital
        else:
            indicators.size = 0
        indicators.growth = total_asset_growth_rate
        indicators.profitability = operating_profit_margin
        indicators.revenue_growth = revenue_asset_growth_rate
        indicators.operating_profit_growth = net_income_growth_rate

        # 변경사항을 저장합니다.
        indicators.save()

    except FinancialStatement.DoesNotExist:
        # 해당 회사에 대한 FinancialStatement 데이터가 없는 경우 예외 처리합니다.
        print(f"FinancialStatement data does not exist for company {company.company_name}")