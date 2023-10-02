import os
import django

# Django 설정 모듈을 설정합니다.
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
django.setup()

from financialData.models import CompanyIndicators, CompanyIndicatorsScore

def calculate_ranking_score(values):
    # 값 중에서 None 또는 0을 제외하고 정렬합니다.
    filtered_values = [x for x in values if x is not None and x != 0]

    if not filtered_values:
        return [None] * len(values)  # 모든 값이 Null 또는 0인 경우에는 None을 반환

    # 등수를 저장할 리스트를 초기화합니다.
    ranking = [None] * len(values)

    # 값들을 내림차순으로 정렬합니다.
    sorted_values = sorted(filtered_values, reverse=True)

    # 등수를 계산하고 설정합니다.
    for i, value in enumerate(sorted_values):
        index = [j for j, v in enumerate(values) if v == value][0]  # 원래 리스트에서 해당 값의 인덱스를 찾음
        ranking[index] = i + 1  # 순위를 설정

    max_rank = len(filtered_values)

    # 등수를 점수로 변환하고 반환합니다.
    return [round(100 - (rank - 1) * 100 / max_rank, 2) if rank is not None else None for rank in ranking]

# CompanyIndicators 테이블에서 데이터를 가져옵니다.
indicators_data = CompanyIndicators.objects.all()

# 각 데이터에 대해 작업을 수행합니다.
for indicators in indicators_data:
    # CompanyIndicators 모델의 size 필드 값을 가져옵니다.
    stability_values = CompanyIndicators.objects.filter(stability__isnull=False).values_list('stability', flat=True)
    size_values = CompanyIndicators.objects.filter(size__isnull=False).values_list('size', flat=True)
    growth_values = CompanyIndicators.objects.filter(growth__isnull=False).values_list('growth', flat=True)
    profitability_values = CompanyIndicators.objects.filter(profitability__isnull=False).values_list('profitability', flat=True)
    revenue_growth_values = CompanyIndicators.objects.filter(revenue_growth__isnull=False).values_list('revenue_growth', flat=True)
    operating_profit_growth_values = CompanyIndicators.objects.filter(operating_profit_growth__isnull=False).values_list('operating_profit_growth', flat=True)
    
    # 등수를 계산합니다.
    ranking_scores = calculate_ranking_score(size_values)
    stability_ranking_scores = calculate_ranking_score(stability_values)
    size_ranking_scores = calculate_ranking_score(size_values)
    growth_ranking_scores = calculate_ranking_score(growth_values)
    profitability_ranking_scores = calculate_ranking_score(profitability_values)
    revenue_growth_ranking_scores = calculate_ranking_score(revenue_growth_values)
    operating_profit_growth_ranking_scores = calculate_ranking_score(operating_profit_growth_values)


    # CompanyIndicatorsScore 모델에서 해당 company_seq에 해당하는 인스턴스를 가져옵니다.
    score, created = CompanyIndicatorsScore.objects.get_or_create(company_seq=indicators.company_seq)

    # size에 점수를 저장합니다.
    if indicators.size is not None and indicators.size != 0:
        index = [i for i, v in enumerate(size_values) if v == indicators.size][0]
        score.size = size_ranking_scores[index]

    # stability에 점수를 저장합니다.
    if indicators.stability is not None and indicators.stability != 0:
        index = [i for i, v in enumerate(stability_values) if v == indicators.stability][0]
        score.stability = stability_ranking_scores[index]

    # growth에 점수를 저장합니다.
    if indicators.growth is not None and indicators.growth != 0:
        index = [i for i, v in enumerate(growth_values) if v == indicators.growth][0]
        score.growth = growth_ranking_scores[index]

    # profitability에 점수를 저장합니다.
    if indicators.profitability is not None and indicators.profitability != 0:
        index = [i for i, v in enumerate(profitability_values) if v == indicators.profitability][0]
        score.profitability = profitability_ranking_scores[index]

    # revenue_growth에 점수를 저장합니다.
    if indicators.revenue_growth is not None and indicators.revenue_growth != 0:
        index = [i for i, v in enumerate(revenue_growth_values) if v == indicators.revenue_growth][0]
        score.revenue_growth = revenue_growth_ranking_scores[index]

    # operating_profit_growth에 점수를 저장합니다.
    if indicators.operating_profit_growth is not None and indicators.operating_profit_growth != 0:
        index = [i for i, v in enumerate(operating_profit_growth_values) if v == indicators.operating_profit_growth][0]
        score.operating_profit_growth = operating_profit_growth_ranking_scores[index]

    # 변경사항을 저장합니다.
    score.save()