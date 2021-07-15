###SMB
nmap --script=smb-enum-shares -Pn -p 139,445  $1 > nmapsmbshares.txt
nmap --script=smb-vuln* -Pn -p 139,445  $1 > nmapsmbvuln.txt
nmap --script=smb-vuln-ms17-010 -Pn -p 139,445 $1  > nmapsmbMS017.txt
nmap --script smb* -sV -p 139,445  $1  > nmapsmb.txt
nmap --script smb-os-discovery -sV -p 139,445  $1  > nmapsmbdiscovery.txt
nmap --script=smb-vuln-ms08-067  --script-args=unsafe=1 -sV -p 139,445  $1  > nmapsmbMS08-067.txt
nmap --script=smb-enum-shares.nse,smb-enum-users.nse $1 -p 445 > nampsamba.txt
nmap --script=nfs-ls,nfs-statfs,nfs-showmount $1 -p 111 > nmaprpc.txt