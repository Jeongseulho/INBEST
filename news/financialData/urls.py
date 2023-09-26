from django.urls import path
from . import views

urlpatterns = [
    path("koreatop/", views.koreatop),
    path("koreahigh/", views.koreahigh),
    path("korealow/", views.korealow),
    path("usatop/", views.usatop),
    path("usahigh/", views.usahigh),
    path("usalow/", views.usalow),
    path("cointop/", views.cointop),
    path("coinhigh/", views.coinhigh),
    path("coinlow/", views.coinlow),
]