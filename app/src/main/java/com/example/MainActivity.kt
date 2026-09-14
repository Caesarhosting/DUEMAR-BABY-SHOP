package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.ProductCatalog
import com.example.model.BabyProduct
import com.example.ui.DuemarViewModel
import com.example.ui.components.DuemarTopBar
import com.example.ui.formatRupiah
import com.example.ui.screens.CartScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ShopInfoScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.DuemarConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DuemarBabyShopApp()
            }
        }
    }
}

@Composable
fun DuemarBabyShopApp(
    viewModel: DuemarViewModel = viewModel()
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ViewModel Reactive State
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val cartCalculation by viewModel.cartCalculation.collectAsStateWithLifecycle()
    val appliedVoucher by viewModel.appliedVoucher.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotifCount by viewModel.unreadNotificationCount.collectAsStateWithLifecycle()
    val notificationPreferences by viewModel.notificationPreferences.collectAsStateWithLifecycle()

    // Snackbar Messages Listener
    LaunchedEffect(viewModel) {
        viewModel.userMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentRoute == "home") {
                DuemarTopBar(
                    cartItemCount = cartItems.sumOf { it.quantity },
                    favoriteCount = favorites.size,
                    unreadNotificationCount = unreadNotifCount,
                    onCartClick = { navController.navigate("cart") },
                    onFavoriteClick = { navController.navigate("wishlist") },
                    onNotificationClick = { navController.navigate("notifications") },
                    onOrdersClick = { navController.navigate("orders") },
                    onInfoClick = { navController.navigate("info") }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home Catalog Screen
            composable("home") {
                HomeScreen(
                    products = filteredProducts,
                    favorites = favorites,
                    searchQuery = searchQuery,
                    selectedCategory = selectedCategory,
                    sortOption = sortOption,
                    onSearchChange = { viewModel.setSearchQuery(it) },
                    onSelectCategory = { viewModel.setCategory(it) },
                    onSelectSort = { viewModel.setSortOption(it) },
                    onProductClick = { product ->
                        navController.navigate("detail/${product.id}")
                    },
                    onToggleFavorite = { productId ->
                        viewModel.toggleFavorite(productId)
                    },
                    onQuickAddToCart = { product ->
                        viewModel.addToCart(product, product.colorways.first(), 1)
                    },
                    onOpenNotifications = {
                        navController.navigate("notifications")
                    }
                )
            }

            // Product Detail & 3D Interactive Viewer Screen
            composable(
                route = "detail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                val product = ProductCatalog.getProductById(productId) ?: ProductCatalog.products.first()

                ProductDetailScreen(
                    product = product,
                    isFavorite = favorites.contains(product.id),
                    onBackClick = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavorite(product.id) },
                    onAddToCart = { prod, colorway, qty ->
                        viewModel.addToCart(prod, colorway, qty)
                    },
                    onBuyNow = { prod, colorway, qty ->
                        viewModel.addToCart(prod, colorway, qty)
                        navController.navigate("cart")
                    },
                    onConsultWhatsApp = { ctx, prod ->
                        val msg = "Halo Admin Duemar Baby Shop, saya ingin menanyakan produk *${prod.name}* seharga Rp ${formatRupiah(prod.price)}. Apakah stok masih tersedia?"
                        DuemarConfig.openWhatsApp(ctx, msg)
                    },
                    onReviewSubmitted = { msg ->
                        viewModel.showMessage(msg)
                    }
                )
            }

            // Cart & Checkout Screen
            composable("cart") {
                CartScreen(
                    cartItems = cartItems,
                    calculation = cartCalculation,
                    appliedVoucher = appliedVoucher,
                    onBackClick = { navController.popBackStack() },
                    onUpdateQuantity = { item, delta -> viewModel.updateQuantity(item, delta) },
                    onRemoveItem = { item -> viewModel.removeFromCart(item) },
                    onClearCart = { viewModel.clearCart() },
                    onApplyVoucher = { code -> viewModel.applyVoucher(code) },
                    onRemoveVoucher = { viewModel.removeVoucher() },
                    onWhatsAppOrder = { ctx, name, addr ->
                        viewModel.openWhatsAppOrder(ctx, name, addr) {
                            navController.navigate("orders") {
                                popUpTo("home")
                            }
                        }
                    },
                    onCheckoutSubmit = { ctx, name, phone, addr, payMethod, onDone ->
                        viewModel.checkoutOrder(name, phone, addr, payMethod, context = ctx) {
                            onDone()
                        }
                    },
                    onNavigateToOrders = {
                        navController.navigate("orders") {
                            popUpTo("home")
                        }
                    }
                )
            }

            // Wishlist / Favorites Screen
            composable("wishlist") {
                val favoriteList = ProductCatalog.products.filter { favorites.contains(it.id) }
                WishlistScreen(
                    favoriteProducts = favoriteList,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { product -> navController.navigate("detail/${product.id}") },
                    onToggleFavorite = { productId -> viewModel.toggleFavorite(productId) },
                    onQuickAddToCart = { product -> viewModel.addToCart(product, product.colorways.first(), 1) }
                )
            }

            // Digital Invoices & Order History Screen
            composable("orders") {
                OrderHistoryScreen(
                    orders = orders,
                    onBackClick = { navController.popBackStack() },
                    onForwardOrder = { ctx, order ->
                        viewModel.forwardOrderToAdmin(ctx, order)
                    }
                )
            }

            // Shop Profile & About Screen
            composable("info") {
                ShopInfoScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Notification Center & Alert Inbox Screen
            composable("notifications") {
                val context = androidx.compose.ui.platform.LocalContext.current
                NotificationsScreen(
                    notifications = notifications,
                    preferences = notificationPreferences,
                    onBackClick = { navController.popBackStack() },
                    onNotificationClick = { notif ->
                        viewModel.markNotificationAsRead(notif.id)
                        when {
                            notif.actionRoute.startsWith("detail/") -> {
                                navController.navigate(notif.actionRoute)
                            }
                            notif.targetProductId != null -> {
                                navController.navigate("detail/${notif.targetProductId}")
                            }
                            notif.actionRoute == "cart" -> {
                                navController.navigate("cart")
                            }
                            notif.actionRoute == "flash_sale" -> {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                            else -> {
                                navController.navigate(notif.actionRoute)
                            }
                        }
                    },
                    onMarkAllRead = { viewModel.markAllNotificationsAsRead() },
                    onDeleteNotification = { id -> viewModel.deleteNotification(id) },
                    onUpdatePreferences = { prefs -> viewModel.updateNotificationPreferences(prefs) },
                    onSimulateFlashSaleAlert = { viewModel.triggerFlashSaleAlert(context) },
                    onSimulateNewArrivalAlert = { viewModel.triggerNewArrivalAlert(context) }
                )
            }
        }
    }
}
