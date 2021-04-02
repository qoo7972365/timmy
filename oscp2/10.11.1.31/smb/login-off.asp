<%

function stripFilter(strWords)     

stripFilter = replace(strWords, "'", "")
stripFilter = replace(stripFilter, "-", "")  
stripFilter = replace(stripFilter, "&", "")  
stripFilter = replace(stripFilter, "%", "")  
stripFilter = replace(stripFilter, "\", "")  
stripFilter = replace(stripFilter, "/", "")  
stripFilter = replace(stripFilter, "|", "")  
stripFilter = replace(stripFilter, ">", "")

end function     

set cnn = server.createobject("ADODB.Connection")
cnn.open "PROVIDER=SQLOLEDB;DATA SOURCE=RALPH;User ID=sa;PWD=poiuytrewq;DATABASE=bankdb"

myUsrName = stripFilter(request.form("txtLoginID"))
myUsrPassword = stripFilter(request.form("txtPassword"))

sSql = "SELECT * FROM tblCustomers where cust_name='" & myUsrName & "' and cust_password='"&myUsrPassword&"'" 

Set rs = Server.CreateObject("ADODB.Recordset")
rs.Open sSql, cnn, 3, 3

if rs.BOF or rs.EOF then
	Response.write "<html><title>Offensive ASP Test Page</title>"
	response.write "<br><br><center><h1>ACCESS DENIED</h1></center>" %>
	<meta http-equiv="REFRESH"content="2;url=base-login.asp"><%
else
	Response.write "Login OK"  
	Response.write "<html><title>Offensive ASP Example</title>" %>
	<meta http-equiv="REFRESH" content="0;url=restricted.htm"><%
	
End If

rs.Close
cnn.Close

set rs = nothing
set cmd = nothing
set cnn = nothing

%>

