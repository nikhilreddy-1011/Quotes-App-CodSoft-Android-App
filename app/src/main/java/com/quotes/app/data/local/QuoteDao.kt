package com.quotes.app.data.local

import androidx.room.*
import com.quotes.app.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    
    @Query("SELECT * FROM quotes WHERE isEnabled = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote?
    
    @Query("""
        SELECT * FROM quotes 
        WHERE isEnabled = 1 
        AND id NOT IN (
            SELECT id FROM quotes 
            WHERE isEnabled = 1 
            ORDER BY lastShownDate DESC 
            LIMIT 20
        )
        ORDER BY RANDOM() 
        LIMIT 1
    """)
    suspend fun getRandomQuoteNotRecentlyShown(): Quote?
    
    @Query("SELECT * FROM quotes WHERE category = :category AND isEnabled = 1")
    fun getQuotesByCategory(category: String): Flow<List<Quote>>
    
    @Query("""
        SELECT * FROM quotes 
        WHERE isEnabled = 1 
        AND (text LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%')
        ORDER BY text ASC
    """)
    fun searchQuotes(query: String): Flow<List<Quote>>
    
    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteQuotes(): Flow<List<Quote>>
    
    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getQuoteById(id: Int): Quote?
    
    @Query("UPDATE quotes SET isFavorite = :isFavorite, updatedAt = :timestamp WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE quotes SET lastShownDate = :timestamp WHERE id = :id")
    suspend fun updateLastShownDate(id: Int, timestamp: Long = System.currentTimeMillis())
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: Quote): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)
    
    @Update
    suspend fun updateQuote(quote: Quote)
    
    @Delete
    suspend fun deleteQuote(quote: Quote)
    
    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuoteCount(): Int
    
    @Query("SELECT * FROM quotes WHERE isEnabled = 1")
    fun getAllQuotes(): Flow<List<Quote>>
}
