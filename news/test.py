from bs4 import BeautifulSoup
import requests
import re

url = "https://www.coindalin.com/"
headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

res = requests.get(url, headers=headers)
soup = BeautifulSoup(res.text, 'lxml')

crypto_data = []

# 테이블에서 데이터 추출
table = soup.find('ul', id='coinList', class_='table-main')
rows = table.find_all('li', class_='tbody')


for row in rows:
    rank = row.find('div', class_='rankNo').text.strip()
    symbol = row.find('div', class_='symbol').text.strip()

    style_attribute = row.find('span', class_='name')['style']
    image_url_match = re.search(r"url\('([^']+)'\)", style_attribute)
    
    if image_url_match:
        image_url = image_url_match.group(1)
    else:
        image_url = None

    name = row.find('span', class_='name').text.strip()

    price = row.find('div', class_='market_cap_krw').text.strip()
    market_cap = row.find_all('div', class_='market_cap_krw')[1].text.strip()
    volume_24h = row.find_all('div', class_='market_cap_krw')[2].text.strip()


    circulating_supply = row.find('span', class_='circulating_supply').text.strip()

    # 데이터를 딕셔너리로 저장
    crypto_dict = {
        'Rank': rank,
        'Symbol': symbol,
        'image_url' : image_url,
        'Name': name,
        'Price': price,
        'Market Cap': market_cap,
        'Volume (24h)': volume_24h,
        'Circulating Supply': circulating_supply,
    }

    crypto_data.append(crypto_dict)

# 결과 출력
for crypto in crypto_data:
    print(crypto)