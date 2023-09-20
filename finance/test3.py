import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "myproject.settings")  # "myproject.settings"를 프로젝트 설정 파일 경로로 변경
import django
django.setup()

from financialData.models import Company

# Company 모델의 인스턴스를 얻습니다.
company_instance = Company.objects.get(pk=1)  # 이 부분은 실제로 사용할 인스턴스의 PK 또는 다른 필드 값으로 대체해야 합니다.

# company_instance가 참조하고 있는 CompanyIndustry 모델의 레코드를 가져옵니다.
referenced_company_industry = company_instance.company_industry_code

if referenced_company_industry:
    # 참조하는 CompanyIndustry 레코드가 존재한다면 해당 정보를 사용할 수 있습니다.
    print(f"{company_instance.company_name}은(는) {referenced_company_industry.industry_name}을(를) 참조합니다.")
else:
    # 참조하는 CompanyIndustry 레코드가 없다면 처리할 내용을 여기에 작성합니다.
    print(f"{company_instance.company_name}은(는) 어떤 산업을 참조하지 않습니다.")

