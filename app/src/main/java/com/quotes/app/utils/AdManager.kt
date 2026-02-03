package com.quotes.app.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdManager(private val context: Context) {
    
    private var interstitialAd: InterstitialAd? = null
    private var isAdLoading = false
    
    companion object {
        private const val TAG = "AdManager"
        // Test Ad Unit ID - Replace with your actual Ad Unit ID from AdMob
        private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712" // Test ID
    }
    
    /**
     * Initialize AdMob
     */
    fun initialize() {
        MobileAds.initialize(context) { initializationStatus ->
            Log.d(TAG, "AdMob initialized: ${initializationStatus.adapterStatusMap}")
        }
        loadInterstitialAd()
    }
    
    /**
     * Load an interstitial ad
     */
    private fun loadInterstitialAd() {
        if (isAdLoading) {
            Log.d(TAG, "Ad is already loading")
            return
        }
        
        isAdLoading = true
        val adRequest = AdRequest.Builder().build()
        
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Ad failed to load: ${adError.message}")
                    interstitialAd = null
                    isAdLoading = false
                }
                
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Ad loaded successfully")
                    interstitialAd = ad
                    isAdLoading = false
                }
            }
        )
    }
    
    /**
     * Show the interstitial ad
     */
    fun showInterstitialAd(activity: Activity, onAdDismissed: () -> Unit = {}) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad dismissed")
                    interstitialAd = null
                    loadInterstitialAd() // Load next ad
                    onAdDismissed()
                }
                
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "Ad failed to show: ${adError.message}")
                    interstitialAd = null
                    loadInterstitialAd()
                }
                
                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content")
                }
            }
            
            interstitialAd?.show(activity)
        } else {
            Log.d(TAG, "Ad not ready yet")
            loadInterstitialAd()
            onAdDismissed()
        }
    }
    
    /**
     * Check if ad is ready to show
     */
    fun isAdReady(): Boolean {
        return interstitialAd != null
    }
}
