package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CartItemEntity::class, FavoriteEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BabyShopDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: BabyShopDatabase? = null

        fun getInstance(context: Context): BabyShopDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BabyShopDatabase::class.java,
                    "duemar_baby_shop.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
