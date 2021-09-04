import hashlib, string, itertools, re, sys
import string
import random
def gen_code(domain, id, date, prefix_length):
	while True:
		mailname = ''
		for i in range(3):
			mailname = mailname + chr(random.randint(97,122))
		
		strings = "teacher@example.com".format(mailname, domain) + date + id
		hash = hashlib.md5(strings.encode('utf-8')).hexdigest()[:10]
		print(hash)
		
		if re.match(r'0+[eE]\d+$', hash):
			print ("找到結果,{0}@offsec.local".format(mailname,))
		else:
			pass
def main():
	domain = sys.argv[1]
	id = sys.argv[2]
	creation_date = sys.argv[3]
	prefix_length = sys.argv[4]
if __name__ == "__main__":
	domain = "offsec.local"
	creation_date = "2018-05-10 19:28:05"
	id = "1"
	prefix_length = "3"
	gen_code(domain, id, creation_date, prefix_length)
