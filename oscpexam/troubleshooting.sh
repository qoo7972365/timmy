#!/bin/bash
## Offensive-Security's troubleshooting script
## Last updated: 2017-03-20


## Bash colours
YELLOW="\033[01;33m"
BLUE="\033[01;34m"
BOLD="\033[01;01m"
RED="\033[01;31m"
RESET="\033[00m"


## Banner/Notice
echo -e "\n${BLUE}[+]${RESET} Should you experience any connectivity issues, please ${BOLD}send the log file './troubleshoot.log'${RESET} along with the ${BOLD}output from the OpenVPN window${RESET}, with ${BOLD}your OSID${RESET} to '${BOLD}help@offensive-security.com${RESET}'."
sleep 3s


## Checking user
echo -e "\n\n${YELLOW}[i]${RESET} Checking User"
echo -e "[i] Checking User" > troubleshoot.log
if [[ "${EUID}" -ne 0 ]]; then
  echo -e "${RED}[-]${RESET} This script must be run as ${RED}root${RESET}"
  echo -e "[-] This script must be run as root" >> troubleshoot.log
  sleep 2s
  exit 1
fi
id | tee -a troubleshoot.log
sleep 3s


## Date
echo -e "\n\n${YELLOW}[i]${RESET} Date"
echo -e "\n\n[i] Date" >> troubleshoot.log
date | tee -a troubleshoot.log
sleep 3s


## VM check
echo -e "\n\n${YELLOW}[i]${RESET} Virtual Machine Check"
echo -e "\n\n[i] Virtual Machine Check" >> troubleshoot.log
if (dmidecode | grep -iq vmware); then
  echo -e "VMware Detected" | tee -a troubleshoot.log
elif (dmidecode | grep -iq virtualbox); then
  echo -e "${YELLOW}[i] VirtualBox Detected${RESET}!   It is highly recommended that all students use the VMware student VM." | tee -a troubleshoot.log
  echo -e "VirtualBox Detected!   It is highly recommended that all students use the VMware student VM." >> troubleshoot.log
  echo -e "    See: https://help.offensive-security.com/hc/en-us/articles/360049796792-Kali-Linux-Virtual-Machine"
  sleep 2s
else
  echo -e "${RED}[-] VM not detected${RESET}!   It is highly recommended that all students use the VMware student VM."
  echo -e "VM not detected!   It is highly recommended that all students use the VMware student VM." >> troubleshoot.log
  echo -e "    See: https://help.offensive-security.com/hc/en-us/articles/360049796792-Kali-Linux-Virtual-Machine"
  sleep 2s
fi
sleep 3s


## Network interfaces
echo -e "\n\n${YELLOW}[i]${RESET} Network Interfaces"
echo -e "\n\n[i] Network Interfaces" >> troubleshoot.log
ifconfig -a | tee -a troubleshoot.log
sleep 3s


## Network routes
echo -e "\n\n${YELLOW}[i]${RESET} Network Routes"
echo -e "\n\n[i] Network Routes" >> troubleshoot.log
route -n | tee -a troubleshoot.log
sleep 3s


## DNS information
echo -e "\n\n${YELLOW}[i]${RESET} DNS Information"
echo -e "\n\n[i] DNS Information" >> troubleshoot.log
cat /etc/resolv.conf | tee -a troubleshoot.log
sleep 3s


## Ping test
echo -e "\n\n${YELLOW}[i]${RESET} Ping Test (External: www.Google.com)"
echo -e "\n\n[i] Ping Test (External: www.Google.com)" >> troubleshoot.log
ping -c 4 8.8.8.8 | tee -a troubleshoot.log
if [[ $? != '0' ]]; then
  echo -e "${RED}[-]${RESET} Ping test failed (8.8.8.8).\n${RED}[-]${RESET} Please make sure you have Internet access."
  sleep 2s
fi
echo -e "" | tee -a troubleshoot.log
ping -c 4 www.google.com | tee -a troubleshoot.log
if [[ $? != '0' ]]; then
  echo -e "${RED}[-]${RESET} Ping test failed (www.google.com)...\n${RED}[-]${RESET} Please make sure you have Internet access."
  sleep 2s
fi
sleep 3s


## Ping test
echo -e "\n\n${YELLOW}[i]${RESET} Ping Test (Internal VPN: 10.11.1.220)"
echo -e "\n\n[i] Ping Test (Internal VPN: 10.11.1.220)" >> troubleshoot.log
ping -c 8 10.11.1.220 | tee -a troubleshoot.log
if [[ $? != '0' ]]; then
  echo -e "${RED}[-]${RESET} Ping test failed (10.11.1.220).\n${RED}[-]${RESET} Please make sure you are connected to the VPN (if it is possible)."
  sleep 2s
fi
sleep 3s


## External IP
echo -e "\n\n${YELLOW}[i]${RESET} External IP"
echo -e "\n\n[i] External IP" >> troubleshoot.log
curl -sS -m 20 http://ipinfo.io/ip 2>&1 | tee -a troubleshoot.log
sleep 3s


## UDP port test
echo -e "\n\n${YELLOW}[i]${RESET} UDP Port Test"
echo -e "\n\n[i] UDP Port Test" >> troubleshoot.log
files=$(find . -name '*.ovpn' -maxdepth 1 2>/dev/null | wc -l)
if [[ "${files}" == "1" ]]; then
  IP=$(grep -e '^remote ' *.ovpn | awk '{print $2}')
  nc -vzu ${IP} 1194 2>&1 | tee -a troubleshoot.log
elif [[ "${files}" == "0" ]]; then
  echo -e "${RED}[-]${RESET} Missing connection pack"
  echo -e "\n\n[-] Missing connection pack" >> troubleshoot.log
  ls -lah | tee -a troubleshoot.log
  pwd | tee -a troubleshoot.log
  sleep 2s
else
  echo -e "${RED}[-]${RESET} Multiple connection packs, please remove the old one(s)"
  echo -e "\n\n[-] Multiple connection packs, please remove the old one(s)" >> troubleshoot.log
  pwd | tee -a troubleshoot.log
  find . -name '*.ovpn' -maxdepth 1 -ls 2>/dev/null | tee -a troubleshoot.log
  echo -e "" | tee -a troubleshoot.log
  ls -lah | tee -a troubleshoot.log
  sleep 2s
fi
sleep 3s


## Checking kernel version
echo -e "\n\n${YELLOW}[i]${RESET} Checking Kernel Version"
echo -e "\n\n[i] Checking Kernel Version" >> troubleshoot.log
uname -a | tee -a troubleshoot.log
if [[ "$(uname -a)" == *"pae"* ]]; then
  echo -e "${RED}[-]${RESET} PAE kernel detected.    Please use the VMware student VM."
  echo -e '    See: https://help.offensive-security.com/hc/en-us/articles/360049796792-Kali-Linux-Virtual-Machine'
  sleep 2s
fi
sleep 3s


## Checking OS
echo -e "\n\n${YELLOW}[i]${RESET} Checking OS"
echo -e "\n\n[i] Checking OS" >> troubleshoot.log
cat /etc/issue | tee -a troubleshoot.log
cat /etc/*-release | tee -a troubleshoot.log
sleep 3s


## Notice
echo -e "\n\n${BLUE}[+]${RESET} Test complete."
echo -e "${BLUE}[+]${RESET} Should you experience any connectivity issues, please ${BOLD}send the log file './troubleshoot.log'${RESET} along with the ${BOLD}output from the OpenVPN window${RESET}, with ${BOLD}your OSID${RESET} to '${BOLD}help@offensive-security.com${RESET}'."
sleep 3s
