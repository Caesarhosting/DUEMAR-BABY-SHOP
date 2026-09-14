package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object DuemarConfig {
    /**
     * Official WhatsApp number for Admin / CS of Duemar Baby Shop.
     */
    const val ADMIN_WHATSAPP_NUMBER = "6281539268446"
    const val ADMIN_WHATSAPP_DISPLAY = "0815-3926-8446"
    const val ADMIN_WHATSAPP_INTERNATIONAL = "+62 815-3926-8446"
    const val SHOP_NAME = "Duemar Baby Shop"

    /**
     * Opens WhatsApp to send an automatic message or transaction invoice directly to Admin/CS.
     */
    fun openWhatsApp(context: Context, message: String): Boolean {
        return try {
            val encodedMessage = Uri.encode(message)
            // Primary standard WhatsApp URI
            val whatsappUri = Uri.parse("https://api.whatsapp.com/send?phone=$ADMIN_WHATSAPP_NUMBER&text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, whatsappUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            try {
                // Secondary fallback using wa.me format
                val fallbackUri = Uri.parse("https://wa.me/$ADMIN_WHATSAPP_NUMBER?text=${Uri.encode(message)}")
                val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(fallbackIntent)
                true
            } catch (e2: Exception) {
                false
            }
        }
    }
}
