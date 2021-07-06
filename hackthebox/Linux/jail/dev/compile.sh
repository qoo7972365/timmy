gcc -o jail jail.c -m32 -z execstack
service jail stop
cp jail /usr/local/bin/jail
service jail start
