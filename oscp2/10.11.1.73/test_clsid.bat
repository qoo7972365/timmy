@echo off
:: Starting port, you can change it
set /a port=10000
SETLOCAL ENABLEDELAYEDEXPANSION

FOR /F %%i IN (C:\wamp\www\PHP\myfolder\fileManager\collectives\DG0\777\CLSID.list) DO (
   echo %%i !port!
   C:\wamp\www\PHP\myfolder\fileManager\collectives\DG0\777\jc.exe -z -l !port! -c %%i >> C:\wamp\www\PHP\myfolder\fileManager\collectives\DG0\777\result.log
   set RET=!ERRORLEVEL!
   :: echo !RET!
   if "!RET!" == "1"  set /a port=port+1
)