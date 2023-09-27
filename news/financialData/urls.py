from django.urls import path
from . import views

urlpatterns = [
    path("korserach/", views.koreaSearch),
    path("koreahigh/", views.koreahigh),
    path("korearise/", views.korearise),
    path("usahigh/", views.usahigh),
    path("usastock/", views.usastock),
    path("usatop/", views.usatop),
    path("cointop/", views.cointop),
]