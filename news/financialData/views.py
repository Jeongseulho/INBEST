from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from pymongo import MongoClient
from bs4 import BeautifulSoup
from .models import News
import requests
from rest_framework.decorators import (api_view)
from textblob import TextBlob
from googletrans import Translator
import time



# 한국 최다거래 주식 목록
@api_view(['GET'])
def koreatop(request):
    url = "https://kr.investing.com/equities/south-korea/most-active-stocks"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')\
    
    newslist = soup.select(".datatable_body__tb4jX")
    print(newslist)
    news_data = []

    for news in newslist:
        try:
            image_url = news.select_one(".sh_thumb_link img")['src']
        except Exception:
            continue

        try:
            link_url = news.select_one(".sh_text a")['href']
        except Exception:
            continue

        title = news.select_one(".sh_text_headline").text.strip()
        content = news.select_one(".sh_text_lede").text.strip()

        data_dict = {
            'title':title,
            'content':content,
            'image_url':image_url,
            'link_url' : link_url
        }

        news_data.append(data_dict)

    time.sleep(1)

    return JsonResponse(news_data, safe=False)

@api_view(['GET'])
def koreahigh(request):
    return

@api_view(['GET'])
def korealow(request):
    return

@api_view(['GET'])
def usatop(request):
    return

@api_view(['GET'])
def usahigh(request):
    return

@api_view(['GET'])
def usalow(request):
    return

@api_view(['GET'])
def cointop(request):
    return

@api_view(['GET'])
def coinhigh(request):
    return

@api_view(['GET'])
def coinlow(request):
    return