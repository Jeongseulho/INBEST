from django.urls import path
from . import views
from .views import search

urlpatterns = [
    path("korserach/", views.koreaSearch),
    path("koreahigh/", views.koreahigh),
    path("korearise/", views.korearise),
    path("usahigh/", views.usahigh),
    path("usastock/", views.usastock),
    path("usatop/", views.usatop),
    path("cointop/", views.cointop),
    path("kospi/", views.kospi),
    path('search/', search, name='search'),
    path("kosdaq/", views.kosdaq),
    path("exchange_rate/", views.exchange_rate),
    path("kospi200/", views.kospi200),
    path("nasdaq/", views.nasdaq),
    path("dowjones/", views.dowjones),
    path("sp500/", views.sp500),
    path("vix/", views.vix),
    path("krx/", views.krx),
    path("crypto/", views.crypto_volume),
    path("fearindex/", views.fear_index),
    path("crypto-market-cap/", views.bitcoin),
]