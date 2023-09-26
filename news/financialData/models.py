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
    current_assets = models.BigIntegerField(null=True, default=None)  # 유동자산
    non_current_assets = models.BigIntegerField(null=True, default=None)  # 비유동자산
    total_assets = models.BigIntegerField(null=True, default=None)  # 자산총계
    current_liabilities = models.BigIntegerField(null=True, default=None)  # 유동부채
    non_current_liabilities = models.BigIntegerField(null=True, default=None)  # 비유동부채
    total_liabilities = models.BigIntegerField(null=True, default=None)  # 부채총계
    capital = models.BigIntegerField(null=True, default=None)  # 자본금
    total_equity = models.BigIntegerField(null=True, default=None)  # 자본총계

    revenue = models.BigIntegerField(null=True, default=None)  # 매출액
    gross_profit = models.BigIntegerField(null=True, default=None)  # 매출총이익
    operating_profit = models.BigIntegerField(null=True, default=None)  # 영업이익
    non_operating_income = models.BigIntegerField(null=True, default=None)  # 영업외수익
    non_operating_expenses = models.BigIntegerField(null=True, default=None)  # 영업외비용
    income_before_tax = models.BigIntegerField(null=True, default=None)  # 법인세차감전이익
    income_tax_expense = models.BigIntegerField(null=True, default=None)  # 법인세비용
    net_income = models.BigIntegerField(null=True, default=None)  # 총당기순이익

    total_asset_growth_rate = models.FloatField(null=True, default=None)  # 총자산증가율
    revenue_asset_growth_rate = models.FloatField(null=True, default=None)  # 매출액자산증가율
    net_income_growth_rate = models.FloatField(null=True, default=None)  # 순이익증가율
    operating_profit_margin = models.FloatField(null=True, default=None)  # 영업이익률
    roe = models.FloatField(null=True, default=None)  # ROE
    roic = models.FloatField(null=True, default=None)  # ROIC
    debt_to_equity_ratio = models.FloatField(null=True, default=None)  # 부채비율
    interest_coverage_ratio = models.FloatField(null=True, default=None)  # 이자보상배수
    debt_dependency = models.FloatField(null=True, default=None)  # 차입금의존도
    accounts_receivable_turnover = models.FloatField(null=True, default=None)  # 매출채권회전율
    inventory_turnover = models.FloatField(null=True, default=None)  # 재고자산회전율
    total_asset_turnover = models.FloatField(null=True, default=None)  # 총자본회전율

class FinancialStatement_2022(models.Model):
    seq = models.AutoField(primary_key=True)
    company_seq = models.ForeignKey('Company', on_delete=models.CASCADE, related_name='financial_statements_2022')
    total_assets = models.BigIntegerField(null=True, default=None)  # 자산총계
    total_liabilities = models.BigIntegerField(null=True, default=None)  # 부채총계

    revenue = models.BigIntegerField(null=True, default=None)  # 매출액
    operating_profit = models.BigIntegerField(null=True, default=None)  # 영업이익
    net_income = models.BigIntegerField(null=True, default=None)  # 총당기순이익

class FinancialStatement_2021(models.Model):
    seq = models.AutoField(primary_key=True)
    company_seq = models.ForeignKey('Company', on_delete=models.CASCADE, related_name='financial_statements_2021')

    revenue = models.BigIntegerField(null=True, default=None)  # 매출액
    net_income = models.BigIntegerField(null=True, default=None)  # 총당기순이익

class FinancialProduct(models.Model):
    seq = models.BigAutoField(primary_key=True)
    fin_prdt_nm = models.CharField(max_length=255, null=True)  # 금융 상품명
    kor_co_nm = models.CharField(max_length=255, null=True)  # 금융 회사명
    co_type_nm = models.CharField(max_length=255, null=True)  # 금융 유형
    bank_type_nm = models.CharField(max_length=255, null=True)  # 금융권
    intr_rate_type_nm = models.CharField(max_length=255, null=True)  # 금리유형
    save_trm = models.BigIntegerField(null=True)  # 저축 기간
    intr_rate = models.FloatField(null=True)  # 저축 금리
    intr_rate2 = models.FloatField(null=True)  # 최대 저축 금리
    fin_prdt_cd = models.CharField(max_length=255, null=True)  # 금융상품 코드
    join_way = models.CharField(max_length=255, null=True)  # 가입 방법
    mtrt_int = models.CharField(max_length=255, null=True)  # 만기 후 이자율
    spcl_cnd = models.CharField(max_length=255, null=True)  # 우대조건
    join_member = models.CharField(max_length=255, null=True)  # 가입 대상
    etc_note = models.CharField(max_length=255, null=True)  # 기타 유의사항
    max_limit = models.BigIntegerField(null=True)  # 최고한도
    rsrv_type_nm = models.CharField(max_length=255, default=None, null=True) # 적금형태
