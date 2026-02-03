package com.quotes.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.quotes.app.data.local.DatabaseSeeder
import com.quotes.app.data.local.QuoteDatabase
import com.quotes.app.data.model.Category
import com.quotes.app.data.model.Quote
import com.quotes.app.data.repository.QuoteRepository
import com.quotes.app.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = QuoteDatabase.getDatabase(application)
    private val repository = QuoteRepository(database.quoteDao())
    private val preferencesManager = PreferencesManager(application)
    
    private val _currentQuote = MutableStateFlow<Quote?>(null)
    val currentQuote: StateFlow<Quote?> = _currentQuote.asStateFlow()
    
    private val _favoriteQuotes = MutableStateFlow<List<Quote>>(emptyList())
    val favoriteQuotes: StateFlow<List<Quote>> = _favoriteQuotes.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<Quote>>(emptyList())
    val searchResults: StateFlow<List<Quote>> = _searchResults.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        // Seed database on first launch
        viewModelScope.launch {
            DatabaseSeeder.seedDatabase(application, database)
            loadQuoteOfTheDay()
            loadFavorites()
        }
    }
    
    /**
     * Load quote of the day
     */
    fun loadQuoteOfTheDay() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val quote = repository.getQuoteOfTheDay()
                quote?.let {
                    _currentQuote.value = it
                    repository.markQuoteAsShown(it.id)
                    preferencesManager.lastQuoteDate = System.currentTimeMillis()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Refresh quote manually
     */
    fun refreshQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val quote = repository.getQuoteOfTheDay()
                quote?.let {
                    _currentQuote.value = it
                    repository.markQuoteAsShown(it.id)
                    preferencesManager.incrementRefreshCount()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Toggle favorite status of current quote
     */
    fun toggleFavorite(quoteId: Int) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(quoteId)
                // Update current quote if it's the one being toggled
                if (_currentQuote.value?.id == quoteId) {
                    val updatedQuote = repository.getQuoteById(quoteId)
                    _currentQuote.value = updatedQuote
                }
                loadFavorites()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Load favorite quotes
     */
    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteQuotes().collect { quotes ->
                _favoriteQuotes.value = quotes
            }
        }
    }
    
    /**
     * Search quotes
     */
    fun searchQuotes(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _searchResults.value = emptyList()
                return@launch
            }
            
            repository.searchQuotes(query).collect { quotes ->
                _searchResults.value = quotes
            }
        }
    }
    
    /**
     * Filter quotes by category
     */
    fun filterByCategory(category: Category?) {
        viewModelScope.launch {
            _selectedCategory.value = category
            
            if (category == null) {
                _searchResults.value = emptyList()
                return@launch
            }
            
            repository.getQuotesByCategory(category.name).collect { quotes ->
                _searchResults.value = quotes
            }
        }
    }
    
    /**
     * Check if should show ad
     */
    fun shouldShowAd(): Boolean {
        val refreshCount = preferencesManager.quoteRefreshCount
        val canShowAd = preferencesManager.canShowAd()
        return refreshCount >= 3 && canShowAd
    }
    
    /**
     * Reset ad counter after showing ad
     */
    fun onAdShown() {
        preferencesManager.resetRefreshCount()
        preferencesManager.lastAdShownTime = System.currentTimeMillis()
    }
    
    /**
     * Check if onboarding is completed
     */
    fun isOnboardingCompleted(): Boolean {
        return preferencesManager.isOnboardingCompleted
    }
    
    /**
     * Mark onboarding as completed
     */
    fun completeOnboarding() {
        preferencesManager.isOnboardingCompleted = true
    }
    
    /**
     * Check if it's a new day (for daily quote refresh)
     */
    fun isNewDay(): Boolean {
        return preferencesManager.isNewDay()
    }
}
