package com.example.data.local

import kotlinx.coroutines.flow.Flow

class BabyShopRepository(private val db: BabyShopDatabase) {
    private val cartDao = db.cartDao()
    private val favoriteDao = db.favoriteDao()
    private val orderDao = db.orderDao()

    val allCartItems: Flow<List<CartItemEntity>> = cartDao.getAllCartItems()
    val allFavorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()

    suspend fun addToCart(
        productId: String,
        productName: String,
        price: Int,
        quantity: Int,
        colorName: String,
        colorHex: String,
        note: String
    ) {
        val existing = cartDao.findExistingCartItem(productId, colorName)
        if (existing != null) {
            cartDao.updateCartItem(existing.copy(quantity = existing.quantity + quantity))
        } else {
            cartDao.insertCartItem(
                CartItemEntity(
                    productId = productId,
                    productName = productName,
                    price = price,
                    quantity = quantity,
                    selectedColorName = colorName,
                    selectedColorHex = colorHex,
                    note = note
                )
            )
        }
    }

    suspend fun updateCartItemQuantity(id: Long, newQuantity: Int) {
        if (newQuantity <= 0) {
            cartDao.deleteCartItemById(id)
        } else {
            // Retrieve and update
            // Since we only have id, we can update in DAO or read
        }
    }

    suspend fun updateCartItem(item: CartItemEntity) {
        cartDao.updateCartItem(item)
    }

    suspend fun removeCartItem(item: CartItemEntity) {
        cartDao.deleteCartItem(item)
    }

    suspend fun removeCartItemById(id: Long) {
        cartDao.deleteCartItemById(id)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun toggleFavorite(productId: String, isCurrentlyFav: Boolean) {
        if (isCurrentlyFav) {
            favoriteDao.removeFavorite(productId)
        } else {
            favoriteDao.addFavorite(FavoriteEntity(productId = productId))
        }
    }

    fun isFavorite(productId: String): Flow<Boolean> {
        return favoriteDao.isFavoriteFlow(productId)
    }

    suspend fun createOrder(
        customerName: String,
        customerPhone: String,
        customerAddress: String,
        itemsSummary: String,
        subtotal: Int,
        discount: Int,
        shippingFee: Int,
        totalAmount: Int,
        paymentMethod: String
    ): Long {
        val orderNo = "DMR-" + System.currentTimeMillis().toString().takeLast(6)
        val entity = OrderEntity(
            orderNumber = orderNo,
            customerName = customerName,
            customerPhone = customerPhone,
            customerAddress = customerAddress,
            itemsSummary = itemsSummary,
            subtotal = subtotal,
            discount = discount,
            shippingFee = shippingFee,
            totalAmount = totalAmount,
            paymentMethod = paymentMethod
        )
        val newId = orderDao.insertOrder(entity)
        cartDao.clearCart()
        return newId
    }
}
