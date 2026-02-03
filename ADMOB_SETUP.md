# AdMob Setup Guide

This guide will help you integrate Google AdMob for monetization.

## Step 1: Create AdMob Account

1. Go to [AdMob](https://admob.google.com)
2. Sign in with your Google account
3. Click "Get Started"
4. Accept terms and conditions

## Step 2: Create App in AdMob

1. Click "Apps" in the sidebar
2. Click "Add App"
3. Select "No" (app not published yet)
4. Select platform: "Android"
5. Enter app name: "Quotes App"
6. Click "Add"
7. **Copy the App ID** (format: ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX)

## Step 3: Create Ad Units

### Interstitial Ad Unit

1. In your app, click "Ad units"
2. Click "Get started" → "Interstitial"
3. Enter ad unit name: "Quote Refresh Interstitial"
4. Click "Create ad unit"
5. **Copy the Ad Unit ID** (format: ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX)

## Step 4: Update Android App

### Update AndroidManifest.xml

Replace the test App ID with your real App ID:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX"/>
```

### Update AdManager.kt

Replace the test Ad Unit ID:

```kotlin
companion object {
    private const val TAG = "AdManager"
    // Replace with your actual Ad Unit ID
    private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"
}
```

## Step 5: Test Ads

### During Development

The app currently uses test Ad Unit IDs. You'll see test ads with "Test Ad" label.

**Important**: Always use test IDs during development to avoid policy violations!

### Before Production

1. Replace all test IDs with your real Ad Unit IDs
2. Test on a real device
3. Verify ads load correctly
4. Check ad frequency (not too aggressive)

## Step 6: Ad Implementation Details

### Current Implementation

Ads are shown:
- After every 3-4 quote refreshes
- After sharing a quote
- With minimum 1-minute interval between ads

### Code Location

All ad logic is in:
- `app/src/main/java/com/quotes/app/utils/AdManager.kt`
- `app/src/main/java/com/quotes/app/MainActivity.kt`

## Step 7: AdMob Policies

### Important Guidelines

✅ **DO:**
- Use test ads during development
- Show ads at natural breaks (after actions)
- Respect user experience
- Follow ad frequency guidelines

❌ **DON'T:**
- Click your own ads
- Encourage users to click ads
- Show ads on app launch
- Show too many ads

### Recommended Frequency

- Maximum 1 ad per minute
- Maximum 3-4 ads per session
- Show ads after user actions (refresh, share)

## Step 8: Verify Integration

1. Build and run the app
2. Refresh quote 3-4 times
3. Ad should appear
4. Verify ad closes properly
5. Check AdMob dashboard for impressions

## Step 9: Optimize Revenue

### Best Practices

1. **Ad Placement**: After natural breaks (refresh, share)
2. **Frequency**: Not too aggressive (current: 3-4 refreshes)
3. **User Experience**: Don't interrupt core functionality
4. **Testing**: A/B test different frequencies

### Mediation (Advanced)

Consider adding mediation for higher revenue:
1. In AdMob, go to "Mediation"
2. Add networks (Facebook, Unity, etc.)
3. Configure waterfall

## Troubleshooting

### Ads Not Showing

**Check:**
- Internet connection
- Ad Unit IDs are correct
- AdMob account is active
- App is not in test mode with wrong device ID

**Common Issues:**
- "Ad failed to load" → Check internet, verify Ad Unit ID
- "No fill" → Normal, ads not always available
- Blank screen → Check implementation in AdManager.kt

### Test Ads Not Showing

Make sure you're using test Ad Unit IDs:
```kotlin
// Test Interstitial Ad Unit ID
private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
```

### Production Checklist

Before publishing:
- [ ] Replace all test Ad Unit IDs with real IDs
- [ ] Test on multiple devices
- [ ] Verify ad frequency is reasonable
- [ ] Check AdMob dashboard shows impressions
- [ ] Review AdMob policies compliance
- [ ] Add privacy policy (required for ads)

## Revenue Expectations

### Estimates (Varies by region)

- **eCPM**: $0.50 - $5.00
- **1000 users/day**: $5 - $50/day
- **10,000 users/day**: $50 - $500/day

**Note**: Actual revenue depends on:
- User location
- Ad quality
- User engagement
- Niche/category

## Support

For AdMob issues:
- [AdMob Help Center](https://support.google.com/admob)
- [AdMob Community](https://groups.google.com/g/google-admob-ads-sdk)
