# How to Temporarily Disable Antivirus for Gradle Sync

## Windows Defender (Most Common Solution)

1. **Open Windows Security**
   - Press `Windows Key`
   - Type "Windows Security"
   - Press Enter

2. **Disable Real-time Protection**
   - Click "Virus & threat protection"
   - Click "Manage settings" under "Virus & threat protection settings"
   - Toggle OFF "Real-time protection"
   - Confirm the UAC prompt

3. **Sync Gradle in Android Studio**
   - Go to Android Studio
   - File → Sync Project with Gradle Files
   - Wait for sync to complete

4. **Re-enable Protection**
   - Go back to Windows Security
   - Toggle ON "Real-time protection"

---

## If Windows Defender Isn't the Issue

### Try Mobile Hotspot (Bypass Network Restrictions)

1. **Enable hotspot on your phone**
2. **Connect your computer to the phone's hotspot**
3. **Sync Gradle in Android Studio**
4. **Switch back to regular WiFi after sync completes**

---

## If That Still Doesn't Work

### Check if Another Process is Using Port

Run this in PowerShell:
```powershell
netstat -ano | findstr :443
```

If you see many connections, your network might be throttling HTTPS connections.

### Last Resort: Download Offline

If nothing works, you may need to:
1. Use a different computer/network to sync once
2. Copy the `.gradle` cache folder
3. Use offline mode in Android Studio
