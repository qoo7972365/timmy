nmap -Pn --script "rdp-enum-encryption" -p 3389 -T4 $1 > nmaprdpencry.txt
nmap -Pn --script "rdp-vuln-ms12-020" -p 3389 -T4 $1 > nmaprdpvuln.txt
nmap -Pn --script "rdp-ntlm-info" -p 3389 -T4 $1 > nmaprdpntlm.txt