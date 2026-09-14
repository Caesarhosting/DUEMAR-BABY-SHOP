package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BabyProduct
import com.example.threed.Baby3DViewer
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FlashSaleSection(
    flashSaleProducts: List<BabyProduct>,
    onProductClick: (BabyProduct) -> Unit,
    onQuickAddToCart: (BabyProduct) -> Unit,
    modifier: Modifier = Modifier
) {
    if (flashSaleProducts.isEmpty()) return

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("id", "ID")).apply {
            maximumFractionDigits = 0
        }
    }

    // Dynamic ticking countdown timer (e.g. 4 hours, 35 minutes, 20 seconds remaining)
    var remainingSeconds by remember { mutableLongStateOf(4 * 3600L + 35 * 60L + 20L) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds--
        }
    }

    val hours = (remainingSeconds / 3600).coerceAtLeast(0)
    val minutes = ((remainingSeconds % 3600) / 60).coerceAtLeast(0)
    val seconds = (remainingSeconds % 60).coerceAtLeast(0)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("flash_sale_section"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
        border = BorderStroke(1.5.dp, Color(0xFFFECDD3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            // Header Bar: Flash Sale Title + Countdown Badges
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Flash Sale badge and title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFE11D48), Color(0xFFF97316))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = "Flash Sale",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "FLASH SALE",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = Color(0xFFBE123C)
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFE11D48)
                            ) {
                                Text(
                                    text = "S/D 40% OFF",
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Penawaran waktu terbatas hari ini",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF9F1239),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Right: Countdown Box (HH : MM : SS)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Waktu Tersisa",
                        tint = Color(0xFFBE123C),
                        modifier = Modifier.size(16.dp)
                    )
                    CountdownDigitBox(String.format("%02d", hours))
                    Text(":", fontWeight = FontWeight.Bold, color = Color(0xFFE11D48), fontSize = 12.sp)
                    CountdownDigitBox(String.format("%02d", minutes))
                    Text(":", fontWeight = FontWeight.Bold, color = Color(0xFFE11D48), fontSize = 12.sp)
                    CountdownDigitBox(String.format("%02d", seconds))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Flash Sale Cards Carousel
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(flashSaleProducts, key = { it.id }) { product ->
                    FlashSaleCard(
                        product = product,
                        currencyFormatter = currencyFormatter,
                        onProductClick = { onProductClick(product) },
                        onQuickAddToCart = { onQuickAddToCart(product) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CountdownDigitBox(value: String) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color(0xFFE11D48)
    ) {
        Text(
            text = value,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun FlashSaleCard(
    product: BabyProduct,
    currencyFormatter: NumberFormat,
    onProductClick: () -> Unit,
    onQuickAddToCart: () -> Unit
) {
    // Determine a simulated sold progress percentage based on sold count
    val soldRatio = remember(product.id) {
        val pct = ((product.soldCount % 40) + 60).toFloat() / 100f
        pct.coerceIn(0.55f, 0.95f)
    }
    val soldPercentInt = (soldRatio * 100).toInt()

    Card(
        modifier = Modifier
            .width(170.dp)
            .clickable { onProductClick() }
            .testTag("flash_card_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFFECDD3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // 3D Preview Box with Discount Pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8FAFC))
            ) {
                // Interactive 3D Turntable Mini Viewer
                val defaultColorway = product.colorways.firstOrNull() ?: com.example.model.ProductColorway("Default", Color(0xFF60A5FA), "Polos", "#60A5FA")
                Baby3DViewer(
                    product = product,
                    selectedColorway = defaultColorway,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    initialAutoRotate = true,
                    showControls = false
                )

                // Discount Pill Top Left
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 10.dp, topStart = 10.dp),
                    color = Color(0xFFE11D48),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "-${product.discountPercent}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // 3D Badge Top Right
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xDD0F172A),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Autorenew,
                            contentDescription = null,
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "3D",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(32.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pricing: Flash Price & Strikethrough Original
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = currencyFormatter.format(product.price),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFFE11D48),
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                )
            }

            if (product.originalPrice > product.price) {
                Text(
                    text = currencyFormatter.format(product.originalPrice),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF94A3B8),
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 10.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Flash Sale Stock Bar: "🔥 Terjual 85%"
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔥 Terjual $soldPercentInt%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE11D48)
                        )
                    )
                    Text(
                        text = "Cepat Habis",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp,
                            color = Color(0xFF64748B)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                LinearProgressIndicator(
                    progress = { soldRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFFE11D48),
                    trackColor = Color(0xFFFEE2E2),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action: "+ Keranjang" Button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onQuickAddToCart() },
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFFF1F2),
                border = BorderStroke(1.dp, Color(0xFFFECDD3))
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Beli",
                        tint = Color(0xFFE11D48),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+ Keranjang",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFE11D48),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }
        }
    }
}
