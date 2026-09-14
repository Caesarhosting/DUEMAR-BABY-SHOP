package com.example.data

import com.example.model.BabyProduct
import com.example.model.ProductCategory
import com.example.model.ProductReview

object ReviewCatalog {

    fun getDefaultReviewsForProduct(product: BabyProduct): List<ProductReview> {
        val variantDefault = product.colorways.firstOrNull()?.patternName ?: "Standar"
        val variantAlt = product.colorways.getOrNull(1)?.patternName ?: variantDefault

        return when (product.category) {
            ProductCategory.TIDUR -> listOf(
                ProductReview(
                    id = "rev_${product.id}_1",
                    authorName = "Bunda Sarah Amelia",
                    location = "Surabaya, Jawa Timur",
                    rating = 5,
                    dateText = "2 hari yang lalu",
                    variantName = variantDefault,
                    comment = "Alhamdulillah barang sampai cepat sekali! Bantalnya empuk banget dan dakronnya padat, gak gampang kempes. Bahannya katun dingin jadi dede bayi gak keringetan sama sekali. Gumoh juga langsung berkurang drastis sejak pakai ini. Recommended banget buat new mom!",
                    isVerifiedPurchase = true,
                    helpfulCount = 28,
                    tags = listOf("Bahan Adem", "Anti Gumoh", "Sangat Empuk")
                ),
                ProductReview(
                    id = "rev_${product.id}_2",
                    authorName = "Mama Rizky Pratama",
                    location = "Bandung, Jawa Barat",
                    rating = 5,
                    dateText = "5 hari yang lalu",
                    variantName = variantAlt,
                    comment = "Jahitannya rapi banget, motifnya lucu persis seperti di tampilan 3D aplikasinya. Aman banget ada tali sabuk pengamannya jadi gak was-was kalau ditinggal ambil air. Kualitasnya setara merk mall jutaan tapi harganya ramah kantong. Makasih Duemar!",
                    isVerifiedPurchase = true,
                    helpfulCount = 19,
                    tags = listOf("Jahitan Rapi", "Aman & Nyaman", "Real Pict 3D")
                ),
                ProductReview(
                    id = "rev_${product.id}_3",
                    authorName = "Bunda Anisa Fitriani",
                    location = "Jakarta Selatan",
                    rating = 5,
                    dateText = "1 minggu yang lalu",
                    variantName = variantDefault,
                    comment = "Pelayanan tokonya juara! Respon admin CS via WhatsApp sangat ramah dan informatif. Packing rapi dobel plastik dan kardus jadi tetap steril dan higienis sampai di rumah. Bayi langsung tidur nyenyak pules.",
                    isVerifiedPurchase = true,
                    helpfulCount = 14,
                    tags = listOf("Packing Higienis", "Respon CS Cepat", "Bayi Tidur Pulas")
                ),
                ProductReview(
                    id = "rev_${product.id}_4",
                    authorName = "Bunda Dewi Kartika",
                    location = "Semarang, Jawa Tengah",
                    rating = 5,
                    dateText = "2 minggu yang lalu",
                    variantName = variantAlt,
                    comment = "Beneran grade A dakronnya, dicuci di mesin cuci mode lembut juga gak menggumpal. Sarungnya halus di kulit sensitif bayi baru lahir. Bakalan langganan belanja perlengkapan bayi di Duemar.",
                    isVerifiedPurchase = true,
                    helpfulCount = 11,
                    tags = listOf("Bisa Dicuci", "Hypoallergenic", "Harga Terjangkau")
                )
            )

            ProductCategory.MANDI -> listOf(
                ProductReview(
                    id = "rev_${product.id}_1",
                    authorName = "Bunda Jessica W.",
                    location = "Tangerang Selatan",
                    rating = 5,
                    dateText = "3 hari yang lalu",
                    variantName = variantDefault,
                    comment = "Bak mandinya kokoh banget dan plastiknya tebal BPA free tanpa bau aneh. Lipatannya gampang disimpan di kamar mandi minimalis. Fitur indikator suhu airnya sangat membantu supaya air gak kepanasan buat bayi.",
                    isVerifiedPurchase = true,
                    helpfulCount = 32,
                    tags = listOf("BPA Free", "Praktis Lipat", "Sensor Suhu")
                ),
                ProductReview(
                    id = "rev_${product.id}_2",
                    authorName = "Mama Olivia",
                    location = "Medan, Sumatera Utara",
                    rating = 5,
                    dateText = "1 minggu yang lalu",
                    variantName = variantAlt,
                    comment = "Bantalan mandinya empuk dan cepat kering, bayi jadi gak licin atau merosot saat dimandikan. Mandiin bayi newborn jadi gak bikin parno lagi. Puas banget!",
                    isVerifiedPurchase = true,
                    helpfulCount = 18,
                    tags = listOf("Anti Slip", "Cepat Kering", "Aman Newborn")
                ),
                ProductReview(
                    id = "rev_${product.id}_3",
                    authorName = "Bunda Rini",
                    location = "Yogyakarta",
                    rating = 5,
                    dateText = "2 minggu yang lalu",
                    variantName = variantDefault,
                    comment = "Barang original sesuai deskripsi. Pengiriman kilat dan harga jauh lebih bersahabat dibanding toko offline. CS WhatsApp juga responsif.",
                    isVerifiedPurchase = true,
                    helpfulCount = 8,
                    tags = listOf("Original 100%", "Pengiriman Kilat")
                )
            )

            ProductCategory.AKSESORI -> listOf(
                ProductReview(
                    id = "rev_${product.id}_1",
                    authorName = "Bunda Cynthia",
                    location = "Jakarta Barat",
                    rating = 5,
                    dateText = "1 hari yang lalu",
                    variantName = variantDefault,
                    comment = "Kacamatanya super lentur dan fleksibel! Bahannya silikon lembut jadi gak sakit di hidung atau telinga bayi. Tali strapnya elastis dan bisa diatur pas di kepala dede pas diajak berjemur pagi.",
                    isVerifiedPurchase = true,
                    helpfulCount = 25,
                    tags = listOf("Silikon Lentur", "Lensa UV400", "Strap Lembut")
                ),
                ProductReview(
                    id = "rev_${product.id}_2",
                    authorName = "Papa Danang",
                    location = "Malang, Jawa Timur",
                    rating = 5,
                    dateText = "4 hari yang lalu",
                    variantName = variantAlt,
                    comment = "Beli buat anak usia 4 bulan buat jemur pagi hari. Beneran polarized dan anti silau, anak gak nangis atau kucek mata lagi. Modelnya kece banget pas dipakai foto!",
                    isVerifiedPurchase = true,
                    helpfulCount = 16,
                    tags = listOf("Anti Silau", "Foto Keren", "Nyaman Dijemur")
                )
            )

            ProductCategory.PAKAIAN -> listOf(
                ProductReview(
                    id = "rev_${product.id}_1",
                    authorName = "Bunda Nurul Hidayah",
                    location = "Bekasi, Jawa Barat",
                    rating = 5,
                    dateText = "3 hari yang lalu",
                    variantName = variantDefault,
                    comment = "Sudah bersertifikat SNI jadi tenang banget pakaikannya ke bayi newborn. Katunnya menyerap keringat dan kancing snap plastiknya kuat gak gampang lepas. Gak luntur saat dicuci.",
                    isVerifiedPurchase = true,
                    helpfulCount = 21,
                    tags = listOf("Sertifikasi SNI", "Katun Lembut", "Kancing Kuat")
                ),
                ProductReview(
                    id = "rev_${product.id}_2",
                    authorName = "Bunda Maya",
                    location = "Solo, Jawa Tengah",
                    rating = 5,
                    dateText = "6 hari yang lalu",
                    variantName = variantAlt,
                    comment = "Paket isi 3 jumper murah banget dengan kualitas katun sehalus ini. Warnanya pastel kalem manis. Pengiriman super rapi.",
                    isVerifiedPurchase = true,
                    helpfulCount = 12,
                    tags = listOf("Warna Pastel", "Hemat Berkualitas")
                )
            )

            else -> listOf(
                ProductReview(
                    id = "rev_${product.id}_1",
                    authorName = "Bunda Ratna Sari",
                    location = "Bogor, Jawa Barat",
                    rating = 5,
                    dateText = "3 hari yang lalu",
                    variantName = variantDefault,
                    comment = "Kualitas produk Duemar Baby Shop selalu konsisten memuaskan! Bahan ramah kulit sensitif bayi, kemasan rapi, dan sesuai dengan visual 3D yang ditampilkan. Sangat memuaskan!",
                    isVerifiedPurchase = true,
                    helpfulCount = 15,
                    tags = listOf("Kualitas Premium", "Bahan Ramah Bayi")
                ),
                ProductReview(
                    id = "rev_${product.id}_2",
                    authorName = "Bunda Fadhila",
                    location = "Depok, Jawa Barat",
                    rating = 5,
                    dateText = "1 minggu yang lalu",
                    variantName = variantAlt,
                    comment = "Senang belanja di sini, barang sampai dengan kondisi prima. Ada garansi dan CS WhatsApp selalu siap bantu. Pasti repeat order.",
                    isVerifiedPurchase = true,
                    helpfulCount = 9,
                    tags = listOf("Garansi Resmi", "Pelayanan Ramah")
                )
            )
        }
    }
}
