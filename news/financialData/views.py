from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from bs4 import BeautifulSoup
import requests
from rest_framework.decorators import (api_view)
import time



# 한국 최다검색 주식 목록
@api_view(['GET'])
def koreaSearch(request):
    url = "https://finance.naver.com/sise/lastsearch2.naver"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')
    
    # 데이터를 저장할 리스트
    data_list = []

    # 테이블에서 데이터 추출
    count = 0
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
                    '등락률': price_change_ratio,
                    '거래량': volume,
                    '시가총액': market_capitalization,
                    '당기순이익': net_profit,
                    '영업이익증가율': operating_profit_growth_rate,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료



    time.sleep(1)

    return JsonResponse(data_list, safe=False)

# 한국 시총순 주식 목록
@api_view(['GET'])
def koreahigh(request):
    url = "https://finance.naver.com/sise/sise_market_sum.naver"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')
    
    # 데이터를 저장할 리스트
    data_list = []

    # 테이블에서 데이터 추출
    count = 0
    table = soup.find('table', class_='type_2')
    if table:
        rows = table.find_all('tr')
        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 10:  # 데이터가 있는 행인 경우
                rank = cols[0].text.strip()
                company_name = cols[1].text.strip()
                current_price = cols[2].text.strip()
                price_change_ratio = cols[4].text.strip()
                volume = cols[6].text.strip()
                market_capitalization = cols[7].text.strip()
                net_profit = cols[8].text.strip()
                operating_profit_growth_rate = cols[9].text.strip()

                # 추출한 데이터를 딕셔너리로 저장
                data_dict = {
                    '순위': rank,
                    '종목명': company_name,
                    '현재가': current_price,
                    '등락률': price_change_ratio,
                    '시가총액': volume,
                    '상장주식수': market_capitalization,
                    '외국인비율': net_profit,
                    '거래량': operating_profit_growth_rate,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료


    time.sleep(1)

    return JsonResponse(data_list, safe=False)
    
# 한국 주식 등락률
@api_view(['GET'])
def korearise(request):
    url = "https://finance.naver.com/sise/sise_rise.naver"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')
    
    # 데이터를 저장할 리스트
    data_list = []

    # 테이블에서 데이터 추출
    count = 0
    table = soup.find('table', class_='type_2')
    if table:
        rows = table.find_all('tr')
        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 10:  # 데이터가 있는 행인 경우
                rank = cols[0].text.strip()
                company_name = cols[1].text.strip()
                current_price = cols[2].text.strip()
                price_change_ratio = cols[3].text.strip()
                volume = cols[4].text.strip()
                market_capitalization = cols[5].text.strip()

                # 추출한 데이터를 딕셔너리로 저장
                data_dict = {
                    '순위': rank,
                    '종목명': company_name,
                    '현재가': current_price,
                    '전일비': price_change_ratio,
                    '등락률': volume,
                    '거래량': market_capitalization,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료


    time.sleep(1)

    return JsonResponse(data_list, safe=False)

# 시가총액(나스닥)
@api_view(['GET'])
def usahigh(request):
    url = "https://www.hankyung.com/globalmarket/usa-marketcap-nasdaq"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    # Data storage list
    data_list = []

    # Find the table with the class "table-stock"
    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        # Extract table headers
        headers = [th.text.strip() for th in table.select('thead th')]

        # Extract table rows
        rows = table.find_all('tr')
        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7:  # Data exists in this row
                # Extract data from each column
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

                # Create a dictionary with extracted data
                data_dict = {
                    '종목명': stock_name,
                    '심볼': symbol,
                    '시세': price,
                    '등락률': price_change_percent,
                    '거래량': volume,
                    '거래대금': trading_amount,
                    '시가총액(백만 USD)': market_cap_usd,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료
    
    time.sleep(1)

    return JsonResponse(data_list, safe=False)

# 거래량순 (나스닥)
@api_view(['GET'])
def usastock(request):
    url = "https://www.hankyung.com/globalmarket/usa-volume-stock-nasdaq"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    # Data storage list
    data_list = []

    # Find the table with the class "table-stock"
    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        # Extract table headers
        headers = [th.text.strip() for th in table.select('thead th')]

        # Extract table rows
        rows = table.find_all('tr')
        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7:  # Data exists in this row
                # Extract data from each column
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

                # Create a dictionary with extracted data
                data_dict = {
                    '종목명': stock_name,
                    '심볼': symbol,
                    '시세': price,
                    '등락률': price_change_percent,
                    '거래량': volume,
                    '거래대금': trading_amount,
                    '시가총액(백만 USD)': market_cap_usd,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료

    time.sleep(1)

    return JsonResponse(data_list, safe=False)

@api_view(['GET'])
def usatop(request):
    url = "https://www.hankyung.com/globalmarket/usa-top-stock-nasdaq"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    # Data storage list
    data_list = []

    # Find the table with the class "table-stock"
    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        # Extract table headers
        headers = [th.text.strip() for th in table.select('thead th')]

        # Extract table rows
        rows = table.find_all('tr')
        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7:  # Data exists in this row
                # Extract data from each column
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

                # Create a dictionary with extracted data
                data_dict = {
                    '종목명': stock_name,
                    '심볼': symbol,
                    '시세': price,
                    '등락률': price_change_percent,
                    '거래량': volume,
                    '거래대금': trading_amount,
                    '시가총액(백만 USD)': market_cap_usd,
                }
                data_list.append(data_dict)

                count += 1
                if count >= 30:  # 처음 30개 데이터만 추출
                    break  # 반복문 종료
    
    time.sleep(1)

    return JsonResponse(data_list, safe=False)

# 코인 시총 순
@api_view(['GET'])
def cointop(request):
    url = "https://coinmarketcap.com/ko/gainers-losers/"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    print(soup)
    crypto_data = []

    table = soup.find('table', class_='sc-f61b15d2-3 iQblET cmc-table')
    rows = table.find_all('tr')
    print(table)
    
    if table:
        # Extract rows from the table
        rows = table.find_all('tr')
        for row in rows[1:]:  # Skip the header row
            columns = row.find_all('td')
            if len(columns) >= 10:  # Ensure there are enough columns
                coin_rank = columns[0].text.strip()
                coin_name = columns[2].text.strip()
                coin_symbol = columns[3].text.strip()
                coin_price = columns[4].text.strip()
                change_1h = columns[5].text.strip()
                change_24h = columns[6].text.strip()
                change_7d = columns[7].text.strip()
                market_cap = columns[8].text.strip()

                crypto_info = {
                    '랭킹': coin_rank,
                    '종목명': coin_name,
                    '심볼': coin_symbol,
                    '가격': coin_price,
                    '변동(1시간)': change_1h,
                    '변동(24시간)': change_24h,
                    '변동(7일)': change_7d,
                    '시가총액': market_cap,
                }
                crypto_data.append(crypto_info)

    
    time.sleep(1)

    return JsonResponse(crypto_data, safe=False)

@api_view(['GET'])
def coinhigh(request):
    return

@api_view(['GET'])
def coinlow(request):
    return