import os
import django

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
django.setup()

from financialData.models import FinancialProduct, FinancialProductRank10

filtered_products = FinancialProduct.objects.filter(
    co_type_nm="예금",
    bank_type_nm="1금융권",
).order_by('-intr_rate2')

# 중복 제거 후의 데이터를 가지고 중복되지 않는 상위 10개를 선택합니다.
unique_products = []
selected_products = []

for product in filtered_products:
    if product.fin_prdt_nm not in unique_products:
        unique_products.append(product.fin_prdt_nm)
        selected_products.append(product)

# 상위 10개를 선택합니다.
top_10_products = selected_products[:10]



for product in top_10_products:
    FinancialProductRank10.objects.create(
        fin_prdt_nm=product.fin_prdt_nm,
        kor_co_nm=product.kor_co_nm,
        co_type_nm=product.co_type_nm,
        bank_type_nm=product.bank_type_nm,
        intr_rate_type_nm=product.intr_rate_type_nm,
        save_trm=product.save_trm,
        intr_rate=product.intr_rate,
        intr_rate2=product.intr_rate2,
        fin_prdt_cd=product.fin_prdt_cd,
        spcl_cnd=product.spcl_cnd,
        etc_note=product.etc_note,
        max_limit=product.max_limit,
        rsrv_type_nm=product.rsrv_type_nm,
    )