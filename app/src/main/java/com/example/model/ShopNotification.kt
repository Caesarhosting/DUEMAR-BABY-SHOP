package com.example.model

enum class NotificationType {
    FLASH_SALE,
    NEW_ARRIVAL,
    PROMO_VOUCHER
}

data class ShopNotification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val body: String,
    val timestampText: String,
    val timestampMillis: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val badgeText: String,
    val discountPercent: Int? = null,
    val targetProductId: String? = null,
    val actionRoute: String = "home",
    val validUntil: String? = null,
    val highlights: List<String> = emptyList()
)

data class NotificationPreferences(
    val flashSaleAlerts: Boolean = true,
    val newArrivalAlerts: Boolean = true,
    val promoVoucherAlerts: Boolean = true,
    val urgentSaleReminder: Boolean = true
)
