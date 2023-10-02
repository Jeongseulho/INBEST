import requests
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "crawling.settings")

import django
django.setup()

from financialData.models import Company


# Opendart API 엔드포인트 URL
url = "https://opendart.fss.or.kr/api/company.json"
api_key = "ac30ceeeb8ac94447e5e065876a5783d79e5b3ec"

# Company 모델에서 company_code가 있는 부분을
companies  = Company.objects.filter(company_code__isnull=False)[3000:]
checkcount = 0

for company in companies:
    company_code = company.company_code

    # 요청 파라미터 설정    
    params = {
        "crtfc_key": api_key,
        "corp_code" : company_code
    }
    checkcount += 1
    print(checkcount)

    response = requests.get(url, params=params)

    if response.status_code == 200:
        company_info = response.json()
        induty_code = company_info.get("induty_code")

        company.company_real_industry_code = induty_code
        company.save()

    else:
        print("API 호출 실패:", response.status_code)