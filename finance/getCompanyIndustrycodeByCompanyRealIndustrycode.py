from django.apps import apps

import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()
print('django setup 성공')

# Django 애플리케이션 초기화
apps.populate(apps.get_app_configs())
print('초기화 성공')

from financialData.models import Company
companies = Company.objects.all()


for company in companies:
    company_code = company.company_real_industry_code
    if company_code and len(company_code) >= 2:
        first_two_digits = company_code[:2]  # 앞 2자리 숫자 추출
        try:
            industry_code = int(first_two_digits)  # 정수로 변환
            if 1 <= industry_code <= 99:
                if 1 <= industry_code <= 3:
                    company.company_industry_code = '1'
                elif 5 <= industry_code <= 8:
                    company.company_industry_code = '2'
                elif 10 <= industry_code <= 34:
                    company.company_industry_code = '3'
                elif industry_code == 35:
                    company.company_industry_code = '4'
                elif 36 <= industry_code <= 39:
                    company.company_industry_code = '5'
                elif 41 <= industry_code <= 42:
                    company.company_industry_code = '6'
                elif 45 <= industry_code <= 47:
                    company.company_industry_code = '7'
                elif 49 <= industry_code <= 52:
                    company.company_industry_code = '8'
                elif 55 <= industry_code <= 56:
                    company.company_industry_code = '9'
                elif 58 <= industry_code <= 63:
                    company.company_industry_code = '10'
                elif 64 <= industry_code <= 66:
                    company.company_industry_code = '11'
                elif industry_code == 68:
                    company.company_industry_code = '12'
                elif 70 <= industry_code <= 73:
                    company.company_industry_code = '13'
                elif 74 <= industry_code <= 76:
                    company.company_industry_code = '14'
                elif industry_code == 84:
                    company.company_industry_code = '15'
                elif industry_code == 85:
                    company.company_industry_code = '16'
                elif 86 <= industry_code <= 87:
                    company.company_industry_code = '17'
                elif 90 <= industry_code <= 91:
                    company.company_industry_code = '18'
                else:
                    company.company_industry_code = '19'
                company.save() 
        except ValueError:
            pass