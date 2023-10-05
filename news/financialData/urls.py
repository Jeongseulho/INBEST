from django.urls import path
from . import views
from .views import search

urlpatterns = [
    path("korserach/", views.koreaSearch), # 한국 최다 검색 주식 목록
    path("koreahigh/", views.koreahigh), # 한국 최다 시총 주식 목록
    path("korearise/", views.korearise), # 한국 최대 상승 주식 목록
    path("usahigh/", views.usahigh), # 미국 최다 시총 주식 목록
    path("usastock/", views.usastock), # 미국 최다 거래 시총 주식 목록
    path("usatop/", views.usatop), # 미국 최대 상승 주식 목록
    path("cointop/", views.cointop), # 코인 최대 상승 목록
    path("kospi/", views.kospi), # 코스피 등락 정보
    path('search/', search, name='search'), # 기업정보 검색 기능
    path("kosdaq/", views.kosdaq), # 코스닥 등락 정보
    path("exchange_rate/", views.exchange_rate), # 환율 등락 정보
    path("kospi200/", views.kospi200), # 코스피 200 등락 정보
    path("nasdaq/", views.nasdaq), # 코스닥 등락 정보
    path("dowjones/", views.dowjones), # 다우존스 등락 정보
    path("sp500/", views.sp500), # sp500 등락 정보
    path("vix/", views.vix), # vix 등락 정보
    path("krx/", views.krx), # krx 등락 정보
    path("crypto/", views.crypto_volume), # 주요 코인 정보 목록
    path("fearindex/", views.fear_index), # 코인 공포 지수
    path("crypto-market-cap/", views.bitcoin), # 코인 시총 정보
    path("financial-statements/<str:company_stock_code>/", views.get_financial_statements, name='get_financial_statements'), # 재무제표 정보 가져오기
    path("company-revenue/<str:company_stock_code>/", views.company_revenue, name='company_revenue'), # 3개년 매출액 정보
    path("company-net-income/<str:company_stock_code>/", views.company_net_income, name='company_net_income'), # 3개년 순수익 정보
    path("indicators-score/<str:company_stock_code>/", views.get_company_indicators_score, name='get_company_indicators_score'), # 회사 요약 정보 가져오기
    path("deposit/", views.get_random_deposit), # 상위 랜덤 1금융 예금 목록 가져오기
    path("deposit2/", views.get_random_deposit2), # 상위 랜덤 2금융 예금 목록 가져오기
    path("saving/", views.get_random_saving), # 상위 랜덤 1금융 적금 목록 가져오기
    path("saving2/", views.get_random_saving2), # 상위 랜덤 2금윤 적금 목록 
    path("fund/", views.get_random_fund), # 상위 랜덤 펀드 목록 자겨오기
    path('company/<str:company_stock_code>/', views.get_company_data, name='get_company_data'), # 주식코드로 회사정보 가져오기
]