from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from pymongo import MongoClient
from bs4 import BeautifulSoup
from .models import News
import requests
from rest_framework.decorators import (api_view)


# 메인 뉴스 목록
@api_view(['GET'])
def MainNewsList(request):
    url = f'https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    newslist = soup.select(".sh_list .sh_item")
    news_data = []

    for news in newslist:
        try:
            image_url = news.select_one(".sh_thumb_link img")['src']
        except Exception:
            continue

        title = news.select_one(".sh_text_headline").text.strip()
        content = news.select_one(".sh_text_lede").text.strip()

        data_dict = {
            'title':title,
            'content':content,
            'image_url':image_url
        }

        news_data.append(data_dict)

    return JsonResponse(news_data, safe=False)


# 뉴스 속보 목록
@api_view(['GET'])
def BreakingNewsList(request):
    url = f'https://www.mk.co.kr/news/economy/latest/'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    newslist = soup.select(".news_list .news_node")
    news_data = []

    for news in newslist:
        try:
            image_url = news.select_one("img.lazy")['data-src']
        except Exception:
            continue

        title = news.select_one(".news_ttl").text.strip()
        content = news.select_one(".news_desc").text.strip()
        time = news.select_one(".time_info").text.strip()

        data_dict = {
            'title':title,
            'content':content,
            'image_url':image_url,
            'time' : time
        }

        news_data.append(data_dict)

    return JsonResponse(news_data, safe=False)


# 산업별 뉴스 목록
@api_view(['GET'])
def IndustryByNewsList(request, category):
    url = f'https://www.yna.co.kr/industry/{category}?site=navi_industry_depth02'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    newslist = soup.select(".list .item-box01")
    news_data = []

    for news in newslist:
        try:
            image_url = "https:" + news.select_one(".img-con img")['src']
        except Exception:
            continue

        try:
            content = news.select_one(".lead").text.strip()    
        except Exception:
            continue

        title = news.select_one(".tit-news").text.strip()

        data_dict ={
            'title': title,
            'content' : content,
            'image_url': image_url,
        }

        news_data.append(data_dict)

    return JsonResponse(news_data, safe=False)





# 기업별 뉴스 + 감정분석
@api_view(['GET'])
def CompanyByNewsList(request, company_code):

    url = f'https://finance.naver.com/item/news_news.nhn?code={company_code}&page=1'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    newslist = soup.select(".type5 tbody tr.first, .type5 tbody tr.last, .type5 tbody tr.relation_tit")
    news_data = []

    for news in newslist:

        title = news.select_one(".tit").text.strip()
        link = "https://finance.naver.com" + news.select_one(".tit")['href']
        time = news.select_one(".date").text.strip()

        data_dict ={
            'title': title,
            'link' : link,
            'time': time,
        }

        news_data.append(data_dict)

    return JsonResponse(news_data, safe=False)