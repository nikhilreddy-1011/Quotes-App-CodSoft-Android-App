@echo off
echo ========================================
echo Quick Gradle Lock Cleanup
echo ========================================
echo.

echo [1/2] Stopping Java/Gradle processes...
taskkill /F /IM java.exe >nul 2>&1
timeout /t 2 /nobreak >nul
echo Done!

echo.
echo [2/2] Cleaning lock files...
if exist .gradle rmdir /s /q .gradle
echo Done!

echo.
echo ========================================
echo Cleanup Complete!
echo Now sync in Android Studio
echo ========================================
echo.
pause
