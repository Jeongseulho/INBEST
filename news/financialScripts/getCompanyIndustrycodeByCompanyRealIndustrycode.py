from django.apps import apps

import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "crawling.settings")

import django
django.setup()
print('django setup 성공')

# Django 애플리케이션 초기화
apps.populate(apps.get_app_configs())
print('초기화 성공')

from financialData.models import Company
companies = Company.objects.all()

Working = 0
for company in companies:
    company_code = company.company_real_industry_code

    if company_code and len(company_code) >= 2:
        first_two_digits = company_code[:2]  # 앞 2자리 숫자 추출
        
        industry_code = int(first_two_digits)  # 정수로 변환

        print(f'Working : {Working}' )
        Working += 1

        if 1 <= industry_code <= 3:
            company.company_industry = "agriculture_forestry_fishing"
        elif 5 <= industry_code <= 8:
            company.company_industry = "mining"
        elif 10 <= industry_code <= 34:
            company.company_industry = "manufacturing"
        elif industry_code == 35:
            company.company_industry = "electricity_gas_supply"
        elif 36 <= industry_code <= 39:
            company.company_industry = "water_waste_recycling"
        elif 41 <= industry_code <= 42:
            company.company_industry = "construction"
        elif 45 <= industry_code <= 47:
            company.company_industry = "wholesale_retail_trade"
        elif 49 <= industry_code <= 52:
            company.company_industry = "transportation_warehousing"
        elif 55 <= industry_code <= 56:
            company.company_industry = "accommodation_food_services"
        elif 58 <= industry_code <= 63:
            company.company_industry = "information_communication"
        elif 64 <= industry_code <= 66:
            company.company_industry = "financial_insurance"
        elif industry_code == 68:
            company.company_industry = "dfasdfasdfasdfasfasdf"
        elif 70 <= industry_code <= 73:
            company.company_industry = "professional_scientific_technical_services"
        elif 74 <= industry_code <= 76:
            company.company_industry = "business_support_rental_services"
        elif industry_code == 84:
            company.company_industry = "public_administration"
        elif industry_code == 85:
            company.company_industry = "education"
        elif 86 <= industry_code <= 87:
            company.company_industry = "health_social_services"
        elif 90 <= industry_code <= 91:
            company.company_industry = "arts_sports_recreation"
        else:
            company.company_industry = "other_industry"
        company.save() 