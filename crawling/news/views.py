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
    response = requests.get(url,headers=headers)
    soup = BeautifulSoup(response.text,'html.parser')


    


    return

# 뉴스 속보 목록
@api_view(['GET'])
def BreakingNewsList(request):
    return

# 산업별 뉴스 목록
@api_view(['GET'])
def IndustryByNewsList(request):
    return

# 기업별 뉴스
@api_view(['GET'])
def CompanyByNewsList(request):
    return


# from django.shortcuts import render
# from django.http import JsonResponse
# import requests
# from bs4 import BeautifulSoup
# from .models import News

# # Create your views here.


# def my_view(request):
#     url = f'https://news.naver.com/'
#     headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}
#     response = requests.get(url,headers=headers)
#     soup = BeautifulSoup(response.text,'html.parser')

#     my_news = soup.select('#main_content > div > div._persist > div.section_headline > ul > li:nth-child(1) > div.sh_text')
#     my_news_content = soup.select('#main_content > div > div._persist > div.section_headline > ul > li:nth-child(1) > div.sh_text > a')
#     my_news_writing = soup.select('#main_content > div > div._persist > div.section_headline > ul > li:nth-child(1) > div.sh_text > div.sh_text_lede')
#     my_news_image = soup.select('#main_content > div > div._persist > div.section_headline > ul > li:nth-child(1) > div.sh_thumb > div > a > img')

#     nnews = list()

#     for news in my_news: # 뉴스 제목을 리스트에 append
#         nnews.append(news.text.strip())
         
#     for i in range(len(nnews)):
#         title = my_news[i].text.strip()
#         link = my_news[i].get('href')
#         content = my_news_content[i].text.strip()
#         writing = my_news_writing[i].text.strip()

#         try: #list index out of range 방지를 위한 예외처리
#             image_s = my_news_image[i].get('src')
#             image = my_news_image[i].get('src').replace('nf132_90','w647')
#         except:
#             image_s="NO IMAGE"
#             image = "NO IMAGE"

#         news_obj = News(
#             title=title,
#             link=link,
#             content=content,
#             writing=writing,
#             image=image if image != "NO IMAGE" else None,
#             image_s=image_s if image_s != "NO IMAGE" else None,
#         )
#         news_obj.save()

#     return JsonResponse({"message": "Scraping done."})


# def news_view(request):
#     news_list = News.objects.all()  # 모든 뉴스 객체를 가져옵니다.
    
#     return render(request, 'news/news.html', {'news_list': news_list})