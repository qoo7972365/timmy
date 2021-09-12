import requests,sys

target = "http://bassmaster:8080/batch"
request_1 = '{"method":"get","path":"/profile"}'
request_2 = '{"method":"get","path":"/item"}'
request_3 = '{"method":"get","path":"/item/$1.id"}'
json = '{"requests":[%s,%s,%s]}' % (request_1, request_2, request_3)
r = requests.post(target, json)
print (r.text)

