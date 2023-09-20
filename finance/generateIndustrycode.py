import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "myproject.settings")  # "myproject.settings"를 프로젝트 설정 파일 경로로 변경
import django
django.setup()

from financialData.models import CompanyIndustry

# 각 산업과 코드를 정의합니다.
industries = [
    (1, "agriculture_forestry_fishing"),
    (2, "mining"),
    (3, "manufacturing"),
    (4, "electricity_gas_supply"),
    (5, "water_waste_recycling"),
    (6, "construction"),
    (7, "wholesale_retail_trade"),
    (8, "transportation_warehousing"),
    (9, "accommodation_food_services"),
    (10, "information_communication"),
    (11, "financial_insurance"),
    (12, "real_estate"),
    (13, "professional_scientific_technical_services"),
    (14, "business_support_rental_services"),
    (15, "public_administration"),
    (16, "education"),
    (17, "health_social_services"),
    (18, "arts_sports_recreation"),
    (19, "other_industry"),
]

# 산업 데이터를 반복문으로 저장합니다.
for code, name in industries:
    industry = CompanyIndustry(company_industry_code=code, industry_name=name)
    industry.save()