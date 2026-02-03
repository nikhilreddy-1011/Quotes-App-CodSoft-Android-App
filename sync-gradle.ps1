# Gradle Sync Helper Script
# This script attempts to sync Gradle dependencies with better error handling

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Gradle Dependency Sync Helper" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Stop all Gradle daemons
Write-Host "[1/4] Stopping all Gradle daemons..." -ForegroundColor Yellow
$gradleProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue | Where-Object { $_.CommandLine -like "*gradle*" }
if ($gradleProcesses) {
    $gradleProcesses | Stop-Process -Force
    Write-Host "✓ Stopped Gradle daemons" -ForegroundColor Green
} else {
    Write-Host "✓ No Gradle daemons running" -ForegroundColor Green
}

# Navigate to project
Write-Host "`n[2/4] Navigating to project directory..." -ForegroundColor Yellow
Set-Location "c:\Quotes App"
Write-Host "✓ In project directory" -ForegroundColor Green

# Check for gradlew
Write-Host "`n[3/4] Checking for Gradle wrapper..." -ForegroundColor Yellow
if (Test-Path ".\gradlew.bat") {
    Write-Host "✓ Found gradlew.bat" -ForegroundColor Green
} else {
    Write-Host "✗ gradlew.bat not found!" -ForegroundColor Red
    Write-Host "Please ensure you're in the correct project directory." -ForegroundColor Red
    exit 1
}

# Try to sync dependencies
Write-Host "`n[4/4] Attempting to download dependencies..." -ForegroundColor Yellow
Write-Host "This may take several minutes. Please be patient...`n" -ForegroundColor Cyan

try {
    # Run with --refresh-dependencies to force re-download
    .\gradlew.bat --refresh-dependencies tasks --stacktrace --info
    
    Write-Host "`n========================================" -ForegroundColor Green
    Write-Host "✓ SUCCESS! Dependencies downloaded" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "`nYou can now sync in Android Studio." -ForegroundColor Cyan
    
} catch {
    Write-Host "`n========================================" -ForegroundColor Red
    Write-Host "✗ FAILED - Connection issue detected" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "`nPossible causes:" -ForegroundColor Yellow
    Write-Host "1. Antivirus/Firewall blocking Gradle" -ForegroundColor White
    Write-Host "2. VPN interfering with connection" -ForegroundColor White
    Write-Host "3. ISP blocking/throttling Maven repos" -ForegroundColor White
    Write-Host "`nSuggested actions:" -ForegroundColor Yellow
    Write-Host "• Temporarily disable antivirus" -ForegroundColor White
    Write-Host "• Disconnect from VPN" -ForegroundColor White
    Write-Host "• Try a different network (mobile hotspot)" -ForegroundColor White
    Write-Host "• Contact your network administrator" -ForegroundColor White
}
