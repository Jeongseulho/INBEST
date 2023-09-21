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

        try:
            link_url = news.select_one(".news_node a")['href']
        except Exception:
            continue
            
        title = news.select_one(".news_ttl").text.strip()
        content = news.select_one(".news_desc").text.strip()
        time = news.select_one(".time_info").text.strip()

        data_dict = {
            'title':title,
            'content':content,
            'image_url':image_url,
            'time' : time,
            'link_url' : link_url
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

        try:
            link_url = news.select_one(".news-con a")['href']
        except Exception:
            continue

        title = news.select_one(".tit-news").text.strip()

        data_dict ={
            'title': title,
            'content' : content,
            'image_url': image_url,
            'link_url' : link_url
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
    translator = Translator()

    for news in newslist:

        title = news.select_one(".tit").text.strip()
        link = "https://finance.naver.com" + news.select_one(".tit")['href']
        time = news.select_one(".date").text.strip()
        info = news.select_one(".info").text.strip()

        url_newslogo = f'https://namu.wiki/w/{info}'
        res_newslogo = requests.get(url_newslogo, headers=headers)
        soup_newslogo = BeautifulSoup(res_newslogo.text, 'lxml')
    

        image_element = soup_newslogo.select_one('.fSQMAUnZ .GJItanm2')['src']

        translation = translator.translate(title, dest='en').text


        blob = TextBlob(translation)
        sentiment_polarity = blob.sentiment.polarity
        sentiment_subjectivity = blob.sentiment.subjectivity

        sentiment_analysis = (sentiment_polarity + 1) * 50 * (1 - sentiment_subjectivity)

        if sentiment_analysis == 50:
            sentiment_analysis += (len(title)/10)

        if sentiment_analysis < 50:
            sentiment_analysis *= 1.5

        while sentiment_analysis > 100:
            sentiment_analysis -= 10

        print(sentiment_polarity, sentiment_subjectivity)

        data_dict ={
            'title': title,
            'link_url' : link,
            'time': time,
            'sentiment_analysis': sentiment_analysis,
            'image_url' : image_element,
            # 'info' : info
        }


        news_data.append(data_dict)

    return JsonResponse(news_data, safe=False)