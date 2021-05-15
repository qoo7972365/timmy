import requests

cookies = {
    'JSESSIONID.33b0e466': 'node01fszqdxgp05a51bfsd5q22zqre1.node0',
}

headers = {
    'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    'Content-Type': 'application/x-www-form-urlencoded',
    'Origin': 'http://127.0.0.1:8081',
    'Connection': 'keep-alive',
    'Referer': 'http://127.0.0.1:8081/login?from=%2F',
    'Upgrade-Insecure-Requests': '1',
}

data = {
  'j_username': 'admin',
  'j_password': 'dsfdsfsd',
  'from': '/',
  'Submit': 'Sign in'
}

response = requests.post('http://127.0.0.1:8081/j_acegi_security_check', headers=headers, cookies=cookies, data=data)
print(response.status_code)
