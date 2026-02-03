# Quick Gradle Lock Cleanup Script
# Run this if you get lock timeout errors

Write-Host "Cleaning Gradle locks..." -ForegroundColor Cyan

# Kill all Java/Gradle processes
Write-Host "`n[1/3] Stopping Gradle daemon processes..." -ForegroundColor Yellow
Get-Process | Where-Object { $_.ProcessName -like "*java*" } | Stop-Process -Force -ErrorAction SilentlyContinue
Write-Host "✓ Processes stopped" -ForegroundColor Green

# Clean lock files
Write-Host "`n[2/3] Removing lock files..." -ForegroundColor Yellow
Remove-Item -Path ".gradle\*\*.lock" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path ".gradle\*\*\*.lock" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "✓ Lock files removed" -ForegroundColor Green

# Clean checksums cache (often causes issues)
Write-Host "`n[3/3] Cleaning checksums cache..." -ForegroundColor Yellow
Remove-Item -Path ".gradle\*\checksums" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "✓ Checksums cleaned" -ForegroundColor Green

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "✓ Gradle locks cleaned successfully!" -ForegroundColor Green
Write-Host "You can now sync/build in Android Studio" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Green
