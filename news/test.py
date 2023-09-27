from bs4 import BeautifulSoup
import requests
import re

url = "https://finance.naver.com/sise/sise_index.naver?code=KOSPI"
headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

res = requests.get(url, headers=headers)
soup = BeautifulSoup(res.text, 'lxml')

data = []
