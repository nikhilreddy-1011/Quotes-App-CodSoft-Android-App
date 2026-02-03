package com.quotes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.quotes.app.data.model.Quote
import com.quotes.app.ui.favorites.FavoritesScreen
import com.quotes.app.ui.home.HomeScreen
import com.quotes.app.ui.onboarding.OnboardingScreen
import com.quotes.app.ui.search.SearchScreen
import com.quotes.app.ui.splash.SplashScreen
import com.quotes.app.ui.theme.QuotesAppTheme
import com.quotes.app.utils.AdManager
import com.quotes.app.utils.ShareHelper
import com.quotes.app.viewmodel.QuoteViewModel

class MainActivity : ComponentActivity() {
    
    private val viewModel: QuoteViewModel by viewModels()
    private lateinit var adManager: AdManager
    private lateinit var shareHelper: ShareHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AdManager and ShareHelper
        adManager = AdManager(this)
        adManager.initialize()
        shareHelper = ShareHelper(this)
        
        setContent {
            QuotesAppTheme {
                QuotesApp(
                    viewModel = viewModel,
                    onShare = { quote -> handleShare(quote) },
                    onShowAd = { handleAdDisplay() }
                )
            }
        }
    }
    
    private fun handleShare(quote: Quote) {
        // Show share options
        shareHelper.shareQuoteAsText(quote)
        
        // Show ad after sharing if conditions are met
        if (viewModel.shouldShowAd()) {
            adManager.showInterstitialAd(this) {
                viewModel.onAdShown()
            }
        }
    }
    
    private fun handleAdDisplay() {
        if (viewModel.shouldShowAd()) {
            adManager.showInterstitialAd(this) {
                viewModel.onAdShown()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesApp(
    viewModel: QuoteViewModel,
    onShare: (Quote) -> Unit,
    onShowAd: () -> Unit
) {
    val navController = rememberNavController()
    val currentQuote by viewModel.currentQuote.collectAsState()
    val favoriteQuotes by viewModel.favoriteQuotes.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    val isOnboardingCompleted = viewModel.isOnboardingCompleted()
    
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                isFirstLaunch = !isOnboardingCompleted
            )
        }
        
        composable("onboarding") {
            OnboardingScreen(
                onComplete = {
                    viewModel.completeOnboarding()
                    navController.navigate("main") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        
        composable("main") {
            MainScreen(
                currentQuote = currentQuote,
                favoriteQuotes = favoriteQuotes,
                searchResults = searchResults,
                selectedCategory = selectedCategory,
                isLoading = isLoading,
                onRefresh = {
                    viewModel.refreshQuote()
                    onShowAd()
                },
                onToggleFavorite = { quoteId ->
                    viewModel.toggleFavorite(quoteId)
                },
                onShare = onShare,
                onSearch = { query ->
                    viewModel.searchQuotes(query)
                },
                onCategorySelected = { category ->
                    viewModel.filterByCategory(category)
                },
                onQuoteClick = { quote ->
                    // Could navigate to detail screen or show dialog
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    currentQuote: Quote?,
    favoriteQuotes: List<Quote>,
    searchResults: List<Quote>,
    selectedCategory: com.quotes.app.data.model.Category?,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onShare: (Quote) -> Unit,
    onSearch: (String) -> Unit,
    onCategorySelected: (com.quotes.app.data.model.Category?) -> Unit,
    onQuoteClick: (Quote) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                quote = currentQuote,
                isLoading = isLoading,
                onRefresh = onRefresh,
                onToggleFavorite = onToggleFavorite,
                onShare = onShare
            )
            1 -> FavoritesScreen(
                favorites = favoriteQuotes,
                onToggleFavorite = onToggleFavorite,
                onQuoteClick = onQuoteClick
            )
            2 -> SearchScreen(
                searchResults = searchResults,
                selectedCategory = selectedCategory,
                onSearch = onSearch,
                onCategorySelected = onCategorySelected,
                onQuoteClick = onQuoteClick
            )
        }
    }
}
