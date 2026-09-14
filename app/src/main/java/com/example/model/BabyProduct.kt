package com.example.model

import androidx.compose.ui.graphics.Color

enum class ProductCategory(val title: String, val shortLabel: String = title) {
    SEMUA("Semua", "Semua"),
    MANDI("Perlengkapan Mandi", "Mandi"),
    PAKAIAN("Pakaian Bayi", "Pakaian"),
    TIDUR("Perlengkapan Tidur", "Tidur"),
    AKSESORI("Aksesori Bayi", "Aksesori"),
    PAKET_HEMAT("Paket Hemat", "Paket Hemat")
}

enum class Model3DType {
    SOFA_BIASA,
    SOFA_DOUBLE,
    BANTAL_SET,
    BEDCOVER_SET,
    BEDCOVER_SINGLE,
    BANTAL_MENYUSUI,
    KACAMATA_BAYI,
    BAK_MANDI,
    PAKAIAN_BAYI,
    GENERIC_CUSHION
}

data class ProductColorway(
    val name: String,
    val color: Color,
    val patternName: String,
    val hexCode: String
)

data class ProductSpecification(
    val label: String,
    val value: String
)

data class BabyProduct(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val price: Int,
    val originalPrice: Int,
    val rating: Float,
    val reviewCount: Int,
    val soldCount: Int,
    val shortDescription: String,
    val fullDescription: String,
    val modelType: Model3DType,
    val isBestSeller: Boolean = false,
    val isMurahEfisien: Boolean = true,
    val colorways: List<ProductColorway>,
    val specifications: List<ProductSpecification>,
    val features: List<String>
) {
    val discountPercent: Int
        get() = if (originalPrice > price) {
            (((originalPrice - price).toDouble() / originalPrice) * 100).toInt()
        } else 0
}
