package com.example.model

data class ProductReview(
    val id: String,
    val authorName: String,
    val location: String,
    val rating: Int,
    val dateText: String,
    val variantName: String,
    val comment: String,
    val isVerifiedPurchase: Boolean = true,
    val helpfulCount: Int = 0,
    val tags: List<String> = emptyList()
)
