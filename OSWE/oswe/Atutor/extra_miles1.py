import hashlib, string, itertools, re, sys
import string
import random
#hash = sha1($_REQUEST['id'] + $_REQUEST['g'] + $row['password']);
password = "admin123456"
memberid = '4'
gen = '18861'
strings = memberid+gen+password

def gen_code(strings):
	hash = hashlib.md5(strings.encode('utf-8')).hexdigest()
	hash_bit = hash[5:20]
	print (hash)
	print (hash_bit)
	#if 	re.match(r'0+[eE]\d+$', hash_bit):
	#	print("yes")
	#else:
	#	passf
	



if __name__ == "__main__":
	gen_code(strings)
