from django.db import models
class Company(models.Model):
    seq = models.AutoField(primary_key=True)
    company_code = models.CharField(max_length=10)
    company_name = models.CharField(max_length=255)
    company_stock_code = models.CharField(max_length=10)
    company_real_industry_code = models.CharField(max_length=10, null=True)
    company_industry = models.CharField(max_length=50)

class FinancialStatement(models.Model):
    seq = models.AutoField(primary_key=True)
    company_seq = models.ForeignKey('Company', on_delete=models.CASCADE, related_name='financial_statements')
    current_assets = models.BigIntegerField(default=0)  # 유동자산
    non_current_assets = models.BigIntegerField(default=0)  # 비유동자산
    total_assets_first = models.BigIntegerField(default=0)  # 자산총계 2분기
    total_assets_second = models.BigIntegerField(default=0)  # 자산총계 4분기
    current_liabilities = models.BigIntegerField(default=0)  # 유동부채
    non_current_liabilities = models.BigIntegerField(default=0)  # 비유동부채
    total_liabilities_first = models.BigIntegerField(default=0)  # 부채총계 2분기
    total_liabilities_second = models.BigIntegerField(default=0)  # 부채총계 4분기
    capital = models.BigIntegerField(default=0)  # 자본금
    total_equity = models.BigIntegerField(default=0)  # 자본총계

    revenue_first = models.BigIntegerField(default=0)  # 매출액 2분기
    revenue_second = models.BigIntegerField(default=0)  # 매출액 4분기
    revenue_2020 = models.BigIntegerField(default=0)  # 2020년 매출액
    revenue_2021 = models.BigIntegerField(default=0)  # 2021년 매출액
    gross_profit = models.BigIntegerField(default=0)  # 매출총이익
    operating_profit_first = models.BigIntegerField(default=0)  # 영업이익 2분기
    operating_profit_second = models.BigIntegerField(default=0)  # 영업이익 4분기
    non_operating_income = models.BigIntegerField(default=0)  # 영업외수익
    non_operating_expenses = models.BigIntegerField(default=0)  # 영업외비용
    income_before_tax = models.BigIntegerField(default=0)  # 법인세차감전이익
    income_tax_expense = models.BigIntegerField(default=0)  # 법인세비용
    net_income = models.BigIntegerField(default=0)  # 총당기순이익
    net_income_2020 = models.BigIntegerField(default=0)  # 2020년 총단기순이익
    net_income_2021 = models.BigIntegerField(default=0)  # 2021년 총단기순이익

    total_asset_growth_rate = models.FloatField(default=0.0)  # 총자산증가율
    revenue_asset_growth_rate = models.FloatField(default=0.0)  # 매출액자산증가율
    net_income_growth_rate = models.FloatField(default=0.0)  # 순이익증가율
    operating_profit_margin = models.FloatField(default=0.0)  # 영업이익률
    roe = models.FloatField(default=0.0)  # ROE
    roic = models.FloatField(default=0.0)  # ROIC
    debt_to_equity_ratio = models.FloatField(default=0.0)  # 부채비율
    interest_coverage_ratio = models.FloatField(default=0.0)  # 이자보상배수
    debt_dependency = models.FloatField(default=0.0)  # 차입금의존도
    accounts_receivable_turnover = models.FloatField(default=0.0)  # 매출채권회전율
    inventory_turnover = models.FloatField(default=0.0)  # 재고자산회전율
    total_asset_turnover = models.FloatField(default=0.0)  # 총자본회전율