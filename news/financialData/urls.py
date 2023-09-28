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
]