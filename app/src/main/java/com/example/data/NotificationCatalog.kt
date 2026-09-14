package com.example.data

import com.example.model.NotificationType
import com.example.model.ShopNotification

object NotificationCatalog {
    val initialNotifications: List<ShopNotification> = listOf(
        ShopNotification(
            id = "notif_fs_1",
            type = NotificationType.FLASH_SALE,
            title = "⚡ Flash Sale Dimulai: Diskon Hingga 30% Paket Bayi!",
            body = "Kesempatan terbatas! Dapatkan Sofa Bayi Jumbo Anti Gumoh & Bantal Menyusui dengan potongan harga spesial mulai dari Rp 45.000. Stok terbatas untuk 50 pembeli pertama!",
            timestampText = "10 Menit lalu",
            timestampMillis = System.currentTimeMillis() - (10 * 60 * 1000),
            isRead = false,
            badgeText = "⚡ FLASH SALE AKTIF",
            discountPercent = 30,
            targetProductId = "p2",
            actionRoute = "flash_sale",
            validUntil = "Berakhir dalam 4 jam 35 menit",
            highlights = listOf("Hemat s/d Rp 40.000", "Bahan Katun CVC Grade A", "Bisa COD & WA CS")
        ),
        ShopNotification(
            id = "notif_new_1",
            type = NotificationType.NEW_ARRIVAL,
            title = "✨ New Arrival: Set Jumper Bayi Katun Organic 3-in-1 Rilis!",
            body = "Koleksi terbaru pakaian bayi motif Safari & Animal Pastel kini hadir di Duemar! Menggunakan 100% katun combed SNI ultra-lembut yang anti-gerah untuk si kecil.",
            timestampText = "1 Jam lalu",
            timestampMillis = System.currentTimeMillis() - (60 * 60 * 1000),
            isRead = false,
            badgeText = "🌟 PRODUK BARU",
            discountPercent = 15,
            targetProductId = "p4",
            actionRoute = "detail/p4",
            validUntil = "Ready Stock",
            highlights = listOf("Size NB sampai XL", "Anti-alergi SNI", "Bonus Kaos Kaki")
        ),
        ShopNotification(
            id = "notif_fs_2",
            type = NotificationType.FLASH_SALE,
            title = "⏰ Pengingat: Flash Sale Kasur Bayi Kelambu Segera Berakhir!",
            body = "Paling dicari para Bunda! Kasur Bayi Kolam Kelambu Anti Nyamuk diskon kilat 25% hanya tersisa 7 unit lagi hari ini. Segera checkout sebelum harga normal kembali.",
            timestampText = "3 Jam lalu",
            timestampMillis = System.currentTimeMillis() - (3 * 60 * 60 * 1000),
            isRead = true,
            badgeText = "⏳ SEGERA BERAKHIR",
            discountPercent = 25,
            targetProductId = "p3",
            actionRoute = "detail/p3",
            validUntil = "Sisa 7 unit",
            highlights = listOf("Diskon 25%", "Termasuk 2 Guling + 1 Bantal Crown", "Kelambu Resleting Kokoh")
        ),
        ShopNotification(
            id = "notif_new_2",
            type = NotificationType.NEW_ARRIVAL,
            title = "🎉 New Arrival: Selimut Topi Bayi Double Fleece Premium",
            body = "Menyambut musim hujan dan ruangan ber-AC, selimut topi motif Polkadot Pastel & Awan Biru siap menjaga kehangatan tidur buah hati Anda.",
            timestampText = "Kemarin",
            timestampMillis = System.currentTimeMillis() - (24 * 60 * 60 * 1000),
            isRead = true,
            badgeText = "🌟 PRODUK BARU",
            discountPercent = 20,
            targetProductId = "p5",
            actionRoute = "detail/p5",
            validUntil = "Ready Stock",
            highlights = listOf("Bulu fleece super lembut", "Tidak mudah rontok", "Ukuran 80 x 85 cm")
        ),
        ShopNotification(
            id = "notif_promo_1",
            type = NotificationType.PROMO_VOUCHER,
            title = "🎁 Voucher Spesial: Diskon Rp 15.000 Khusus Pelanggan Setia",
            body = "Gunakan kode kupon *HEMATNEWBORN* di keranjang belanja untuk menikmati potongan langsung Rp 15.000 tanpa minimum belanja ribet!",
            timestampText = "2 Hari lalu",
            timestampMillis = System.currentTimeMillis() - (48 * 60 * 60 * 1000),
            isRead = true,
            badgeText = "🎁 KUPON DISKON",
            discountPercent = null,
            targetProductId = null,
            actionRoute = "cart",
            validUntil = "Berlaku sepanjang bulan ini",
            highlights = listOf("Kode: HEMATNEWBORN", "Semua Produk Bayi", "Potongan Langsung")
        )
    )

    fun createFlashSaleAlert(productName: String, discount: Int, productId: String): ShopNotification {
        val now = System.currentTimeMillis()
        return ShopNotification(
            id = "alert_fs_$now",
            type = NotificationType.FLASH_SALE,
            title = "⚡ Flash Sale Darurat: $productName Diskon $discount%!",
            body = "Flash sale dadakan untuk produk $productName telah dibuka! Amankan sekarang sebelum kehabisan stok promo kilat.",
            timestampText = "Baru saja",
            timestampMillis = now,
            isRead = false,
            badgeText = "⚡ FLASH SALE DADAKAN",
            discountPercent = discount,
            targetProductId = productId,
            actionRoute = "detail/$productId",
            validUntil = "Berlaku 2 jam ke depan",
            highlights = listOf("Diskon $discount%", "Stok Kilat Terbatas", "Konfirmasi Otomatis")
        )
    }

    fun createNewArrivalAlert(productName: String, productId: String, categoryName: String): ShopNotification {
        val now = System.currentTimeMillis()
        return ShopNotification(
            id = "alert_new_$now",
            type = NotificationType.NEW_ARRIVAL,
            title = "✨ New Arrival: $productName Baru Saja Mendarat!",
            body = "Koleksi baru kategori $categoryName kini tersedia di Duemar Baby Shop dengan motif teranyar dan kualitas teruji.",
            timestampText = "Baru saja",
            timestampMillis = now,
            isRead = false,
            badgeText = "🌟 KOLEKSI BARU",
            discountPercent = 10,
            targetProductId = productId,
            actionRoute = "detail/$productId",
            validUntil = "Stok Baru Masuk",
            highlights = listOf("Bahan Halus SNI", "Varian Warna Lengkap", "Garansi Toko")
        )
    }
}
