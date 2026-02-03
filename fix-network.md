# Fix "Connection Reset by Peer" Error

## ✅ Completed
- Killed blocking Gradle daemon (PID 13692)
- Updated `gradle.properties` with network timeout settings

## 🔧 Action Required: Switch DNS (Needs Administrator)

### Step 1: Run DNS Script as Admin
**Right-click PowerShell → "Run as Administrator"**, then:
```powershell
cd "C:\Quotes App"
.\switch-to-google-dns.ps1
```

**OR** run this command in your current PowerShell:
```powershell
Start-Process powershell -Verb RunAs -ArgumentList "-ExecutionPolicy Bypass -File `"C:\Quotes App\switch-to-google-dns.ps1`""
```

### Step 2: Sync Gradle
After DNS is switched:
1. Go to **Android Studio**
2. Click **File → Sync Project with Gradle Files**

---

## 🔄 Alternative: Manual DNS Change
If the script doesn't work, manually change DNS:

1. **Open Network Settings:**
   - Press `Win + R`
   - Type `ncpa.cpl` and press Enter

2. **Configure DNS:**
   - Right-click your active network adapter → **Properties**
   - Select **Internet Protocol Version 4 (TCP/IPv4)** → **Properties**
   - Select **"Use the following DNS server addresses"**
   - Preferred DNS: `8.8.8.8`
   - Alternate DNS: `8.8.4.4`
   - Click **OK**

3. **Flush DNS cache:**
   ```powershell
   ipconfig /flushdns
   ```

---

## 🌐 Alternative: Use Mobile Hotspot
If DNS change doesn't help:
1. Enable mobile hotspot on your phone
2. Connect your PC to the hotspot
3. Try Gradle sync again

This bypasses your current network's restrictions.
