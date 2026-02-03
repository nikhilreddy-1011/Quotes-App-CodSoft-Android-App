package com.quotes.app.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quotes.app.data.model.Quote

@Composable
fun HomeScreen(
    quote: Quote?,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onShare: (Quote) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else if (quote != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Quote Card with copy button
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .border(
                                width = 1.dp,
                                color = Color.Black.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(24.dp)
                            ),
                        shape = RoundedCornerShape(24.dp),
                        color = parseColor(quote.backgroundColor),
                        shadowElevation = 0.dp,
                        tonalElevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Quote emoji
                            if (!quote.emoji.isNullOrEmpty()) {
                                Text(
                                    text = quote.emoji,
                                    fontSize = 48.sp,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }
                            
                            // Quote text
                            Text(
                                text = "\"${quote.text}\"",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 32.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            // Author
                            if (!quote.author.isNullOrEmpty()) {
                                Text(
                                    text = "— ${quote.author}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    
                    // Copy button overlay in top-right corner
                    val context = androidx.compose.ui.platform.LocalContext.current
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val clip = android.content.ClipData.newPlainText("quote", "\"${quote.text}\" - ${quote.author ?: "Unknown"}")
                            clipboard.setPrimaryClip(clip)
                            
                            // Show toast
                            android.widget.Toast.makeText(context, "Quote copied!", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Quote",
                            tint = Color.Black.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Refresh button
                    FloatingActionButton(
                        onClick = onRefresh,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Quote"
                        )
                    }
                    
                    // Favorite button
                    FloatingActionButton(
                        onClick = { onToggleFavorite(quote.id) },
                        containerColor = if (quote.isFavorite) 
                            MaterialTheme.colorScheme.error 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (quote.isFavorite) 
                            MaterialTheme.colorScheme.onError 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Icon(
                            imageVector = if (quote.isFavorite) 
                                Icons.Default.Favorite 
                            else 
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite"
                        )
                    }
                    
                    // Share button
                    FloatingActionButton(
                        onClick = { onShare(quote) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Quote"
                        )
                    }
                }
            }
        } else {
            // No quote available
            Text(
                text = "No quote available",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun parseColor(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        Color(0xFFF5F5F5) // Default grey
    }
}
