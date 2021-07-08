#!/bin/bash
nmap -sV -sC -Pn $1 > nmap.txt
nikto -h http://$1 > nikto.txt
enum4linux $1 > enum4linun.txt
snmpwalk -c public -v2c $1 > snmpwalkpublic.txt
snmp-check  $1> snmp.txt
gobuster dir  -u http://$1/ -w /usr/share/dirb/wordlists/common.txt -x txt,php > gobuster.txt
gobuster dir  -u http://$1/ -w /usr/share/dirbuster/wordlists/directory-list-2.3-medium.txt -x txt,php  > gobustermedium.txt
nmap -p- -Pn -T5  $1> nmapall.txt
nmap --script vuln -sV -p 135,80,139,445,80,49663,49666,49668 -Pn  $1  > nmapvuln.txt

sudo nmap -sU $1 > nmapuu.txt
davtest -url http://$1 > devtest.txt


###SMB
nmap --script=smb-enum-shares -Pn -p 139,445  $1 > nmapsmbshares.txt
nmap --script=smb-vuln* -Pn -p 139,445  $1 > nmapsmbvuln.txt
nmap --script=smb-vuln-ms17-010 -Pn -p 139,445 $1  > nmapsmbMS017.txt
nmap --script smb* -sV -p 139,445  $1  > nmapsmb.txt
nmap --script smb-os-discovery -sV -p 139,445  $1  > nmapsmbdiscovery.txt
nmap --script=smb-vuln-ms08-067  --script-args=unsafe=1 -sV -p 139,445  $1  > nmapsmbMS08-067.txt
nmap --script=smb-enum-shares.nse,smb-enum-users.nse $1 -p 445 > nampsamba.txt
nmap --script=nfs-ls,nfs-statfs,nfs-showmount $1 -p 111 > nmaprpc.txt

###RDP
nmap -Pn --script "rdp-enum-encryption" -p 3389 -T4 $1 > nmaprdpencry.txt
nmap -Pn --script "rdp-vuln-ms12-020" -p 3389 -T4 $1 > nmaprdpvuln.txt
nmap -Pn --script "rdp-ntlm-info" -p 3389 -T4 $1 > nmaprdpntlm.txt
###MSSQL
nmap --script ms-sql-info,ms-sql-empty-password,ms-sql-xp-cmdshell,ms-sql-config,ms-sql-ntlm-info,ms-sql-tables,ms-sql-hasdbaccess,ms-sql-dac,ms-sql-dump-hashes --script-args mssql.instance-port=1433,mssql.username=sa,mssql.password=,mssql.instance-name=MSSQLSERVER -sV -p 1433 $1


###默認redis嘗試
redis-cli -h $1 -a root
###檢測漏洞
if grep  "Voting System" *.txt; then  echo " Got Voting System,See~HTB LOVE" ;fi
if grep  "JSON parser" *.txt; then  echo " Got JSON parser,See~HTB Time" ;fi
if grep  "List Manager" *.txt; then  echo " Got List Manager,See~HTB Waldo" ;fi
if grep  "telnet" *.txt; then  echo " Got telnet,See~HTB Access" ;fi
if grep  "ColdFusion" *.txt; then  echo " Got ColdFusion,See~HTB ColdFusion oscp2 10.11.1.10" ;fi
if grep  "CFIDE" *.txt; then  echo " Got CFIDE,See~HTB Artic" ;fi
if grep  "Drupal" *.txt; then  echo " Got Drupal,See~HTB Armageddon or Bastard or Hawk" ;fi
if grep  "electron-builder" *.txt; then  echo " Got electron-builder,See~HTB Atom" ;fi
if grep  "ldap" *.txt; then  echo " Got ldap,See~HTB lightweight" ;fi
if grep  "MS08-067" *.txt; then  echo " Got MS08-067,See~HTB Legacy" ;fi
if grep  "MS17-010" *.txt; then  echo " Got MS17-010,See~TryHackeMe Blue or oscp2 ALICE" ;fi
if grep  "Medusa" *.txt; then  echo " Got Medusa,See~HTB Luanne" ;fi
if grep  "Parse YAML" *.txt; then  echo " Got Parse YAML,See~HTB Ophiuchi" ;fi
if grep  "opennetadmin" *.txt; then  echo " Got opennetadmin,See~HTB opennetadmin" ;fi
if grep  "teacher" *.txt; then  echo " Got teacher,See~HTB teacher" ;fi
if grep  "school" *.txt; then  echo " Got school,See~HTB teacher" ;fi
if grep  "jwt" *.txt; then  echo " Got jwt,See~HTB TheNoteBook" ;fi
if grep  "JSON Web Token" *.txt; then  echo " Got JSON Web Token,See~HTB TheNoteBook" ;fi
if grep  "Heartbleed" *.txt; then  echo " Got Heartbleed,See~HTB Valentine" ;fi
if grep  "Kerberos" *.txt; then  echo " Got Kerberos,See~HTB Sauna or Blackfield or AttacktiveDirect or TryHackMe windows_server" ;fi
if grep  "HttpFileServer" *.txt; then  echo " Got HttpFileServer,See~HTB optimum or oscp2 BETHANY" ;fi
if grep  "rejetto" *.txt; then  echo " Got rejetto,See~HTB optimum" ;fi
if grep  "torrent" *.txt; then  echo " Got torrent,See~HTB popcorn" ;fi
if grep  "cgi-bin" *.txt; then  echo " Got cgi-bin,See~HTB Shocker or oscp2 ALPHA" ;fi
if grep  "nvms" *.txt; then  echo " Got nvms,See~HTB ServMon" ;fi
if grep  "CMS Made Simple" *.txt; then  echo " Got CMS Made Simple,See~HTB writeup" ;fi
if grep  "3690" *.txt; then  echo " Got 3690 port SVN,See~HTB worker" ;fi
if grep  "SunSSH" *.txt; then  echo " Got SunSSH,See~HTB sunday" ;fi
if grep  "moodle" *.txt; then  echo " Got moodle,See~HTB schooled" ;fi
if grep  "snmp" *.txt; then  echo " Got snmp,See~HTB pit" ;fi
if grep  "seeddms" *.txt; then  echo " Got seeddms,See~HTB pit" ;fi
if grep  "Vanilla" *.txt; then  echo " Got Vanilla,See~HTB october" ;fi
if grep  "PRTG" *.txt; then  echo " Got PRTG,See~HTB netmon" ;fi
if grep  "Samba 3.0.20" *.txt; then  echo " Got Samba 3.0.20,See~HTB lame" ;fi
if grep  "vsftpd 2.3.4" *.txt; then  echo " Got vsftpd 2.3.4,See~HTB LaCasaDePapel" ;fi
if grep  "git" *.txt; then  echo " Got git,See~HTB laboratory" ;fi
if grep  "8.1.0-dev" *.txt; then  echo " Got php 8.1.0-dev,See~HTB Knife" ;fi
if grep  "IRC" *.txt; then  echo " Got IRC,See~HTB Irked" ;fi
if grep  "nfs" *.txt; then  echo " Got nfs,See~HTB remote,TryHackeMe Kenobi" ;fi
if grep  "Umbraco" *.txt; then  echo " Got Umbraco,See~HTB remote" ;fi
if grep  "VNC" *.txt; then  echo " Got VNC,See~HTB poison or oscp2 10.11.1.227" ;fi
if grep  "IIS httpd 6.0" *.txt; then  echo " Got IIS httpd 6.0,See~HTB Grandpa" ;fi
if grep  "nochg" *.txt; then  echo " Got nochg,See~HTB dynstr" ;fi
if grep  "XML" *.txt; then  echo " Got XML,See~HTB Devoops" ;fi
if grep  "mattermost" *.txt; then  echo " Got mattermost,See~HTB Delivery" ;fi
if grep  "ticket system" *.txt; then  echo " Got ticket system,See~HTB Delivery" ;fi
if grep  "AChat" *.txt; then  echo " Got AChat,See~HTB Chatterbox" ;fi
if grep  "Gym Management" *.txt; then  echo " Got Gym Management,See~HTB buff" ;fi
if grep  "Bludit" *.txt; then  echo " Got Bludit,See~HTB blunder" ;fi
if grep  "rpc" *.txt; then  echo " Got rpc,See~HTB blackfiled" ;fi
if grep  "OpenSMTPD" *.txt; then  echo " Got OpenSMTPD,See~OSPG Bratarina" ;fi
if grep  "ClamAV" *.txt; then  echo " Got ClamAV,See~OSPG ClamAV" ;fi
if grep  "rsync" *.txt; then  echo " Got rsync,See~OSPG fail" ;fi
if grep  "CVE-2009-3103" *.txt; then  echo " Got CVE-2009-3103,See~OSPG Internal" ;fi
if grep  "H2 database" *.txt; then  echo " Got H2 database,See~OSPG jacko" ;fi
if grep  "Applications Manager" *.txt; then  echo " Got Applications Manager,See~OSPG Metallus" ;fi
if grep  "CMS Made Simple" *.txt; then  echo " Got CMS Made Simple,See~OSPG My-CMSMS" ;fi
if grep  "postgresql" *.txt; then  echo " Got postgresql,See~OSPG Nibbles" ;fi
if grep  "exhibitor" *.txt; then  echo " Got exhibitor,See~OSPG Pelican" ;fi
if grep  "webcalendar" *.txt; then  echo " Got webcalendar,See~OSPG webcal" ;fi
if grep  "Joomla!" *.txt; then  echo " Got Joomla!,See~TryHackeMe Daily_Bugle" ;fi
if grep  "webmin" *.txt; then  echo " Got webmin,See~TryHackeMe GameZone,oscp2 10.11.1.141" ;fi
if grep  "blogenine" *.txt; then  echo " Got blogenine,See~TryHackeMe HackPark" ;fi
if grep  "Jenkins" *.txt; then  echo " Got Jenkins,See~TryHackeMe Internal" ;fi
if grep  "cuppa" *.txt; then  echo " Got cuppa,See~TryHackeMe skynet" ;fi
if grep  "HTTP File Server" *.txt; then  echo " Got HTTP File Server,See~TryHackeMe Stell Mountain" ;fi
if grep  "Advanced comment system" *.txt; then  echo " Got Advanced comment system,See~oscp2 10.11.1.8" ;fi
if grep  "_vti_pingit" *.txt; then  echo " Got _vti_pingit,See~oscp2 10.11.1.31" ;fi
if grep  "ovidentia" *.txt; then  echo " Got ovidentia,See~oscp2 10.11.1.73" ;fi
if grep  "Samba 2.2" *.txt; then  echo " Got Samba 2.2,See~oscp2 10.11.1.115" ;fi
if grep  "cuppcms" *.txt; then  echo " Got cuppcms,See~oscp2 10.11.1.116" ;fi
if grep  "MicroBlog" *.txt; then  echo " Got MicroBlog,See~oscp2 10.11.1.223" ;fi
if grep  "elastix" *.txt; then  echo " Got elastix,See~oscp2 HOTLINE" ;fi
if grep  "smtp" *.txt; then  echo " Got smtp,See~oscp2 MAILMAN" ;fi



