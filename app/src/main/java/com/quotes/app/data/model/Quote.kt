package com.quotes.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val text: String,
    
    val author: String? = null,
    
    val category: String, // MOTIVATION, SUCCESS, LIFE, LOVE, STUDY, FITNESS, SAD, HAPPY
    
    val emoji: String? = null, // 😊🔥✨💪🌟
    
    val backgroundColor: String = "#F5F5F5", // Hex color or gradient
    
    val isFavorite: Boolean = false,
    
    val lastShownDate: Long = 0L, // Timestamp for quote rotation
    
    val isEnabled: Boolean = true, // Admin can disable quotes
    
    val createdAt: Long = System.currentTimeMillis(),
    
    val updatedAt: Long = System.currentTimeMillis()
)
