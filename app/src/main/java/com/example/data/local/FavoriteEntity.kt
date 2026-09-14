package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val customerName: String,
    val customerPhone: String,
    val customerAddress: String,
    val itemsSummary: String,
    val subtotal: Int,
    val discount: Int,
    val shippingFee: Int,
    val totalAmount: Int,
    val paymentMethod: String,
    val status: String = "Diproses", // Diproses, Dikemas, Dikirim
    val dateMillis: Long = System.currentTimeMillis()
)
