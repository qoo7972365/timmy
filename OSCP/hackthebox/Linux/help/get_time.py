import requests
response = requests.head('http://help.htb')
headers = response.headers
date = headers['Date']
print(date)
print(type(date))