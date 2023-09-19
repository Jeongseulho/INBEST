from financialData.models import Company

import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "finance.settings")

import django
django.setup()

companies = Company.objects.all()


for company in companies:
    company_code = company.company_code
    if company_code and len(company_code) >= 2:
        first_two_digits = company_code[:2]  # 앞 2자리 숫자 추출
        try:
            industry_code = int(first_two_digits)  # 정수로 변환
            if 1 <= industry_code <= 99:
                if 1 <= industry_code <= 10:
                    company.company_industry_code = 'A'
                elif 11 <= industry_code <= 20:
                    company.company_industry_code = 'B'
                else:
                    company.company_industry_code = 'C'
                company.save() 
        except ValueError:
            pass