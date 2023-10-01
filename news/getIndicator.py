import os
import django

# Django 설정 모듈을 설정합니다.
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
django.setup()

from financialData.models import Company, CompanyIndicators, FinancialStatement

companies = Company.objects.all()

for company in companies:
    # Company 모델에서 해당 회사의 company_seq와 debt_to_equity_ratio 값을 가져옵니다.

    company_seq = company.seq 
    financial_statement = FinancialStatement.objects.get(company_seq=company_seq)

    debt_to_equity_ratio = financial_statement.debt_to_equity_ratio
    revenue = financial_statement.revenue
    capital = financial_statement.capital
    total_asset_growth_rate = financial_statement.total_asset_growth_rate
    operating_profit_margin = financial_statement.operating_profit_margin
    revenue_asset_growth_rate = financial_statement.revenue_asset_growth_rate
    net_income_growth_rate = financial_statement.net_income_growth_rate

    # CompanyIndicators 모델에서 해당 company_seq에 해당하는 인스턴스를 가져옵니다.
    indicators, created = CompanyIndicators.objects.get_or_create(company_seq=company_seq)

    # 가져온 데이터를 CompanyIndicators 모델의 stability 필드에 저장합니다.
    indicators.stability = debt_to_equity_ratio
    indicators.size = revenue + capital
    indicators.growth = total_asset_growth_rate
    indicators.profitability = operating_profit_margin
    indicators.revenue_growth = revenue_asset_growth_rate
    indicators.operating_profit_growth = net_income_growth_rate

    # 변경사항을 저장합니다.
    indicators.save()