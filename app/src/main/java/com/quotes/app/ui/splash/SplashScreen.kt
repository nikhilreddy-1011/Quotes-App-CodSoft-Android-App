package com.quotes.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quotes.app.ui.theme.SkyBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    isFirstLaunch: Boolean
) {
    var textToShow by remember { mutableStateOf("") }
    val fullText = "Quotes of the day"
    
    LaunchedEffect(Unit) {
        // Typing animation - reveal one character at a time (2x speed = 50ms)
        for (i in fullText.indices) {
            textToShow = fullText.substring(0, i + 1)
            delay(50) // 50ms between each character for 2x speed
        }
        
        // Wait a bit after typing completes
        delay(400)
        
        // Navigate to appropriate screen
        if (isFirstLaunch) {
            onNavigateToOnboarding()
        } else {
            onNavigateToHome()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // Typing text with cursor effect
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = textToShow,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = SkyBlue
            )
            
            // Blinking cursor
            if (textToShow.length < fullText.length) {
                BlinkingCursor()
            }
        }

        
        // Footer text
        Text(
            text = "Internship project by Nikhil K",
            fontSize = 12.sp,
            color = Color.Black.copy(alpha = 0.3f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
fun BlinkingCursor() {
    val infiniteTransition = rememberInfiniteTransition(label = "cursor")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing), // Smoother 60fps animation
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )
    
    Text(
        text = "|",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = SkyBlue.copy(alpha = alpha),
        modifier = Modifier.padding(start = 2.dp)
    )
}
