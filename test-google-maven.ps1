#!/usr/bin/env pwsh
# Quick Network Test for Maven Repositories

Write-Host "Testing Maven Repository Access..." -ForegroundColor Cyan
Write-Host ""

# Test Google Maven (where KSP plugin lives)
Write-Host "[1] Testing Google Maven Repository..." -ForegroundColor Yellow
try {
    $startTime = Get-Date
    $response = Invoke-WebRequest -Uri "https://dl.google.com/dl/android/maven2/com/google/devtools/ksp/com.google.devtools.ksp.gradle.plugin/1.9.22-1.0.17/" -UseBasicParsing -TimeoutSec 15
    $endTime = Get-Date
    $duration = ($endTime - $startTime).TotalSeconds
    
    if ($response.StatusCode -eq 200) {
        Write-Host "  ✅ SUCCESS - Google Maven is accessible ($duration seconds)" -ForegroundColor Green
        Write-Host "  KSP 1.9.22-1.0.17 folder exists!" -ForegroundColor Green
        $google_works = $true
    }
}
catch {
    Write-Host "  ❌ FAILED - Cannot access Google Maven" -ForegroundColor Red
    Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Red
    $google_works = $false
}

Write-Host ""

# Test Maven Central
Write-Host "[2] Testing Maven Central..." -ForegroundColor Yellow
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
Write-Host "===============================================" -ForegroundColor Cyan

if ($google_works) {
    Write-Host "✅ GOOD NEWS!" -ForegroundColor Green
    Write-Host "Google Maven is accessible. The KSP plugin should download." -ForegroundColor Green
    Write-Host ""
    Write-Host "Try syncing in Android Studio now." -ForegroundColor Yellow
}
else {
    Write-Host "❌ PROBLEM CONFIRMED" -ForegroundColor Red
    Write-Host "Your network is blocking Google Maven repository." -ForegroundColor Red
    Write-Host ""
    Write-Host "🔥 YOU MUST USE MOBILE HOTSPOT 🔥" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "1. Enable mobile hotspot on your phone" -ForegroundColor White
    Write-Host "2. Connect PC to hotspot" -ForegroundColor White
    Write-Host "3. Run this test again to verify" -ForegroundColor White
    Write-Host "4. Then sync in Android Studio" -ForegroundColor White
}

Write-Host "===============================================" -ForegroundColor Cyan
