package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.ProductCatalog
import com.example.model.Model3DType
import com.example.threed.Baby3DMeshGenerator
import com.example.util.DuemarConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Duemar Baby Shop", appName)
    }

    @Test
    fun `verify official WhatsApp number is 6281539268446 for CS and transactions`() {
        assertEquals("6281539268446", DuemarConfig.ADMIN_WHATSAPP_NUMBER)
        assertEquals("0815-3926-8446", DuemarConfig.ADMIN_WHATSAPP_DISPLAY)
        assertEquals("+62 815-3926-8446", DuemarConfig.ADMIN_WHATSAPP_INTERNATIONAL)
    }

    @Test
    fun `verify openWhatsApp helper initiates valid intent to 6281539268446`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val success = DuemarConfig.openWhatsApp(context, "Test transaction to Admin CS")
        assertTrue("WhatsApp intent launcher should succeed in test environment", success)
    }

    @Test
    fun `verify product reviews and testimonials provide high trust signals`() {
        val products = ProductCatalog.products
        products.forEach { prod ->
            val reviews = com.example.data.ReviewCatalog.getDefaultReviewsForProduct(prod)
            assertTrue("Product ${prod.name} should have reviews", reviews.isNotEmpty())
            reviews.forEach { rev ->
                assertTrue("Review author should not be blank", rev.authorName.isNotBlank())
                assertTrue("Review rating should be between 1 and 5", rev.rating in 1..5)
                assertTrue("Review comment should provide trust details", rev.comment.length > 20)
                assertTrue("Review should indicate verified purchase", rev.isVerifiedPurchase)
            }
        }
    }

    @Test
    fun `verify catalog contains requested products and price bounds`() {
        val products = ProductCatalog.products
        assertTrue("Catalog should not be empty", products.isNotEmpty())

        // Check required products exist
        val sofaBiasa = products.find { it.id == "prod_sofa_biasa" }
        assertNotNull("Bantal Sofa Bayi Biasa must exist", sofaBiasa)
        assertEquals("Bantal Sofa Bayi Biasa (Baby Lounger)", sofaBiasa?.name)

        val sofaDouble = products.find { it.id == "prod_sofa_double" }
        assertNotNull("Bantal Sofa Bayi Double must exist", sofaDouble)

        val bantalSet = products.find { it.id == "prod_bantal_set" }
        assertNotNull("Bantal Set must exist", bantalSet)

        val bedcoverSet = products.find { it.id == "prod_bedcover_set" }
        assertNotNull("Bedcover Set must exist", bedcoverSet)

        val bedcoverSingle = products.find { it.id == "prod_bedcover_single" }
        assertNotNull("Bedcover Single must exist", bedcoverSingle)

        val bantalMenyusui = products.find { it.id == "prod_bantal_menyusui" }
        assertNotNull("Bantal Menyusui must exist", bantalMenyusui)

        val kacamataBayi = products.find { it.id == "prod_kacamata_bayi" }
        assertNotNull("Kacamata Bayi must exist", kacamataBayi)

        // Verify prices are between 25.000 and 300.000 IDR
        val minPrice = products.minOf { it.price }
        val maxPrice = products.maxOf { it.price }
        assertEquals(25000, minPrice)
        assertTrue("Max price should be <= 300.000 IDR", maxPrice <= 300000)
    }

    @Test
    fun `verify 3D mesh generation for baby products`() {
        val testColor = Color(0xFF60A5FA)

        val sofaMesh = Baby3DMeshGenerator.generateMesh(Model3DType.SOFA_BIASA, testColor)
        assertTrue("Sofa mesh must contain vertices", sofaMesh.vertices.isNotEmpty())
        assertTrue("Sofa mesh must contain faces", sofaMesh.faces.isNotEmpty())

        val doubleSofaMesh = Baby3DMeshGenerator.generateMesh(Model3DType.SOFA_DOUBLE, testColor)
        assertTrue("Double sofa mesh must contain vertices", doubleSofaMesh.vertices.isNotEmpty())

        val glassesMesh = Baby3DMeshGenerator.generateMesh(Model3DType.KACAMATA_BAYI, testColor)
        assertTrue("Glasses mesh must contain vertices", glassesMesh.vertices.isNotEmpty())

        val nursingMesh = Baby3DMeshGenerator.generateMesh(Model3DType.BANTAL_MENYUSUI, testColor)
        assertTrue("Nursing pillow mesh must contain vertices", nursingMesh.vertices.isNotEmpty())
    }

    @Test
    fun `verify category filters for Mandi, Pakaian, and Tidur`() {
        val products = ProductCatalog.products

        val mandiProducts = products.filter { it.category == com.example.model.ProductCategory.MANDI }
        assertTrue("Mandi products should not be empty", mandiProducts.isNotEmpty())
        assertTrue("Mandi products should include bath items", mandiProducts.any { it.name.contains("Mandi", ignoreCase = true) })

        val pakaianProducts = products.filter { it.category == com.example.model.ProductCategory.PAKAIAN }
        assertTrue("Pakaian products should not be empty", pakaianProducts.isNotEmpty())
        assertTrue("Pakaian products should include apparel items", pakaianProducts.any { it.name.contains("Jumper", ignoreCase = true) || it.name.contains("Piyama", ignoreCase = true) })

        val tidurProducts = products.filter { it.category == com.example.model.ProductCategory.TIDUR }
        assertTrue("Tidur products should not be empty", tidurProducts.isNotEmpty())
        assertTrue("Tidur products should include bedding items", tidurProducts.any { it.name.contains("Bedcover", ignoreCase = true) || it.name.contains("Bantal", ignoreCase = true) })
    }

    @Test
    fun `verify search filtering on product catalog`() {
        val products = ProductCatalog.products

        val searchBantal = products.filter { it.name.contains("Bantal", ignoreCase = true) }
        assertTrue("Search for Bantal should return results", searchBantal.size >= 4)

        val searchKacamata = products.filter { it.name.contains("Kacamata", ignoreCase = true) }
        assertEquals(1, searchKacamata.size)
        assertEquals("prod_kacamata_bayi", searchKacamata.first().id)
    }

    @Test
    fun `verify flash sale items have deep discounts`() {
        val products = ProductCatalog.products
        val flashSaleItems = products.filter { it.discountPercent >= 20 }
        assertTrue("There should be multiple Flash Sale items with >=20% discount", flashSaleItems.size >= 5)

        // Verify Kacamata Bayi has the deepest flash sale discount
        val kacamata = flashSaleItems.find { it.id == "prod_kacamata_bayi" }
        assertNotNull(kacamata)
        assertTrue("Kacamata discount should be approx 37-38%", (kacamata?.discountPercent ?: 0) >= 35)

        // Verify all Flash Sale prices are strictly below original prices
        flashSaleItems.forEach { item ->
            assertTrue("Price must be less than originalPrice", item.price < item.originalPrice)
            assertTrue("Discount percent should be positive", item.discountPercent > 0)
        }
    }

    @Test
    fun `verify baby clothing size guide catalog and recommendation accuracy`() {
        val sizes = com.example.model.BabySizeCatalog.standardSizes
        assertEquals(5, sizes.size)

        // Verify standard sizes coverage
        val nb = sizes.find { it.code == "NB" }
        assertNotNull("NB size should exist", nb)
        assertTrue("NB covers newborns", nb!!.weightRange.contains("2.5"))

        val s = sizes.find { it.code == "S" }
        assertNotNull("S size should exist", s)

        val m = sizes.find { it.code == "M" }
        assertNotNull("M size should exist", m)

        val l = sizes.find { it.code == "L" }
        assertNotNull("L size should exist", l)

        val xl = sizes.find { it.code == "XL" }
        assertNotNull("XL size should exist", xl)

        // Test recommendation algorithm
        val rec1 = com.example.model.BabySizeCatalog.recommendSize(weightKg = 4.0f, ageMonths = 1)
        assertEquals("NB", rec1.code)

        val rec2 = com.example.model.BabySizeCatalog.recommendSize(weightKg = 6.5f, ageMonths = 4)
        assertEquals("S", rec2.code)

        val rec3 = com.example.model.BabySizeCatalog.recommendSize(weightKg = 8.5f, ageMonths = 8)
        assertEquals("M", rec3.code)

        val rec4 = com.example.model.BabySizeCatalog.recommendSize(weightKg = 11.0f, ageMonths = 14)
        assertEquals("L", rec4.code)

        val rec5 = com.example.model.BabySizeCatalog.recommendSize(weightKg = 13.5f, ageMonths = 20)
        assertEquals("XL", rec5.code)
    }

    @Test
    fun `verify clothing products in catalog have proper categorization`() {
        val clothingProducts = ProductCatalog.products.filter { it.category == com.example.model.ProductCategory.PAKAIAN }
        assertTrue("Catalog should have clothing products", clothingProducts.isNotEmpty())
        clothingProducts.forEach { cloth ->
            assertTrue("Clothing product should have positive price", cloth.price > 0)
            assertTrue("Clothing product should have colorways", cloth.colorways.isNotEmpty())
        }
    }

    @Test
    fun `verify notification system alerts for flash sale and new arrivals`() {
        val notifications = com.example.data.NotificationCatalog.initialNotifications
        assertTrue("Initial notifications should not be empty", notifications.isNotEmpty())

        // Check Flash Sale notification
        val flashSaleNotifs = notifications.filter { it.type == com.example.model.NotificationType.FLASH_SALE }
        assertTrue("Flash sale notifications should exist", flashSaleNotifs.isNotEmpty())
        flashSaleNotifs.forEach { fs ->
            assertTrue("Flash sale title should mention flash sale or discount", fs.title.contains("Flash Sale", ignoreCase = true) || fs.title.contains("Pengingat", ignoreCase = true))
            assertNotNull("Flash sale should have discount percentage", fs.discountPercent)
            assertTrue("Flash sale discount should be positive", fs.discountPercent!! > 0)
            assertNotNull("Flash sale should have validUntil text", fs.validUntil)
        }

        // Check New Arrival notification
        val newArrivalNotifs = notifications.filter { it.type == com.example.model.NotificationType.NEW_ARRIVAL }
        assertTrue("New arrival notifications should exist", newArrivalNotifs.isNotEmpty())
        newArrivalNotifs.forEach { na ->
            assertTrue("New arrival title should mention New Arrival or Baru", na.title.contains("New Arrival", ignoreCase = true) || na.title.contains("Baru", ignoreCase = true))
            assertNotNull("New arrival should have a target product ID", na.targetProductId)
        }

        // Check dynamic alert creators
        val customFsAlert = com.example.data.NotificationCatalog.createFlashSaleAlert(
            productName = "Kasur Bayi Kolam",
            discount = 30,
            productId = "p3"
        )
        assertEquals(com.example.model.NotificationType.FLASH_SALE, customFsAlert.type)
        assertEquals(30, customFsAlert.discountPercent)
        assertEquals("p3", customFsAlert.targetProductId)
        assertEquals("detail/p3", customFsAlert.actionRoute)

        val customNewAlert = com.example.data.NotificationCatalog.createNewArrivalAlert(
            productName = "Piyama Bayi Katun Bamboo",
            productId = "p8",
            categoryName = "Pakaian"
        )
        assertEquals(com.example.model.NotificationType.NEW_ARRIVAL, customNewAlert.type)
        assertEquals("p8", customNewAlert.targetProductId)
        assertTrue(customNewAlert.title.contains("Piyama Bayi Katun Bamboo"))

        // Check default preferences
        val prefs = com.example.model.NotificationPreferences()
        assertTrue("Flash sale alerts enabled by default", prefs.flashSaleAlerts)
        assertTrue("New arrival alerts enabled by default", prefs.newArrivalAlerts)
    }
}
