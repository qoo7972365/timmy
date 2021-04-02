import requests
import json

def get(username):
    url = 'https://gitlab.com/api/v4/users?username=' + username
    firstresponse = requests.get(url, verify = True)

    firstdata = firstresponse.json()
    firstid = firstdata[0]['id']

    endpoint = 'https://gitlab.com/api/v4/users/' + str(firstid)
    response = requests.get(endpoint, verify = True)

    if response.status_code != 200:
        print('Status:', response.status_code, 'Problem with the request. Exiting.')
        exit()

    data = response.json()

    result = {}

    result['username'] = data['username']
    result['bio'] = data['bio']
    result['location'] = data['location']
    result['twitter'] = data['twitter']
    result['linkedin'] = data['linkedin']
    result['skype'] = data['skype']
    result['website_url'] = data['website_url']
    result['id'] = data['id']
    result['state'] = data['state']
    result['avatar_url'] = data['avatar_url']
    result['web_url'] = data['web_url']

    return result
