from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from bs4 import BeautifulSoup
import requests
from rest_framework.decorators import (api_view)
import time
import re
from .models import Company
import FinanceDataReader as fdr
from .models import FinancialStatement, FinancialStatement_2022, FinancialStatement_2021, CompanyIndicatorsScore, FinancialProductRank10


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

    data_list = []

    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        headers = [th.text.strip() for th in table.select('thead th')]
        rows = table.find_all('tr')

        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7: 
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

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

    data_list = []

    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        headers = [th.text.strip() for th in table.select('thead th')]
        rows = table.find_all('tr')

        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7:
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

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

    data_list = []

    count = 0
    table = soup.find('table', class_='table-stock')
    if table:
        headers = [th.text.strip() for th in table.select('thead th')]
        rows = table.find_all('tr')

        for row in rows:
            cols = row.find_all('td')
            if len(cols) >= 7: 
                stock_name = cols[0].find('div', class_='stock-name').text.strip()
                symbol = cols[0].find('div', class_='symbol').text.strip()
                price = cols[1].find('div', class_='price').text.strip()
                price_change_percent = cols[2].find('span', class_='ratio-box txt-num').text.strip()
                volume = cols[3].text.strip()
                trading_amount = cols[4].text.strip()
                market_cap_usd = cols[5].text.strip()

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
    url = "https://www.coindalin.com/"
    headers = {'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537."}

    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')

    crypto_data = []

    # 테이블에서 데이터 추출
    table = soup.find('ul', id='coinList', class_='table-main')
    rows = table.find_all('li', class_='tbody')

    count = 0
    
    for row in rows:
        rank = row.find('div', class_='rankNo').text.strip()
        symbol = row.find('div', class_='symbol').text.strip()

        style_attribute = row.find('span', class_='name')['style']
        image_url_match = re.search(r"url\('([^']+)'\)", style_attribute)
        
        if image_url_match:
            image_url = image_url_match.group(1)
        else:
            image_url = None

        name_element = row.find('span', class_='name')

        name_with_percent_change = name_element.text.strip()
        name = name_with_percent_change.split(' ')[0].strip()

        percent_change_match = re.search(r"[▼▲] (.+)%", name_with_percent_change)
        percent_change = percent_change_match.group(1) if percent_change_match else "0"

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
            'Fluctuation': percent_change,
            'Price': price,
            'Market Cap': market_cap,
            'Volume (24h)': volume_24h,
            'Circulating Supply': circulating_supply,
        }
        crypto_data.append(crypto_dict)

        count += 1
        if count >= 35:  # 처음 35개 데이터만 추출
            break  # 반복문 종료

    
    time.sleep(1)

    return JsonResponse(crypto_data, safe=False)

# 기업검색 기능
def search(request):
    query = request.GET.get('q', '')
    results = Company.objects.filter(company_name__icontains=query)
    data = list(results.values('company_name', 'company_stock_code', 'company_stock_type', 'company_image_url'))
    return JsonResponse(data, safe=False)

# 기엄검색 기능 (주식코드를 기준으로)
def get_company_data(request, company_stock_code):
    try:
        company = Company.objects.get(company_stock_code=company_stock_code)
        company_data = {
            'company_code': company.company_code,
            'company_name': company.company_name,
            'company_stock_code': company.company_stock_code,
            'company_real_industry_code': company.company_real_industry_code,
            'company_industry': company.company_industry,
            'company_stock_type': company.company_stock_type,
            'company_image_url': company.company_image_url,
        }

        return JsonResponse(company_data)
    except Company.DoesNotExist:
        return JsonResponse({'message': '해당 회사 정보를 찾을 수 없습니다.'}, status=404)

# 코스피
@api_view(['GET'])
def kospi(request):

    kospi = fdr.DataReader('KS11')

    kospi['전일 종가'] = kospi['Close'].shift(1)
    # 등락률 계산
    kospi['등락률'] = ((kospi['Close'] - kospi['전일 종가']) / kospi['전일 종가']) * 100

    latest_row = kospi.iloc[-1]
    
    kospi_data = {
        'name': 'KOSPI',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(kospi_data)

# 코스닥
@api_view(['GET'])
def kosdaq(request):
    kosdaq = fdr.DataReader('KQ11')

    kosdaq['전일 종가'] = kosdaq['Close'].shift(1)
    kosdaq['등락률'] = ((kosdaq['Close'] - kosdaq['전일 종가']) / kosdaq['전일 종가']) * 100

    latest_row = kosdaq.iloc[-1]
    
    kosdaq_data = {
        'name': 'KOSDAQ',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(kosdaq_data)

#환율
@api_view(['GET'])
def exchange_rate(request):

    exchange_data = fdr.DataReader('USD/KRW')

    # 가장 최근의 환율과 전일 환율
    latest_exchange_rate = exchange_data['Close'].iloc[-1]
    previous_day_exchange_rate = exchange_data['Close'].iloc[-2]

    # 환율 증감률을 계산
    exchange_rate_change = ((latest_exchange_rate - previous_day_exchange_rate) / previous_day_exchange_rate) * 100

    exchange_rate_info = {
        'currency_pair': 'USD/KRW',
        'exchange_rate_change': exchange_rate_change,
        'exchange_rate_change_state' : 1 if exchange_rate_change >= 0 else 0
    }

    return JsonResponse(exchange_rate_info)

# 코스피200
@api_view(['GET'])
def kospi200(request):

    kospi200 = fdr.DataReader('KS200')

    kospi200['전일 종가'] = kospi200['Close'].shift(1)

    # 등락률 계산
    kospi200['등락률'] = ((kospi200['Close'] - kospi200['전일 종가']) / kospi200['전일 종가']) * 100

    latest_row = kospi200.iloc[-1]
    kospi200_data = {
    'name': 'KOSPI 200',
    'fluctuation_rate': latest_row['등락률'],
    'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(kospi200_data)

# 나스닥
@api_view(['GET'])
def nasdaq(request):

    nasdaq = fdr.DataReader('IXIC')

    nasdaq['전일 종가'] = nasdaq['Close'].shift(1)

    # 등락률 계산
    nasdaq['등락률'] = ((nasdaq['Close'] - nasdaq['전일 종가']) / nasdaq['전일 종가']) * 100

    latest_row = nasdaq.iloc[-1]
    nasdaq_data = {
        'name': 'NASDAQ',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(nasdaq_data)

# 다우존스
@api_view(['GET'])
def dowjones(request):

    dow_jones  = fdr.DataReader('DJI')

    # 전일 종가 정보 추가
    dow_jones['전일 종가'] = dow_jones['Close'].shift(1)

    # 등락률 계산
    dow_jones['등락률'] = ((dow_jones['Close'] - dow_jones['전일 종가']) / dow_jones['전일 종가']) * 100

    # 가장 최근의 등락률과 등락 여부 판단
    latest_row = dow_jones.iloc[-1]
    dow_jones_data = {
        'name': 'Dow Jones',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(dow_jones_data)


# S&P 500지수
@api_view(['GET'])
def sp500 (request):

    # S&P 500 지수 정보 가져오기
    sp500 = fdr.DataReader('US500')

    # 전일 종가 정보 추가
    sp500['전일 종가'] = sp500['Close'].shift(1)

    # 등락률 계산
    sp500['등락률'] = ((sp500['Close'] - sp500['전일 종가']) / sp500['전일 종가']) * 100

    # 가장 최근의 등락률과 등락 여부 판단
    latest_row = sp500.iloc[-1]
    sp500_data = {
        'name': 'S&P 500',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(sp500_data)

# S&P 500 VIX
@api_view(['GET'])
def vix(request):

    VIX = fdr.DataReader('VIX')

    # 전일 종가 정보 추가
    VIX['전일 종가'] = VIX['Close'].shift(1)

    # 등락률 계산
    VIX['등락률'] = ((VIX['Close'] - VIX['전일 종가']) / VIX['전일 종가']) * 100

    # 가장 최근의 등락률과 등락 여부 판단
    latest_row = VIX.iloc[-1]
    VIX_data = {
        'name': 'VIX',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(VIX_data)


# KRX 100
@api_view(['GET'])
def krx(request):

    KRX = fdr.DataReader('KS11')

    # 전일 종가 정보 추가
    KRX['전일 종가'] = KRX['Close'].shift(5)

    # 등락률 계산
    KRX['등락률'] = ((KRX['Close'] - KRX['전일 종가']) / KRX['전일 종가']) * 100

    # 가장 최근의 등락률과 등락 여부 판단
    latest_row = KRX.iloc[-1]
    KRX_data = {
        'name': 'KRX',
        'fluctuation_rate': latest_row['등락률'],
        'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
    }

    return JsonResponse(KRX_data)

# 코인 거래량
@api_view(['GET'])
def crypto_volume(request):

    symbol = 'BTC/KRW'

    # 가상화폐의 거래량
    crypto_data = fdr.DataReader(symbol, start='2023-01-01', end='2023-12-31')

    # 거래량 증감률 계산
    crypto_data['Volume_change'] = crypto_data['Volume'].pct_change() * 100

    # 어제와 오늘의 거래량 증감률 차이 계산
    crypto_data['Volume_change_diff'] = crypto_data['Volume_change'].diff()

    # 오늘 거래량 증감률 차이 출력
    today_volume_change_diff = crypto_data['Volume_change_diff'].iloc[-1]


    cryptocurrency_volume_data = {
        'name': 'cryptocurrency_volume',
        'fluctuation_rate': today_volume_change_diff,
        'fluctuation_state': 1 if today_volume_change_diff >= 0 else 0,
    }

    return JsonResponse(cryptocurrency_volume_data)

# 공포지수
@api_view(['GET'])
def fear_index(request):
    symbol = 'BTC/KRW'

    # 가상화폐의 시가총액
    crypto_data = fdr.DataReader(symbol, start='2023-01-01', end='2023-12-31')

    # 종가 데이터와 거래량 데이터를 사용하여 시가총액 증감률 계산
    crypto_data['MarketCap_change'] = (crypto_data['Close'] * crypto_data['Volume']).pct_change() * 100

    # 어제와 오늘의 시가총액 증감률 차이 계산
    crypto_data['MarketCap_change_diff'] = crypto_data['MarketCap_change'].diff()

    # 최소 및 최대값 구하기
    min_change = crypto_data['MarketCap_change_diff'].min()
    max_change = crypto_data['MarketCap_change_diff'].max()

    crypto_data['Scaled_MarketCap_change'] = ((crypto_data['MarketCap_change_diff'] - min_change) / (max_change - min_change)) * 100

    # 오늘 시가총액 증감률 차이를 스케일링된 값으로 가져옴
    today_scaled_marketcap_change_diff = crypto_data['Scaled_MarketCap_change'].iloc[-1]

    today_marketcap_change_diff_data = {
        'name': 'cryptocurrency_market_cap',
        'fear_index': int(today_scaled_marketcap_change_diff),
    }

    return JsonResponse(today_marketcap_change_diff_data)

# 가상화폐 시가총액
@api_view(['GET'])
def bitcoin(request):
    symbol = 'BTC/KRW'

    # 가상화폐의 종가
    crypto_data = fdr.DataReader(symbol, start='2023-01-01', end='2023-12-31')

    # 어제와 오늘의 종가 차이 계산
    crypto_data['Close_change'] = crypto_data['Close'].diff()

    # 어제 종가 대비 오늘 종가 증감률 계산
    crypto_data['Close_change_rate'] = (crypto_data['Close_change'] / crypto_data['Close'].shift(1)) * 100

    # 오늘 종가 증감률 출력
    today_close_change_rate = crypto_data['Close_change_rate'].iloc[-1]

    today_close_change_rate_data = {
        'name': 'bitcoin_close_price_rate',
        'fluctuation_rate': today_close_change_rate,
        'fluctuation_state': 1 if today_close_change_rate >= 0 else 0,
    }

    return JsonResponse(today_close_change_rate_data)

# 매출액 3개년
@api_view(['GET'])
def company_revenue(request, company_stock_code):
    try:
        company = Company.objects.get(company_stock_code=company_stock_code)
        company_seq = company.seq

        revenue_2023 = FinancialStatement.objects.filter(company_seq=company_seq).values('revenue')[0]['revenue']
        revenue_2022 = FinancialStatement_2022.objects.filter(company_seq=company_seq).values('revenue')[0]['revenue']
        revenue_2021 = FinancialStatement_2021.objects.filter(company_seq=company_seq).values('revenue')[0]['revenue']

        data = {
            'revenue_2023': revenue_2023,
            'revenue_2022': revenue_2022,
            'revenue_2021': revenue_2021,
        }

        return JsonResponse(data)

    except Company.DoesNotExist:
        return JsonResponse({'message': '해당 회사 정보를 찾을 수 없습니다.'}, status=404)
    
# 당기순이익 3개년
@api_view(['GET'])
def company_net_income(request, company_stock_code):
    try:
        company = Company.objects.get(company_stock_code=company_stock_code)
        company_seq = company.seq

        net_income_2023 = FinancialStatement.objects.filter(company_seq=company_seq).values('net_income')[0]['net_income']
        net_income_2022 = FinancialStatement_2022.objects.filter(company_seq=company_seq).values('net_income')[0]['net_income']
        net_income_2021 = FinancialStatement_2021.objects.filter(company_seq=company_seq).values('net_income')[0]['net_income']

        data = {
            'net_income_2023': net_income_2023,
            'net_income_2022': net_income_2022,
            'net_income_2021': net_income_2021,
        }

        return JsonResponse(data)

    except Company.DoesNotExist:
        return JsonResponse({'message': '해당 회사 정보를 찾을 수 없습니다.'}, status=404)
    
# 재무제표
def get_financial_statements(request, company_stock_code):
    try:
        financial_statements = FinancialStatement.objects.filter(company_seq__company_stock_code=company_stock_code).values()
        return JsonResponse(list(financial_statements), safe=False)
    except FinancialStatement.DoesNotExist:
        return JsonResponse({'message': '해당 회사의 재무제표 정보를 찾을 수 없습니다.'}, status=404)


# 회사지표점수
def get_company_indicators_score(request, company_stock_code):
    try:
        company = Company.objects.get(company_stock_code=company_stock_code)

        indicators_score = CompanyIndicatorsScore.objects.filter(company_seq=company)

        indicators_score_data = list(indicators_score.values())

        return JsonResponse(indicators_score_data, safe=False)
    except Company.DoesNotExist:
        return JsonResponse({'message': '해당 회사 정보를 찾을 수 없습니다.'}, status=404)
    except CompanyIndicatorsScore.DoesNotExist:
        return JsonResponse({'message': '해당 회사의 지표 정보를 찾을 수 없습니다.'}, status=404)
    

# 1금융권/예금 상품 추천
@api_view(['GET'])
def get_random_deposit(request):
    random_product = FinancialProductRank10.objects.filter(
        co_type_nm="예금",
        bank_type_nm="1금융권"
    ).order_by('?').first()  # '?'를 사용하여 랜덤 선택

    if random_product:
        serialized_data = {
            'fin_prdt_nm': random_product.fin_prdt_nm,
            'kor_co_nm': random_product.kor_co_nm,
            'co_type_nm': random_product.co_type_nm,
            'bank_type_nm': random_product.bank_type_nm,
            'intr_rate_type_nm': random_product.intr_rate_type_nm,
            'save_trm': random_product.save_trm,
            'intr_rate2': random_product.intr_rate2,
            'fin_prdt_cd': random_product.fin_prdt_cd,
            'spcl_cnd': random_product.spcl_cnd,
            'etc_note': random_product.etc_note,
        }
        return JsonResponse(serialized_data)
    else:
        return JsonResponse({'message': 'No matching records found'}, status=404)
    

# 2금융권/예금 상품 추천
@api_view(['GET'])
def get_random_deposit2(request):
    random_product = FinancialProductRank10.objects.filter(
        co_type_nm="예금",
        bank_type_nm="2금융권"
    ).order_by('?').first()  # '?'를 사용하여 랜덤 선택

    if random_product:
        serialized_data = {
            'fin_prdt_nm': random_product.fin_prdt_nm,
            'kor_co_nm': random_product.kor_co_nm,
            'co_type_nm': random_product.co_type_nm,
            'bank_type_nm': random_product.bank_type_nm,
            'intr_rate_type_nm': random_product.intr_rate_type_nm,
            'save_trm': random_product.save_trm,
            'intr_rate2': random_product.intr_rate2,
            'fin_prdt_cd': random_product.fin_prdt_cd,
            'spcl_cnd': random_product.spcl_cnd,
            'etc_note': random_product.etc_note,
        }
        return JsonResponse(serialized_data)
    else:
        return JsonResponse({'message': 'No matching records found'}, status=404)
    
# 1금융권/적금 상품 추천
@api_view(['GET'])
def get_random_saving(request):
    random_product = FinancialProductRank10.objects.filter(
        co_type_nm="적금",
        bank_type_nm="1금융권"
    ).order_by('?').first()  # '?'를 사용하여 랜덤 선택

    if random_product:
        serialized_data = {
            'fin_prdt_nm': random_product.fin_prdt_nm,
            'kor_co_nm': random_product.kor_co_nm,
            'co_type_nm': random_product.co_type_nm,
            'bank_type_nm': random_product.bank_type_nm,
            'intr_rate_type_nm': random_product.intr_rate_type_nm,
            'save_trm': random_product.save_trm,
            'intr_rate2': random_product.intr_rate2,
            'fin_prdt_cd': random_product.fin_prdt_cd,
            'spcl_cnd': random_product.spcl_cnd,
            'etc_note': random_product.etc_note,
        }
        return JsonResponse(serialized_data)
    else:
        return JsonResponse({'message': 'No matching records found'}, status=404)
    

# 2금융권/적금 상품 추천
@api_view(['GET'])
def get_random_saving2(request):
    random_product = FinancialProductRank10.objects.filter(
        co_type_nm="적금",
        bank_type_nm="2금융권"
    ).order_by('?').first()  # '?'를 사용하여 랜덤 선택

    if random_product:
        serialized_data = {
            'fin_prdt_nm': random_product.fin_prdt_nm,
            'kor_co_nm': random_product.kor_co_nm,
            'co_type_nm': random_product.co_type_nm,
            'bank_type_nm': random_product.bank_type_nm,
            'intr_rate_type_nm': random_product.intr_rate_type_nm,
            'save_trm': random_product.save_trm,
            'intr_rate2': random_product.intr_rate2,
            'fin_prdt_cd': random_product.fin_prdt_cd,
            'spcl_cnd': random_product.spcl_cnd,
            'etc_note': random_product.etc_note,
        }
        return JsonResponse(serialized_data)
    else:
        return JsonResponse({'message': 'No matching records found'}, status=404)
    

# 펀드 상품 추천
@api_view(['GET'])
def get_random_fund(request):
    random_product = FinancialProductRank10.objects.filter(
        co_type_nm="펀드",
        bank_type_nm="펀드"
    ).order_by('?').first()  # '?'를 사용하여 랜덤 선택

    if random_product:
        serialized_data = {
            'fin_prdt_nm': random_product.fin_prdt_nm,
            'kor_co_nm': random_product.kor_co_nm,
            'co_type_nm': random_product.co_type_nm,
            'bank_type_nm': random_product.bank_type_nm,
            'intr_rate_type_nm': random_product.intr_rate_type_nm,
            'save_trm': random_product.save_trm,
            'intr_rate2': random_product.intr_rate2,
            'fin_prdt_cd': random_product.fin_prdt_cd,
            'spcl_cnd': random_product.spcl_cnd,
            'etc_note': random_product.etc_note,
        }
        return JsonResponse(serialized_data)
    else:
        return JsonResponse({'message': 'No matching records found'}, status=404)


