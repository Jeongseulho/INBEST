from django.urls import path
from . import views

# urlpatterns = [
#     path("", views.MainNewsList),  # 메인 뉴스 목록
#     path("breakingnews/", views.BreakingNewsList),  # 뉴스 속보 목록
#     path("industry/<str:category>/", views.IndustryByNewsList),  #산업별 뉴스 목록
#     path("company/<str:company_code>/", views.CompanyByNewsList),  # 기업별 뉴스
# ]

urlpatterns = [
    path("", views.MainNewsList),  # 메인 뉴스 목록
    path("breakingnews/", views.BreakingNewsList),  # 뉴스 속보 목록
    path("industry/<int:category_num_first>/", views.IndustryByNewsList3),  #산업별 뉴스 목록 2
    path("company/<str:company_code>/", views.CompanyByNewsList),  # 기업별 뉴스 / 감성분석
]
