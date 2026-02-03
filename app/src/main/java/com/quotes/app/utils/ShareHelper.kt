package com.quotes.app.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.core.content.FileProvider
import com.quotes.app.data.model.Quote
import java.io.File
import java.io.FileOutputStream

class ShareHelper(private val context: Context) {
    
    /**
     * Share quote as text
     */
    fun shareQuoteAsText(quote: Quote) {
        val shareText = buildString {
            append("\"${quote.text}\"\n\n")
            if (!quote.author.isNullOrEmpty()) {
                append("- ${quote.author}\n\n")
            }
            append("Shared from Quotes App")
        }
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        context.startActivity(Intent.createChooser(intent, "Share Quote"))
    }
    
    /**
     * Share quote as image
     */
    fun shareQuoteAsImage(quote: Quote) {
        try {
            val bitmap = createQuoteImage(quote)
            val imageUri = saveBitmapToCache(bitmap)
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            context.startActivity(Intent.createChooser(intent, "Share Quote Image"))
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to text sharing
            shareQuoteAsText(quote)
        }
    }
    
    /**
     * Create an image from the quote
     */
    private fun createQuoteImage(quote: Quote): Bitmap {
        val width = 1080
        val height = 1080
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Draw background
        val bgColor = try {
            Color.parseColor(quote.backgroundColor)
        } catch (e: Exception) {
            Color.parseColor("#F5F5F5")
        }
        canvas.drawColor(bgColor)
        
        // Setup text paint
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 60f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        // Draw quote text (with word wrapping)
        val quoteText = "\"${quote.text}\""
        val words = quoteText.split(" ")
        var line = ""
        var y = height / 2f - 100
        
        for (word in words) {
            val testLine = if (line.isEmpty()) word else "$line $word"
            val textWidth = textPaint.measureText(testLine)
            
            if (textWidth > width - 200) {
                canvas.drawText(line, width / 2f, y, textPaint)
                line = word
                y += 80
            } else {
                line = testLine
            }
        }
        canvas.drawText(line, width / 2f, y, textPaint)
        
        // Draw author
        if (!quote.author.isNullOrEmpty()) {
            val authorPaint = Paint().apply {
                color = Color.DKGRAY
                textSize = 40f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText("- ${quote.author}", width / 2f, y + 100, authorPaint)
        }
        
        // Draw app branding
        val brandPaint = Paint().apply {
            color = Color.GRAY
            textSize = 30f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("Quotes App", width / 2f, height - 50f, brandPaint)
        
        return bitmap
    }
    
    /**
     * Save bitmap to cache directory and return URI
     */
    private fun saveBitmapToCache(bitmap: Bitmap): Uri {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        
        val file = File(cachePath, "quote_${System.currentTimeMillis()}.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}
