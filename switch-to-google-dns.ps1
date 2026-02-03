# Switch to Google DNS to fix connection issues
# Run this as Administrator

Write-Host "Switching to Google DNS (8.8.8.8, 8.8.4.4)..." -ForegroundColor Yellow
Write-Host "This may help resolve 'Connection reset by peer' errors`n" -ForegroundColor Cyan

# Get active network adapter
$adapter = Get-NetAdapter | Where-Object { $_.Status -eq "Up" } | Select-Object -First 1

if ($adapter) {
    Write-Host "Active adapter: $($adapter.Name)" -ForegroundColor Green
    
    # Set DNS servers
    Set-DnsClientServerAddress -InterfaceIndex $adapter.ifIndex -ServerAddresses ("8.8.8.8", "8.8.4.4")
    
    # Flush DNS cache
    ipconfig /flushdns | Out-Null
    
    Write-Host "`n✓ DNS changed to Google DNS (8.8.8.8, 8.8.4.4)" -ForegroundColor Green
    Write-Host "✓ DNS cache flushed" -ForegroundColor Green
    Write-Host "`nNow try syncing Gradle in Android Studio`n" -ForegroundColor Cyan
    
    # Instructions to revert
    Write-Host "To revert back to automatic DNS later:" -ForegroundColor Yellow
    Write-Host "Set-DnsClientServerAddress -InterfaceIndex $($adapter.ifIndex) -ResetServerAddresses" -ForegroundColor White
}
else {
    Write-Host "No active network adapter found!" -ForegroundColor Red
}
