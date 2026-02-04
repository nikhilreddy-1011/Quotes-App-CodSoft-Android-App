#!/usr/bin/env pwsh
# Network Connectivity Test and Gradle Sync Helper
# This script helps diagnose network issues and provides solutions

Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "Gradle Network Connectivity Test" -ForegroundColor Cyan
Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host ""

# Test 1: Aliyun Google Mirror
Write-Host "[1/4] Testing Aliyun Google Mirror..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "https://maven.aliyun.com/repository/google/" -UseBasicParsing -TimeoutSec 10
    if ($response.StatusCode -eq 200) {
        Write-Host "  ✅ Aliyun Google Mirror is accessible" -ForegroundColor Green
        $aliyun_works = $true
    }
}
catch {
    Write-Host "  ❌ Aliyun Google Mirror FAILED: $($_.Exception.Message)" -ForegroundColor Red
    $aliyun_works = $false
}

# Test 2: Aliyun Central Mirror  
Write-Host "[2/4] Testing Aliyun Central Mirror..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "https://maven.aliyun.com/repository/central/" -UseBasicParsing -TimeoutSec 10
    if ($response.StatusCode -eq 200) {
        Write-Host "  ✅ Aliyun Central Mirror is accessible" -ForegroundColor Green
    }
}
catch {
    Write-Host "  ❌ Aliyun Central Mirror FAILED" -ForegroundColor Red
}

# Test 3: Google Maven
Write-Host "[3/4] Testing Google Maven Repository..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "https://dl.google.com/dl/android/maven2/" -UseBasicParsing -TimeoutSec 10
    if ($response.StatusCode -eq 200) {
        Write-Host "  ✅ Google Maven is accessible" -ForegroundColor Green
        $google_works = $true
    }
}
catch {
    Write-Host "  ❌ Google Maven FAILED: $($_.Exception.Message)" -ForegroundColor Red
    $google_works = $false
}

# Test 4: Maven Central
Write-Host "[4/4] Testing Maven Central..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/" -UseBasicParsing -TimeoutSec 10
    if ($response.StatusCode -eq 200) {
        Write-Host "  ✅ Maven Central is accessible" -ForegroundColor Green
    }
}
catch {
    Write-Host "  ❌ Maven Central FAILED" -ForegroundColor Red
}

Write-Host ""
Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "DIAGNOSIS & RECOMMENDATIONS" -ForegroundColor Cyan
Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host ""

# Provide recommendations based on test results
if ($aliyun_works) {
    Write-Host "✅ Aliyun mirrors are working! Your current setup should work." -ForegroundColor Green
    Write-Host ""
    Write-Host "NEXT STEPS:" -ForegroundColor Yellow
    Write-Host "1. Open Android Studio" -ForegroundColor White
    Write-Host "2. Click: File → Sync Project with Gradle Files" -ForegroundColor White  
    Write-Host "3. Wait patiently (first sync takes 5-10 minutes)" -ForegroundColor White
    Write-Host ""
    Write-Host "If sync still fails, KSP plugin may not be in Aliyun mirrors." -ForegroundColor Yellow
    Write-Host "In that case, try the solutions below..." -ForegroundColor Yellow
}
elseif ($google_works) {
    Write-Host "⚠️  Aliyun mirrors are blocked, but Google Maven works!" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "SOLUTION: The repositories are configured to fallback to Google/Maven." -ForegroundColor Green
    Write-Host "Gradle should automatically try Google repos when Aliyun fails." -ForegroundColor Green
    Write-Host ""
    Write-Host "NEXT STEPS:" -ForegroundColor Yellow
    Write-Host "1. Open Android Studio" -ForegroundColor White
    Write-Host "2. Click: File → Sync Project with Gradle Files" -ForegroundColor White
    Write-Host "3. Be patient - may take longer as it tries Aliyun first then falls back" -ForegroundColor White
}
else {
    Write-Host "❌ ALL repositories are unreachable!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Your network is blocking Maven repositories." -ForegroundColor Red
    Write-Host ""
    Write-Host "SOLUTIONS (try in order):" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "🔥 OPTION 1: USE MOBILE HOTSPOT (RECOMMENDED)" -ForegroundColor Cyan
    Write-Host "   1. Enable mobile hotspot on your phone" -ForegroundColor White
    Write-Host "   2. Connect your PC to the hotspot" -ForegroundColor White
    Write-Host "   3. Run this script again to verify connectivity" -ForegroundColor White
    Write-Host "   4. Sync project in Android Studio" -ForegroundColor White
    Write-Host ""
    Write-Host "🔥 OPTION 2: DISABLE ANTIVIRUS TEMPORARILY" -ForegroundColor Cyan
    Write-Host "   1. Disable antivirus for 10 minutes" -ForegroundColor White
    Write-Host "   2. Run this script again" -ForegroundColor White
    Write-Host "   3. Sync project in Android Studio" -ForegroundColor White
    Write-Host ""
    Write-Host "🔥 OPTION 3: USE VPN" -ForegroundColor Cyan
    Write-Host "   1. Connect to VPN (Singapore/US server)" -ForegroundColor White
    Write-Host "   2. Run this script again" -ForegroundColor White
    Write-Host "   3. Sync project in Android Studio" -ForegroundColor White
}

Write-Host ""
Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "For more help, check: GRADLE_NETWORK_FIX_GUIDE.md" -ForegroundColor White
Write-Host "==============================================================" -ForegroundColor Cyan
