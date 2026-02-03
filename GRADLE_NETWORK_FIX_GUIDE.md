# Gradle "Connection Reset by Peer" - COMPLETE FIX GUIDE

## What I've Done

### 1. ✅ Updated Repository Configuration
**File: `settings.gradle.kts`**
- Added Aliyun CDN mirrors (faster for Asia region)
- Added JitPack as fallback
- Changed repository mode to allow multiple sources
- Repositories will be tried in order until one succeeds

### 2. ✅ Enhanced Network Settings  
**File: `gradle.properties`**
- Increased connection timeout: 120s → 300s (5 minutes)
- Increased socket timeout: 120s → 300s (5 minutes)
- Disabled HTTP keep-alive (prevents stale connection reuse)
- Increased max redirects: 10 → 20
- Added connection pooling (max 10 concurrent)
- Enabled TLS 1.2 and 1.3

### 3. ✅ Created Helper Scripts
- `clean-gradle-locks.ps1` - Kill processes and clean locks
- `fix-gradle-network.ps1` - Network diagnostics

## Next Steps (IN THIS ORDER)

### ⭐ OPTION 1: Use Mobile Hotspot (RECOMMENDED)
This is the **most reliable** solution for Indian users:

1. Enable mobile hotspot on your phone
2. Connect your PC to the hotspot
3. Open Android Studio
4. Click: **File → Sync Project with Gradle Files**
5. Wait patiently (first sync takes 5-10 minutes)

**Why this works:** ISPs often throttle or block Maven repositories. Mobile networks usually don't have these restrictions.

---

### ⭐ OPTION 2: Disable Antivirus
1. Right-click your antivirus tray icon
2. Select "Disable for 10 minutes" 
3. Try Gradle sync in Android Studio

---

### ⭐ OPTION 3: Use Android Studio (Not Command Line)
Android Studio has better network handling than gradlew:

1. Open Android Studio
2. **File → Invalidate Caches and Restart**
3. After restart: **File → Sync Project with Gradle Files**

---

### ⭐ OPTION 4: Try VPN
1. Install free VPN (ProtonVPN, Windscribe)
2. Connect to Singapore/US server
3. Try Gradle sync

---

## If Still Failing

### Check Your Network
Run this in PowerShell:
```powershell
# Test Google Maven
Invoke-WebRequest -Uri "https://dl.google.com/dl/android/maven2/" -UseBasicParsing

# Test Maven Central  
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/" -UseBasicParsing
```

If both fail → Network/ISP issue. **Use mobile hotspot.**

### Clean Everything and Start Fresh
```powershell
# Run the cleanup script
.\clean-gradle-locks.ps1

# In Android Studio:
# File → Invalidate Caches → Invalidate and Restart
```

---

## Understanding the Error

**"Connection reset by peer"** means:
- The server (Maven repo) closed the connection unexpectedly
- Could be: ISP throttling, antivirus interference, firewall blocking, or unstable network

**Common causes in India:**
1. ISP blocking/throttling Maven repos (very common)
2. Antivirus SSL inspection interfering with HTTPS
3. Firewall blocking Java network connections
4. Unstable WiFi connection

---

## What Changed in Your Project

### `settings.gradle.kts`
Now uses 7 repository mirrors instead of 2:
- Aliyun Google mirror (China CDN - fast in Asia)
- Aliyun Central mirror
- Aliyun Gradle Plugin mirror
- Original Google Maven
- Original Maven Central
- JitPack

Gradle will try each one until it finds a working connection.

### `gradle.properties`  
- 5-minute timeouts (very generous)
- No connection reuse (prevents stale connections)
- More retries and redirects allowed

---

## Expected Behavior

**First Build:**
- Will take 5-10 minutes
- Downloads ~500MB of dependencies
- Shows many "Downloading..." messages
- **Don't interrupt it!**

**Subsequent Builds:**
- Much faster (1-2 minutes)
- Uses cached dependencies

---

## Still Not Working?

If you've tried everything above and it's still failing:

1. **Check if you're on corporate/school network**
   - These often block Maven repos
   - Ask network admin to whitelist:
     - `*.maven.org`
     - `*.google.com`
     - `*.gradle.org`

2. **Try at different time of day**
   - Some ISPs throttle during peak hours
   - Try early morning (6-8 AM)

3. **Use mobile hotspot** (seriously, this almost always works)

---

## Success Checklist

✅ Tried mobile hotspot  
✅ Disabled antivirus  
✅ Used Android Studio (not command line)  
✅ Cleared caches (Invalidate Caches)  
✅ Waited full 10 minutes without interrupting  
✅ Checked network connectivity  

If you've done all these and it still fails, the issue is likely your ISP or network infrastructure blocking the repositories.

---

## Quick Reference Commands

```powershell
# Clean locks
.\clean-gradle-locks.ps1

# Or manually:
Get-Process | Where-Object {$_.ProcessName -like "*java*"} | Stop-Process -Force
Remove-Item -Path ".gradle" -Recurse -Force

# Test network
Invoke-WebRequest -Uri "https://maven.aliyun.com/repository/google" -UseBasicParsing
```

---

## Final Recommendation

**🔥 JUST USE MOBILE HOTSPOT 🔥**

Seriously, it's the fastest solution. I've configured every possible workaround, but if your ISP or antivirus is blocking Maven repos, no amount of configuration will fix it. Mobile hotspot bypasses all those issues.

1. Enable phone hotspot
2. Connect PC
3. Sync in Android Studio  
4. First build will download everything
5. Switch back to WiFi for subsequent builds (dependencies are now cached)

Good luck! 🚀
