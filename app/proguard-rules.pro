# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Room database classes
-keep class com.quotes.app.data.model.** { *; }
-keep class com.quotes.app.data.local.** { *; }

# Keep Firebase classes
-keep class com.google.firebase.** { *; }

# Keep AdMob classes
-keep class com.google.android.gms.ads.** { *; }
