from django.db import models

class CompanyIndustry(models.Model):
    company_industry_code = models.IntegerField(primary_key=True)
    industry_name = models.CharField(max_length=255)

    def __str__(self):
        return self.industry_name

class Company(models.Model):
    seq = models.AutoField(primary_key=True)
    company_code = models.CharField(max_length=10)
    company_name = models.CharField(max_length=255)
    # company_industry_code = models.IntegerField(default=None, null=True)
    company_stock_code = models.CharField(max_length=10)
    company_real_industry_code = models.CharField(max_length=10, null=True)

    company_industry_code = models.OneToOneField(
        CompanyIndustry,
        on_delete=models.SET_NULL,
        null=True,
        related_name='company',
    )

    def __str__(self):
        return self.company_name

class FinancialStatement(models.Model):
    seq = models.AutoField(primary_key=True)
    company_seq = models.ForeignKey(
        Company, 
        on_delete=models.CASCADE, 
        related_name='financial_statements'
    )
    current_assets = models.BigIntegerField()  # 유동자산
    non_current_assets = models.BigIntegerField()  # 비유동자산
    total_assets_first = models.BigIntegerField()  # 자산총계 1분기
    total_assets_second = models.BigIntegerField()  # 자산총계 2분기
    total_assets_third = models.BigIntegerField()  # 자산총계 3분기
    current_liabilities = models.BigIntegerField()  # 유동부채
    non_current_liabilities = models.BigIntegerField()  # 비유동부채
    total_liabilities_first = models.BigIntegerField()  # 부채총계 1분기
    total_liabilities_second = models.BigIntegerField()  # 부채총계 2분기
    total_liabilities_third = models.BigIntegerField()  # 부채총계 3분기
    capital = models.BigIntegerField()  # 자본금
    total_equity = models.BigIntegerField()  # 자본총계
    revenue_first = models.BigIntegerField()  # 매출액 1분기
    revenue_second = models.BigIntegerField()  # 매출액 2분기
    revenue_third = models.BigIntegerField()  # 매출액 3분기
    gross_profit = models.BigIntegerField()  # 매출총이익
    operating_profit_first = models.BigIntegerField()  # 영업이익 1분기
    operating_profit_second = models.BigIntegerField()  # 영업이익 2분기
    operating_profit_third = models.BigIntegerField()  # 영업이익 3분기
    non_operating_income = models.BigIntegerField()  # 영업외수익
    non_operating_expenses = models.BigIntegerField()  # 영업외비용
    income_before_tax = models.BigIntegerField()  # 법인세차감전이익
    income_tax_expense = models.BigIntegerField()  # 법인세비용
    net_income = models.BigIntegerField()  # 총당기순이익
    total_asset_growth_rate = models.FloatField()  # 총자산증가율
    revenue_asset_growth_rate = models.FloatField()  # 매출액자산증가율
    net_income_growth_rate = models.FloatField()  # 순이익증가율
    operating_profit_margin = models.FloatField()  # 영업이익률
    roe = models.FloatField()  # ROE
    roic = models.FloatField()  # ROIC
    debt_to_equity_ratio = models.FloatField()  # 부채비율
    interest_coverage_ratio = models.FloatField()  # 이자보상배수
    debt_dependency = models.FloatField()  # 차입금의존도
    accounts_receivable_turnover = models.FloatField()  # 매출채권회전율
    inventory_turnover = models.FloatField()  # 재고자산회전율
    total_asset_turnover = models.FloatField()  # 총자본회전율