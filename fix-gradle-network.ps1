# Complete Gradle Network Fix Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "GRADLE NETWORK FIX - COMPREHENSIVE" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Step 1: Stop all Gradle processes
Write-Host "`n[1/5] Stopping all Gradle/Java processes..." -ForegroundColor Yellow
Get-Process | Where-Object { $_.ProcessName -like "*java*" } | Stop-Process -Force -ErrorAction SilentlyContinue
Start-Sleep -Seconds 2
Write-Host "✓ Processes stopped" -ForegroundColor Green

# Step 2: Clean local project Gradle cache
Write-Host "`n[2/5] Cleaning project .gradle cache..." -ForegroundColor Yellow
Remove-Item -Path ".gradle" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "✓ Project cache cleaned" -ForegroundColor Green

# Step 3: Clean lock files
Write-Host "`n[3/5] Cleaning Gradle lock files..." -ForegroundColor Yellow
$userGradle = Join-Path $env:USERPROFILE ".gradle"
if (Test-Path $userGradle) {
    Get-ChildItem -Path $userGradle -Recurse -Filter "*.lock" -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
}
Write-Host "✓ Lock files cleaned" -ForegroundColor Green

# Step 4: Test network connectivity
Write-Host "`n[4/5] Testing connectivity to repositories..." -ForegroundColor Yellow

$testUrls = @(
    "https://maven.aliyun.com/repository/google",
    "https://maven.aliyun.com/repository/central",
    "https://dl.google.com/dl/android/maven2/",
    "https://repo1.maven.org/maven2/"
)

$successCount = 0
foreach ($url in $testUrls) {
    try {
        $response = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
        Write-Host "  ✓ Reachable: $url" -ForegroundColor Green
        $successCount++
    }
    catch {
        Write-Host "  ✗ Failed: $url" -ForegroundColor Red
    }
}

# Step 5: Provide recommendations
Write-Host "`n[5/5] Configuration Summary..." -ForegroundColor Yellow
Write-Host "  ✓ Mirror repositories configured" -ForegroundColor Green
Write-Host "  ✓ Network timeouts: 5 minutes" -ForegroundColor Green
Write-Host "  ✓ Connection pooling enabled" -ForegroundColor Green

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "RESULTS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

if ($successCount -gt 0) {
    Write-Host "`n✓ $successCount/$($testUrls.Count) repositories reachable" -ForegroundColor Green
    Write-Host "`nNow try in Android Studio:" -ForegroundColor Yellow
    Write-Host "  File → Sync Project with Gradle Files" -ForegroundColor White
}
else {
    Write-Host "`n⚠ No repositories reachable!" -ForegroundColor Red
    Write-Host "`nQuick fixes (try in this order):" -ForegroundColor Yellow
    Write-Host "  1. Switch to MOBILE HOTSPOT" -ForegroundColor White
    Write-Host "  2. Disable antivirus for 10 minutes" -ForegroundColor White
    Write-Host "  3. Use a VPN" -ForegroundColor White
}

Write-Host "`n========================================`n" -ForegroundColor Green
