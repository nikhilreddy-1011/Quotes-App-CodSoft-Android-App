# Firebase Setup Guide

This guide will help you set up Firebase for the Quotes App admin panel and optional cloud sync.

## Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Click "Add project"
3. Enter project name: "Quotes App"
4. Disable Google Analytics (optional)
5. Click "Create project"

## Step 2: Add Android App

1. In Firebase Console, click "Add app" → Android icon
2. Enter package name: `com.quotes.app`
3. Enter app nickname: "Quotes App"
4. Click "Register app"
5. Download `google-services.json`
6. Place file in `app/` directory

## Step 3: Enable Firestore

1. In Firebase Console, go to "Firestore Database"
2. Click "Create database"
3. Select "Start in test mode" (for development)
4. Choose a location (closest to your users)
5. Click "Enable"

## Step 4: Set Up Authentication

1. In Firebase Console, go to "Authentication"
2. Click "Get started"
3. Enable "Email/Password" sign-in method
4. Add your admin email and password

## Step 5: Configure Admin Panel

1. In Firebase Console, go to "Project settings" → "General"
2. Scroll to "Your apps" → "Web apps"
3. Click "Add app" → Enter nickname: "Admin Panel"
4. Copy the Firebase configuration
5. Update `admin-panel/firebase-config.js`:

```javascript
const firebaseConfig = {
    apiKey: "YOUR_API_KEY",
    authDomain: "YOUR_PROJECT_ID.firebaseapp.com",
    projectId: "YOUR_PROJECT_ID",
    storageBucket: "YOUR_PROJECT_ID.appspot.com",
    messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
    appId: "YOUR_APP_ID"
};
```

## Step 6: Firestore Security Rules

In Firestore, go to "Rules" and update:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Allow authenticated users to read quotes
    match /quotes/{quoteId} {
      allow read: if true;
      allow write: if request.auth != null;
    }
  }
}
```

## Step 7: Test Admin Panel

1. Open `admin-panel/index.html` in a web browser
2. Login with your admin credentials
3. Try adding a test quote
4. Verify it appears in Firestore Console

## Optional: Cloud Sync (Advanced)

To sync quotes from Firestore to the Android app:

1. Add Firebase SDK to Android app (already included)
2. Implement sync logic in `QuoteRepository.kt`
3. Use WorkManager for periodic background sync

## Troubleshooting

**"Permission denied" error**
- Check Firestore security rules
- Verify user is authenticated

**Admin panel won't load**
- Check browser console for errors
- Verify Firebase config is correct
- Make sure internet connection is active

**Quotes not syncing to app**
- Implement sync logic in repository
- Check Firebase connection in Android app
