package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.NotificationCatalog
import com.example.data.ProductCatalog
import com.example.data.local.BabyShopDatabase
import com.example.data.local.BabyShopRepository
import com.example.data.local.CartItemEntity
import com.example.data.local.OrderEntity
import com.example.model.BabyProduct
import com.example.model.NotificationPreferences
import com.example.model.ProductCategory
import com.example.model.ProductColorway
import com.example.model.ShopNotification
import com.example.util.DuemarConfig
import com.example.util.NotificationHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val title: String) {
    TERMURAH("Termurah (Rp 25rb)"),
    TERPOPULER("Paling Laris"),
    RATING("Rating Tertinggi"),
    TERMAHAL("Harga Tertinggi")
}

data class CartCalculation(
    val subtotal: Int,
    val wholesaleDiscount: Int, // 5% if total items >= 3
    val voucherDiscount: Int,
    val shippingFee: Int,
    val finalTotal: Int,
    val totalItems: Int
)

class DuemarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BabyShopRepository(BabyShopDatabase.getInstance(application))

    val cartItems: StateFlow<List<CartItemEntity>> = repository.allCartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<Set<String>> = repository.allFavorites
        .combine(MutableStateFlow(Unit)) { favs, _ -> favs.map { it.productId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val orders: StateFlow<List<OrderEntity>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Notification System State
    private val _notifications = MutableStateFlow<List<ShopNotification>>(NotificationCatalog.initialNotifications)
    val notifications: StateFlow<List<ShopNotification>> = _notifications.asStateFlow()

    val unreadNotificationCount: StateFlow<Int> = _notifications
        .combine(MutableStateFlow(Unit)) { notifs, _ -> notifs.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotificationCatalog.initialNotifications.count { !it.isRead })

    private val _notificationPreferences = MutableStateFlow(NotificationPreferences())
    val notificationPreferences: StateFlow<NotificationPreferences> = _notificationPreferences.asStateFlow()

    // Search and Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ProductCategory.SEMUA)
    val selectedCategory: StateFlow<ProductCategory> = _selectedCategory.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.TERMURAH)
    val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()

    private val _maxPriceFilter = MutableStateFlow(300000)
    val maxPriceFilter: StateFlow<Int> = _maxPriceFilter.asStateFlow()

    // Voucher code
    private val _appliedVoucher = MutableStateFlow<String?>("HEMATNEWBORN")
    val appliedVoucher: StateFlow<String?> = _appliedVoucher.asStateFlow()

    // Message events
    private val _userMessage = MutableSharedFlow<String>()
    val userMessage: SharedFlow<String> = _userMessage.asSharedFlow()

    // Filtered Products
    val filteredProducts: StateFlow<List<BabyProduct>> = combine(
        _searchQuery,
        _selectedCategory,
        _sortOption,
        _maxPriceFilter
    ) { query, category, sort, maxPrice ->
        var list = ProductCatalog.products.filter { it.price <= maxPrice }

        if (category != ProductCategory.SEMUA) {
            list = list.filter { it.category == category }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.shortDescription.lowercase().contains(q) ||
                it.features.any { f -> f.lowercase().contains(q) }
            }
        }

        when (sort) {
            SortOption.TERMURAH -> list.sortedBy { it.price }
            SortOption.TERPOPULER -> list.sortedByDescending { it.soldCount }
            SortOption.RATING -> list.sortedByDescending { it.rating }
            SortOption.TERMAHAL -> list.sortedByDescending { it.price }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductCatalog.products)

    // Dynamic Cart Calculations
    val cartCalculation: StateFlow<CartCalculation> = combine(
        cartItems,
        _appliedVoucher
    ) { items, voucher ->
        val subtotal = items.sumOf { it.price * it.quantity }
        val totalCount = items.sumOf { it.quantity }

        // Wholesale discount (5% if items >= 3)
        val wholesaleDiscount = if (totalCount >= 3) (subtotal * 0.05).toInt() else 0

        // Voucher discount
        val voucherDiscount = when (voucher) {
            "HEMATNEWBORN" -> if (subtotal >= 100000) 15000 else 0
            "GRATISONGKIR" -> 12000
            "DUEMARHEMAT" -> 10000
            else -> 0
        }

        // Standard flat shipping fee (efisien)
        val baseShipping = if (subtotal > 0) 12000 else 0
        val effectiveShipping = if (voucher == "GRATISONGKIR") 0 else baseShipping

        val finalTotal = (subtotal - wholesaleDiscount - voucherDiscount + effectiveShipping).coerceAtLeast(0)

        CartCalculation(
            subtotal = subtotal,
            wholesaleDiscount = wholesaleDiscount,
            voucherDiscount = voucherDiscount,
            shippingFee = effectiveShipping,
            finalTotal = finalTotal,
            totalItems = totalCount
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartCalculation(0, 0, 0, 0, 0, 0))

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: ProductCategory) {
        _selectedCategory.value = category
    }

    fun setSortOption(sort: SortOption) {
        _sortOption.value = sort
    }

    fun setMaxPrice(max: Int) {
        _maxPriceFilter.value = max
    }

    fun applyVoucher(code: String) {
        val upper = code.trim().uppercase()
        if (upper in listOf("HEMATNEWBORN", "GRATISONGKIR", "DUEMARHEMAT")) {
            _appliedVoucher.value = upper
            emitMessage("Voucher $upper berhasil diterapkan!")
        } else {
            emitMessage("Kode voucher tidak valid")
        }
    }

    fun removeVoucher() {
        _appliedVoucher.value = null
        emitMessage("Voucher dihapus")
    }

    fun addToCart(product: BabyProduct, colorway: ProductColorway, quantity: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(
                productId = product.id,
                productName = product.name,
                price = product.price,
                quantity = quantity,
                colorName = colorway.name,
                colorHex = colorway.hexCode,
                note = "Varian: ${colorway.patternName}"
            )
            _userMessage.emit("${product.name} dimasukkan ke keranjang!")
        }
    }

    fun updateQuantity(item: CartItemEntity, delta: Int) {
        viewModelScope.launch {
            val newQty = item.quantity + delta
            if (newQty <= 0) {
                repository.removeCartItem(item)
                _userMessage.emit("${item.productName} dihapus dari keranjang")
            } else {
                repository.updateCartItem(item.copy(quantity = newQty))
            }
        }
    }

    fun removeFromCart(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeCartItem(item)
            _userMessage.emit("${item.productName} dihapus dari keranjang")
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            _userMessage.emit("Keranjang berhasil dikosongkan")
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            val isFav = favorites.value.contains(productId)
            repository.toggleFavorite(productId, isFav)
            _userMessage.emit(if (isFav) "Dihapus dari favorit" else "Disimpan ke favorit")
        }
    }

    fun checkoutOrder(
        customerName: String,
        customerPhone: String,
        customerAddress: String,
        paymentMethod: String,
        context: Context? = null,
        onSuccess: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) {
                _userMessage.emit("Keranjang Anda masih kosong!")
                return@launch
            }
            val calc = cartCalculation.value
            val summary = items.joinToString("\n") {
                "- ${it.productName} (${it.selectedColorName}) x${it.quantity} = Rp ${formatRupiah(it.price * it.quantity)}"
            }

            val orderId = repository.createOrder(
                customerName = customerName,
                customerPhone = customerPhone,
                customerAddress = customerAddress,
                itemsSummary = summary,
                subtotal = calc.subtotal,
                discount = calc.wholesaleDiscount + calc.voucherDiscount,
                shippingFee = calc.shippingFee,
                totalAmount = calc.finalTotal,
                paymentMethod = paymentMethod
            )

            // Clear the cart so the shopping transaction is finalized
            repository.clearCart()

            // Prepare automatic transaction invoice message for Admin/CS WhatsApp
            val itemsDetailed = items.mapIndexed { idx, it ->
                "${idx + 1}. *${it.productName}*\n   Motif/Warna: ${it.selectedColorName}\n   Qty: ${it.quantity} x Rp ${formatRupiah(it.price)} = Rp ${formatRupiah(it.price * it.quantity)}"
            }.joinToString("\n\n")

            val transactionInvoice = """
*TRANSAKSI BARU - DUEMAR BABY SHOP*
No. Transaksi: *#DMR-$orderId*
Status: *Menunggu Konfirmasi CS*

*DATA PEMBELI:*
• Nama: ${customerName.ifBlank { "Pelanggan Duemar" }}
• No. WhatsApp / HP: ${customerPhone.ifBlank { "-" }}
• Alamat Pengiriman: ${customerAddress.ifBlank { "Diambil di Toko" }}

*RINCIAN BARANG:*
$itemsDetailed

----------------------------------
*RINCIAN PEMBAYARAN:*
• Subtotal: Rp ${formatRupiah(calc.subtotal)}
${if (calc.wholesaleDiscount > 0) "• Diskon Grosir (5%): -Rp ${formatRupiah(calc.wholesaleDiscount)}\n" else ""}${if (calc.voucherDiscount > 0) "• Potongan Voucher: -Rp ${formatRupiah(calc.voucherDiscount)}\n" else ""}• Ongkos Kirim: ${if (calc.shippingFee == 0) "GRATIS ONGKIR" else "Rp " + formatRupiah(calc.shippingFee)}
*TOTAL AKHIR: Rp ${formatRupiah(calc.finalTotal)}*
• Metode Pembayaran: *$paymentMethod*

Halo Admin Duemar Baby Shop, saya baru saja melakukan pemesanan via aplikasi dengan nomor transaksi *#DMR-$orderId*. Mohon diproses dan dikonfirmasi rekening tujuannya. Terima kasih!
            """.trimIndent()

            // Automatically open WhatsApp transaction to Admin/CS (6281539268446)
            if (context != null) {
                val opened = DuemarConfig.openWhatsApp(context, transactionInvoice)
                if (opened) {
                    _userMessage.emit("Pesanan #DMR-$orderId berhasil dibuat & otomatis diteruskan ke Admin WhatsApp!")
                } else {
                    _userMessage.emit("Pesanan #DMR-$orderId tersimpan! Buka WhatsApp untuk konfirmasi ke Admin.")
                }
            } else {
                _userMessage.emit("Pesanan #DMR-$orderId berhasil dibuat!")
            }

            onSuccess(orderId)
        }
    }

    fun openWhatsAppOrder(context: Context, customerName: String, customerAddress: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) {
                emitMessage("Keranjang masih kosong!")
                return@launch
            }
            val calc = cartCalculation.value
            val summary = items.joinToString("\n") {
                "- ${it.productName} (${it.selectedColorName}) x${it.quantity} = Rp ${formatRupiah(it.price * it.quantity)}"
            }

            // Record transaction automatically in local database
            val orderId = repository.createOrder(
                customerName = customerName.ifBlank { "Bunda/Ayah" },
                customerPhone = "Order via WhatsApp CS",
                customerAddress = customerAddress.ifBlank { "Konfirmasi via WhatsApp" },
                itemsSummary = summary,
                subtotal = calc.subtotal,
                discount = calc.wholesaleDiscount + calc.voucherDiscount,
                shippingFee = calc.shippingFee,
                totalAmount = calc.finalTotal,
                paymentMethod = "WhatsApp CS (Admin 0815-3926-8446)"
            )

            // Clear the cart
            repository.clearCart()

            val itemsDetailed = items.mapIndexed { idx, it ->
                "${idx + 1}. *${it.productName}*\n   Motif/Varian: ${it.selectedColorName}\n   Qty: ${it.quantity} x Rp ${formatRupiah(it.price)} = Rp ${formatRupiah(it.price * it.quantity)}"
            }.joinToString("\n\n")

            val message = """
*PESANAN CEPAT - DUEMAR BABY SHOP*
No. Pesanan: *#DMR-$orderId*

Halo Admin Duemar Baby Shop, saya ingin memesan perlengkapan bayi:

$itemsDetailed

----------------------------------
*Rincian Pembayaran:*
• Subtotal: Rp ${formatRupiah(calc.subtotal)}
${if (calc.wholesaleDiscount > 0) "• Diskon Grosir: -Rp ${formatRupiah(calc.wholesaleDiscount)}\n" else ""}${if (calc.voucherDiscount > 0) "• Potongan Voucher: -Rp ${formatRupiah(calc.voucherDiscount)}\n" else ""}• Ongkir: ${if (calc.shippingFee == 0) "GRATIS" else "Rp " + formatRupiah(calc.shippingFee)}
*TOTAL AKHIR: Rp ${formatRupiah(calc.finalTotal)}*

*Data Pembeli:*
• Nama: ${customerName.ifBlank { "Bunda/Ayah" }}
• Alamat: ${customerAddress.ifBlank { "Konfirmasi di chat" }}

Mohon info nomor rekening dan proses pengirimannya ya kak Admin. Terima kasih!
            """.trimIndent()

            val opened = DuemarConfig.openWhatsApp(context, message)
            if (opened) {
                _userMessage.emit("Pesanan #DMR-$orderId dicatat & dialihkan ke Admin CS WhatsApp!")
            } else {
                _userMessage.emit("Pesanan #DMR-$orderId tersimpan!")
            }
            onSuccess()
        }
    }

    fun forwardOrderToAdmin(context: Context, order: OrderEntity) {
        val message = """
*KONFIRMASI PESANAN - DUEMAR BABY SHOP*
No. Pesanan: *${order.orderNumber}*
Status: *${order.status}*

*Data Pembeli:*
• Nama: ${order.customerName}
• No. HP/WA: ${order.customerPhone}
• Alamat: ${order.customerAddress}

*Rincian Barang:*
${order.itemsSummary}

*Total Pembayaran: Rp ${formatRupiah(order.totalAmount)}*
• Metode: ${order.paymentMethod}

Halo Admin Duemar Baby Shop (${DuemarConfig.ADMIN_WHATSAPP_DISPLAY}), saya ingin mengonfirmasi kelanjutan pesanan transaksi *${order.orderNumber}*. Terima kasih!
        """.trimIndent()

        DuemarConfig.openWhatsApp(context, message)
    }

    fun markNotificationAsRead(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    fun markAllNotificationsAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
        emitMessage("Semua notifikasi telah ditandai dibaca")
    }

    fun deleteNotification(id: String) {
        _notifications.value = _notifications.value.filterNot { it.id == id }
    }

    fun updateNotificationPreferences(prefs: NotificationPreferences) {
        _notificationPreferences.value = prefs
        emitMessage("Preferensi notifikasi diperbarui")
    }

    fun triggerFlashSaleAlert(context: Context) {
        val alert = NotificationCatalog.createFlashSaleAlert(
            productName = "Kasur Bayi Kolam & Bantal Menyusui",
            discount = 35,
            productId = "p3"
        )
        _notifications.value = listOf(alert) + _notifications.value
        NotificationHelper.showSystemNotification(context, alert)
        emitMessage("⚡ Notifikasi Flash Sale dikirim!")
    }

    fun triggerNewArrivalAlert(context: Context) {
        val alert = NotificationCatalog.createNewArrivalAlert(
            productName = "Piyama Bayi Katun Bamboo Ultra Soft",
            productId = "p8",
            categoryName = "Pakaian Bayi"
        )
        _notifications.value = listOf(alert) + _notifications.value
        NotificationHelper.showSystemNotification(context, alert)
        emitMessage("✨ Notifikasi Produk Baru dikirim!")
    }

    fun showMessage(msg: String) {
        emitMessage(msg)
    }

    private fun emitMessage(msg: String) {
        viewModelScope.launch {
            _userMessage.emit(msg)
        }
    }
}

fun formatRupiah(amount: Int): String {
    return String.format("%,d", amount).replace(',', '.')
}
