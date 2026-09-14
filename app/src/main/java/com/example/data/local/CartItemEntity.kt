package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: String,
    val productName: String,
    val price: Int,
    val quantity: Int,
    val selectedColorName: String,
    val selectedColorHex: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
