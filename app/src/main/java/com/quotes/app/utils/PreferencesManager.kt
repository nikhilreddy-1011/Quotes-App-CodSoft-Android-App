package com.quotes.app.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "quotes_app_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_LAST_QUOTE_DATE = "last_quote_date"
        private const val KEY_QUOTE_REFRESH_COUNT = "quote_refresh_count"
        private const val KEY_LAST_AD_SHOWN_TIME = "last_ad_shown_time"
    }
    
    /**
     * Check if onboarding has been completed
     */
    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, value).apply()
    
    /**
     * Get the date when the last quote was shown
     */
    var lastQuoteDate: Long
        get() = prefs.getLong(KEY_LAST_QUOTE_DATE, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_QUOTE_DATE, value).apply()
    
    /**
     * Get the quote refresh count (for ad display logic)
     */
    var quoteRefreshCount: Int
        get() = prefs.getInt(KEY_QUOTE_REFRESH_COUNT, 0)
        set(value) = prefs.edit().putInt(KEY_QUOTE_REFRESH_COUNT, value).apply()
    
    /**
     * Get the last time an ad was shown
     */
    var lastAdShownTime: Long
        get() = prefs.getLong(KEY_LAST_AD_SHOWN_TIME, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_AD_SHOWN_TIME, value).apply()
    
    /**
     * Increment quote refresh count
     */
    fun incrementRefreshCount() {
        quoteRefreshCount++
    }
    
    /**
     * Reset quote refresh count (after showing ad)
     */
    fun resetRefreshCount() {
        quoteRefreshCount = 0
    }
    
    /**
     * Check if a new day has started (for daily quote refresh)
     */
    fun isNewDay(): Boolean {
        val currentDate = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val lastDate = lastQuoteDate / (1000 * 60 * 60 * 24)
        return currentDate > lastDate
    }
    
    /**
     * Check if enough time has passed since last ad (minimum 1 minute)
     */
    fun canShowAd(): Boolean {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastAd = currentTime - lastAdShownTime
        return timeSinceLastAd > 60000 // 1 minute
    }
}
