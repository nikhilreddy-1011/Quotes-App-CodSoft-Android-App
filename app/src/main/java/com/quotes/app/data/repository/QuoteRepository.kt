package com.quotes.app.data.repository

import com.quotes.app.data.local.QuoteDao
import com.quotes.app.data.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class QuoteRepository(private val quoteDao: QuoteDao) {
    
    /**
     * Get a random quote that hasn't been shown recently
     */
    suspend fun getQuoteOfTheDay(): Quote? {
        // Try to get a quote that wasn't shown in the last 20 quotes
        val quote = quoteDao.getRandomQuoteNotRecentlyShown()
        
        // If all quotes have been shown recently, just get any random quote
        return quote ?: quoteDao.getRandomQuote()
    }
    
    /**
     * Get quotes by category
     */
    fun getQuotesByCategory(category: String): Flow<List<Quote>> {
        return quoteDao.getQuotesByCategory(category)
    }
    
    /**
     * Search quotes by text or author
     */
    fun searchQuotes(query: String): Flow<List<Quote>> {
        return quoteDao.searchQuotes(query)
    }
    
    /**
     * Get all favorite quotes
     */
    fun getFavoriteQuotes(): Flow<List<Quote>> {
        return quoteDao.getFavoriteQuotes()
    }
    
    /**
     * Toggle favorite status of a quote
     */
    suspend fun toggleFavorite(quoteId: Int) {
        val quote = quoteDao.getQuoteById(quoteId)
        quote?.let {
            quoteDao.updateFavoriteStatus(quoteId, !it.isFavorite)
        }
    }
    
    /**
     * Mark a quote as shown
     */
    suspend fun markQuoteAsShown(quoteId: Int) {
        quoteDao.updateLastShownDate(quoteId, System.currentTimeMillis())
    }
    
    /**
     * Get all quotes
     */
    fun getAllQuotes(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes()
    }
    
    /**
     * Get quote by ID
     */
    suspend fun getQuoteById(id: Int): Quote? {
        return quoteDao.getQuoteById(id)
    }
    
    /**
     * Insert a new quote (for admin functionality)
     */
    suspend fun insertQuote(quote: Quote): Long {
        return quoteDao.insertQuote(quote)
    }
    
    /**
     * Update an existing quote
     */
    suspend fun updateQuote(quote: Quote) {
        quoteDao.updateQuote(quote)
    }
    
    /**
     * Delete a quote
     */
    suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuote(quote)
    }
}
