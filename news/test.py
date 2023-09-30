import FinanceDataReader as fdr

# 가상화폐 심볼 지정 (비트코인은 BTC/KRW)
symbol = 'BTC/KRW'

crypto_data = fdr.DataReader(symbol, start='2023-01-01', end='2023-12-31')

# 데이터프레임의 열 이름 출력
print(crypto_data.columns)