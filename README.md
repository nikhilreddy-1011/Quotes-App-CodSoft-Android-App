# ✨ Quotes App - Android Application

A professional Quote of the Day Android application built with Kotlin, Jetpack Compose, and MVVM architecture. Features offline-first design with 800+ pre-loaded quotes, favorites management, search functionality, and AdMob monetization.

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange.svg)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-brightgreen.svg)

## 📱 Features

### Core Features
- **Quote of the Day**: Display inspiring quotes with emojis and beautiful backgrounds
- **Daily Refresh**: Automatic quote rotation with smart algorithm to prevent repeats
- **Offline-First**: 800+ quotes pre-loaded across 8 categories
- **Favorites**: Save and manage your favorite quotes
- **Search**: Find quotes by keyword or author
- **Categories**: Browse quotes by category (Motivation, Success, Life, Love, Study, Fitness, Sad, Happy)
- **Share**: Share quotes as text or beautiful images on social media
- **Onboarding**: 3-screen introduction for new users
- **AdMob Integration**: Monetization with interstitial ads

### Technical Features
- **MVVM Architecture**: Clean separation of concerns
- **Room Database**: Local persistence with 800+ quotes
- **Jetpack Compose**: Modern declarative UI
- **Material 3**: Latest Material Design guidelines
- **Firebase**: Optional cloud sync and admin panel
- **AdMob**: Interstitial ads after sharing and refreshes

## 🎨 Design

- **Primary Color**: Sky Blue (#87CEEB)
- **Background**: White (#FFFFFF)
- **Card Background**: Grey (#F5F5F5)
- **Text**: Black (#000000)
- **Theme**: Light mode only (as per requirements)

## 🏗️ Architecture

```
app/
├── data/
│   ├── local/          # Room database, DAO, seeder
│   ├── model/          # Data models (Quote, Category)
│   └── repository/     # Repository layer
├── ui/
│   ├── splash/         # Splash screen
│   ├── onboarding/     # Onboarding flow
│   ├── home/           # Main quote display
│   ├── favorites/      # Favorites list
│   ├── search/         # Search and categories
│   └── theme/          # App theme and colors
├── viewmodel/          # ViewModels
└── utils/              # Utilities (AdManager, ShareHelper, etc.)
```

## 📦 Dependencies

- **Jetpack Compose**: Modern UI toolkit
- **Room**: Local database
- **Navigation Compose**: Screen navigation
- **Firebase**: Cloud backend (optional)
- **AdMob**: Ad monetization
- **Accompanist**: Pager for onboarding
- **Coil**: Image loading
- **Gson**: JSON parsing

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK 34
- Minimum Android version: 7.0 (API 24)

### Installation

1. **Clone or download the project**
   ```bash
   cd "c:\Quotes App"
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - File → Open → Select "Quotes App" folder

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Configure Firebase (Optional)**
   - Create a Firebase project at https://console.firebase.google.com
   - Download `google-services.json`
   - Place it in `app/` directory
   - Update `admin-panel/firebase-config.js` with your credentials

5. **Configure AdMob**
   - Create an AdMob account at https://admob.google.com
   - Create an app and get your App ID
   - Replace the test Ad Unit ID in `AdManager.kt`:
     ```kotlin
     private const val INTERSTITIAL_AD_UNIT_ID = "YOUR_AD_UNIT_ID"
     ```
   - Update `AndroidManifest.xml` with your AdMob App ID:
     ```xml
     <meta-data
         android:name="com.google.android.gms.ads.APPLICATION_ID"
         android:value="YOUR_ADMOB_APP_ID"/>
     ```

6. **Build and Run**
   - Connect an Android device or start an emulator
   - Click Run (▶️) or press Shift+F10

## 📊 Database Schema

The app uses Room database with the following schema:

```sql
CREATE TABLE quotes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    text TEXT NOT NULL,
    author TEXT,
    category TEXT NOT NULL,
    emoji TEXT,
    backgroundColor TEXT,
    isFavorite INTEGER DEFAULT 0,
    lastShownDate INTEGER,
    isEnabled INTEGER DEFAULT 1,
    createdAt INTEGER,
    updatedAt INTEGER
);
```

## 🎯 Quote Categories

1. **Motivation** 💪 - 100 quotes
2. **Success** 🏆 - 100 quotes
3. **Life** 🌟 - 100 quotes
4. **Love** ❤️ - 100 quotes
5. **Study** 📚 - 100 quotes
6. **Fitness** 🏃 - 100 quotes
7. **Sad** 😢 - 100 quotes
8. **Happy** 😊 - 100 quotes

**Total: 800+ quotes pre-loaded**

## 🔧 Admin Panel

A web-based admin panel is included for managing quotes remotely.

### Setup

1. Navigate to `admin-panel/` directory
2. Update `firebase-config.js` with your Firebase credentials
3. Open `index.html` in a web browser
4. Login with Firebase admin credentials

### Features

- Add new quotes with emoji and background color
- Edit existing quotes
- Enable/disable quotes
- Delete quotes
- Filter by category
- Search quotes
- View statistics

## 💰 Monetization Strategy

### AdMob Implementation

- **Interstitial Ads** shown:
  - After every 3-4 quote refreshes
  - After sharing quotes
  - Minimum 1-minute interval between ads

### Future Monetization

- Premium version (ad-free) - $2.99
- Custom backgrounds pack - $0.99
- Cloud backup feature
- Widget support

## 📱 Play Store Preparation

### Required Assets

1. **App Icon**: 512x512 PNG
2. **Feature Graphic**: 1024x500 PNG
3. **Screenshots**: 8 screenshots (phone + tablet)
4. **Privacy Policy**: Required for AdMob

### Store Listing

- **Title**: Quotes App - Daily Inspiration
- **Short Description**: Get inspired daily with beautiful quotes
- **Category**: Lifestyle
- **Content Rating**: Everyone
- **Target Audience**: 13+

## 🧪 Testing

### Manual Testing Checklist

- [ ] Splash screen displays for 2 seconds
- [ ] Onboarding shows on first launch only
- [ ] Quote displays with emoji and background
- [ ] Refresh button loads new quote
- [ ] Favorite button toggles correctly
- [ ] Share functionality works
- [ ] Search finds quotes by keyword
- [ ] Category filters work
- [ ] Favorites screen shows saved quotes
- [ ] Ads display after 3-4 refreshes
- [ ] App works offline

## 🐛 Troubleshooting

### Common Issues

**Build fails with "google-services.json not found"**
- Solution: Either add Firebase configuration or remove Firebase dependencies temporarily

**Ads not showing**
- Solution: Make sure you're using test Ad Unit IDs during development
- Check internet connection
- Verify AdMob account is active

**Database empty**
- Solution: Clear app data and reinstall to trigger database seeding

## 📄 License

This project is created for educational and commercial use.

## 🤝 Contributing

This is a complete, production-ready application. Feel free to customize it for your needs.

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review the implementation plan
3. Check Android Studio logs

## 🎉 Credits

- **Architecture**: MVVM with Jetpack Compose
- **Database**: Room Persistence Library
- **UI**: Material Design 3
- **Ads**: Google AdMob
- **Backend**: Firebase (optional)

---

**Built with ❤️ using Kotlin and Jetpack Compose**
