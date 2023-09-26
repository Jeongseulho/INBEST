from bs4 import BeautifulSoup
import requests
from textblob import TextBlob
from googletrans import Translator
import time
import json



url = "https://finance.naver.com/sise/lastsearch2.naver"
headers = {
    'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."
}

res = requests.get(url, headers=headers)
soup = BeautifulSoup(res.text, 'html.parser')

# 데이터를 저장할 리스트
data_list = []

# 테이블에서 데이터 추출
table = soup.find('table', class_='type_5')
if table:
    rows = table.find_all('tr')
    for row in rows:
        cols = row.find_all('td')
        if len(cols) >= 10:  # 데이터가 있는 행인 경우
            rank = cols[0].text.strip()
            company_name = cols[1].text.strip()
            search_ratio = cols[2].text.strip()
            current_price = cols[3].text.strip()
            yesterday_price_change = cols[4].text.strip()
            price_change_ratio = cols[5].text.strip()
            volume = cols[6].text.strip()
            market_capitalization = cols[7].text.strip()
            net_profit = cols[8].text.strip()
            operating_profit_growth_rate = cols[9].text.strip()

            # 추출한 데이터를 딕셔너리로 저장
            data_dict = {
                '순위': rank,
                '종목명': company_name,
                '검색비율': search_ratio,
                '현재가': current_price,
                '전일비': yesterday_price_change,
                '등락률': price_change_ratio,
                '거래량': volume,
                '시가총액': market_capitalization,
                '당기순이익': net_profit,
                '영업이익증가율': operating_profit_growth_rate,
            }
            data_list.append(data_dict)

# 결과를 JSON 형식으로 출력
print(json.dumps(data_list, ensure_ascii=False, indent=4))