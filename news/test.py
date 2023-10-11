import FinanceDataReader as fdr



kospi200 = fdr.DataReader('KS200')

print(kospi200)
# kospi200['전일 종가'] = kospi200['Close'].shift(1)

# print

# # 등락률 계산
# kospi200['등락률'] = ((kospi200['Close'] - kospi200['전일 종가']) / kospi200['전일 종가']) * 100

# latest_row = kospi200.iloc[-1]
# kospi200_data = {
# 'name': 'KOSPI 200',
# 'fluctuation_rate': latest_row['등락률'],
# 'fluctuation_state': 1 if latest_row['등락률'] >= 0 else 0,
# }

# print(kospi200_data)