import os
import django

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
django.setup()

from financialData.models import Company, CompanyIndicators, FinancialStatement

companies = Company.objects.all()
cnt = 1
for company in companies:
    print(cnt)
    cnt += 1
    try:
        company_seq = company.seq

        financial_statement = FinancialStatement.objects.get(company_seq=company_seq)

        debt_to_equity_ratio = financial_statement.debt_to_equity_ratio
        revenue = financial_statement.revenue
        capital = financial_statement.capital
        total_asset_growth_rate = financial_statement.total_asset_growth_rate
        operating_profit_margin = financial_statement.operating_profit_margin
        revenue_asset_growth_rate = financial_statement.revenue_asset_growth_rate
        net_income_growth_rate = financial_statement.net_income_growth_rate

        indicators, created = CompanyIndicators.objects.get_or_create(company_seq=company)

        indicators.stability = debt_to_equity_ratio

        if revenue is not None and capital is not None:
            indicators.size = revenue + capital
        else:
            indicators.size = 0
        indicators.growth = total_asset_growth_rate
        indicators.profitability = operating_profit_margin
        indicators.revenue_growth = revenue_asset_growth_rate
        indicators.operating_profit_growth = net_income_growth_rate

        indicators.save()

    except FinancialStatement.DoesNotExist:
        print(f"FinancialStatement data does not exist for company {company.company_name}")