import OpenDartReader

api_key = 'ac30ceeeb8ac94447e5e065876a5783d79e5b3ec'

dart = OpenDartReader(api_key) 

a = dart.list('삼성전자')
print(a)