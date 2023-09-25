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

    time.sleep(1)

    return JsonResponse(news_data, safe=False)


# 뉴스 속보 목록
@api_view(['GET'])
def BreakingNewsList(request):
    url = f'https://www.mk.co.kr/news/economy/latest/'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers, verify=False)
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
        newstime = news.select_one(".time_info").text.strip()

        data_dict = {
            'title':title,
            'content':content,
            'image_url':image_url,
            'time' : newstime,
            'link_url' : link_url
        }

        news_data.append(data_dict)

    time.sleep(1)

    return JsonResponse(news_data, safe=False)


# 산업별 뉴스 목록 ver1
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

    time.sleep(1)

    return JsonResponse(news_data, safe=False)

# 산업별 뉴스 목록 ver2
@api_view(['GET'])
def IndustryByNewsList2(request, category):
    url = f'https://www.yna.co.kr/industry/{category}?site=navi_industry_depth02'
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers, verify=False)
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

    time.sleep(1)

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
        newstime = news.select_one(".date").text.strip()
        info = news.select_one(".info").text.strip()

        if info == '헤럴드경제':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/016/2019/01/22/logo_016_6_20190122142922.png'
        elif info == '뉴시스':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/003/2019/01/23/logo_003_6_20190123191323.jpg'
        elif info == '한국경제':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/015/2020/09/15/logo_015_6_20200915190950.png'
        elif info == '파이낸셜뉴스':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/014/2020/09/18/logo_014_6_20200918175030.png'
        elif info == '매일경제':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/009/2018/10/05/logo_009_6_20181005175405.png'
        elif info == '머니투데이':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/008/2020/09/24/logo_008_6_20200924115228.png'
        elif info == '연합뉴스':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/001/2020/09/15/logo_001_6_20200915184213.png'
        elif info == '뉴스1':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/001/2020/09/15/logo_001_6_20200915184213.png'
        elif info == '아시아경제':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/277/2020/09/15/logo_277_6_20200915184035.png'
        elif info == '이데일리':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/018/2020/09/15/logo_018_6_20200915185838.png'
        elif info == '조선비즈':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/366/2020/09/15/logo_366_6_20200915190031.png'
        elif info == '이코노미스트':
            image_url = 'https://mimgnews.pstatic.net/image/upload/office_logo/243/2020/09/15/logo_243_6_20200915185921.png'
        else:
            image_url = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHcAAAB3CAMAAAAO5y+4AAAAclBMVEU/Y7////86YL4oVLgwWbtrgcTn6fC5wNk3Xr3V2uzb3+5RbbgzW7v7+/rR1+rJ0OZZc75IZ7tDZLuCksXw8fR1iMGIl8euuNiQn85ObL5ie8Th5fGptNcWSrKfrNPByN5tgb6WosuImc8DQ69gd7p+kcqiWtMbAAACqElEQVRoge2W7ZaaMBBAM0MIgwZBjCKCoK28/ys2IaKworUfe/acdu6f3QjJDZPJJEIwDMMwDMMwDMMwDMMwDMMw/ws4y+drD+EMh08XBwuYoQo+3RvPeRe/7UW3cn/szfOHDjTVDA6UDhSHUry1Si+9eds+iE/luJWdvAO3hSMrTZ2bVNofSPcQzU/itVdVH71rOOt7S0Zw7P/RNSilzKVM6lxZr45uY30jMYP3mjiOFUCyilfmpRdLaMbeAsTgXWpCtF7dewvYbjylmMN7V8cgCBNQ3+UxnXqFjZTj+jptIZKj3iu40NUbFUWUlcmiSLz3SJOe8147lmxABUJOvWp/Ze0/V6/AZDextHONJN7irC6lMQq89/WWuHuFrB68sRnI3OjysIA2MZ3ucwVpAyqF1s2jj7PW0sZ5rYbvfVX77l6ksJp6EZfZgN2YKM4J1MFWQbu00aMsBRVagdkhWm9zrqM0TGpx9Z62nmxWfPPigXRHYy8uJ2CmYHWRgqgG2BOdAQpJIghXsMh0kyhVKef9mM+FfumVceZCMvIGary1TCnSnQ+wztoQcZtm/YhEuwhdhZFa6GEfiXw98Fh6Jt5A+UW+e2nbdQXUXbeHtuu63K7EtRM6P91WzuesjBqNpWm892fcvRXstZiuL8k9bKRe2lW1dScYo+WkaacQuF4YaFcn6ePDZ95AHiswBzndv0LvbbnBC9Ra4MWoO+ZcqDFLvHrdbJHOk3fDGbH3Vk3d2EK1sH+qZ96sjUZs9uNWVPbfm5ce3IyftXMZ/fo8st6d87qc9CfOANG45RIssHXWo8rJQ/k8zs+8GBb2UCuL7o2TjXYDm/kU/gWvz1wxO+NH8Y03Xv7b9413+ar71VfdJ7/q/swwDMMwDMMwDMMwDMMwDMP8g/wAcb0ujFI5nOoAAAAASUVORK5CYII='



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
            'time': newstime,
            'sentiment_analysis': sentiment_analysis,
            'image_url' : image_url,
            'info' : info
        }


        news_data.append(data_dict)

    time.sleep(1)

    return JsonResponse(news_data, safe=False)