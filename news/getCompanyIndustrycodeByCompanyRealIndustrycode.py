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
            company.company_industry = "농업_임업_어업"
        elif 5 <= industry_code <= 8:
            company.company_industry = "광업"
        elif 10 <= industry_code <= 34:
            company.company_industry = "제조업"
        elif industry_code == 35:
            company.company_industry = "전기_가스_조절공급업"
        elif 36 <= industry_code <= 39:
            company.company_industry = "원료재생업"
        elif 41 <= industry_code <= 42:
            company.company_industry = "건설업"
        elif 45 <= industry_code <= 47:
            company.company_industry = "도매_소매업"
        elif 49 <= industry_code <= 52:
            company.company_industry = "운수_창고업"
        elif 55 <= industry_code <= 56:
            company.company_industry = "숙박_음식점"
        elif 58 <= industry_code <= 63:
            company.company_industry = "정보통신업"
        elif 64 <= industry_code <= 66:
            company.company_industry = "금융업"
        elif industry_code == 68:
            company.company_industry = "부동산업"
        elif 70 <= industry_code <= 73:
            company.company_industry = "기술서비스업"
        elif 74 <= industry_code <= 76:
            company.company_industry = "임대서비스업"
        elif industry_code == 84:
            company.company_industry = "사회보장_행정"
        elif industry_code == 85:
            company.company_industry = "교육_서비스업"
        elif 86 <= industry_code <= 87:
            company.company_industry = "보건_사회복지_서비스업"
        elif 90 <= industry_code <= 91:
            company.company_industry = "여과관련_서비스업"
        else:
            company.company_industry = "기타"
        company.save() 