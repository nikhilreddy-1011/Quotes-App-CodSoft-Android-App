package com.quotes.app.data.model

enum class Category(val displayName: String, val icon: String) {
    MOTIVATION("Motivation", "💪"),
    SUCCESS("Success", "🏆"),
    LIFE("Life", "🌟"),
    LOVE("Love", "❤️"),
    STUDY("Study", "📚"),
    FITNESS("Fitness", "🏃"),
    SAD("Sad", "😢"),
    HAPPY("Happy", "😊");
    
    companion object {
        fun fromString(value: String): Category {
            return values().find { it.name.equals(value, ignoreCase = true) } ?: MOTIVATION
        }
    }
}
