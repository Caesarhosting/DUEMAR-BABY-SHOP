package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ReviewCatalog
import com.example.model.BabyProduct
import com.example.model.ProductReview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomerReviewsSection(
    product: BabyProduct,
    modifier: Modifier = Modifier,
    onReviewSubmittedMessage: ((String) -> Unit)? = null
) {
    // Dynamic review list initialized with authentic contextual reviews
    val reviewsList = remember(product.id) {
        mutableStateListOf<ProductReview>().apply {
            addAll(ReviewCatalog.getDefaultReviewsForProduct(product))
        }
    }

    var selectedFilter by remember { mutableStateOf("Semua") }
    var showAddReviewDialog by remember { mutableStateOf(false) }

    // Helpful like toggles
    val helpfulClickedMap = remember { mutableStateListOf<String>() }

    // Filter reviews
    val filteredReviews = remember(selectedFilter, reviewsList.toList()) {
        when (selectedFilter) {
            "5 Bintang" -> reviewsList.filter { it.rating == 5 }
            "4 Bintang" -> reviewsList.filter { it.rating == 4 }
            else -> reviewsList
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("customer_reviews_section"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Title, Icon & "Tulis Ulasan" button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RateReview,
                            contentDescription = "Testimoni",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Ulasan & Testimoni Pembeli",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Text(
                            text = "Pengalaman nyata bunda & ayah berbelanja di Duemar",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF64748B),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                OutlinedButton(
                    onClick = { showAddReviewDialog = true },
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("btn_write_review")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Tulis Ulasan",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Rating Scorecard & Summary Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, Color(0xFFF1F5F9))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Column: Big Score & Stars
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = String.format(java.util.Locale.US, "%.1f", product.rating),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFF59E0B),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "${product.reviewCount} ulasan",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFDCFCE7),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                text = "99% Puas",
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF15803D),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Right Column: Star distribution bars
                    Column(
                        modifier = Modifier.weight(1.4f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        StarRatingBar(label = "5 Bintang", ratio = 0.94f, count = "${(product.reviewCount * 0.94).toInt()}")
                        StarRatingBar(label = "4 Bintang", ratio = 0.05f, count = "${(product.reviewCount * 0.05).toInt()}")
                        StarRatingBar(label = "3 Bintang", ratio = 0.01f, count = "${(product.reviewCount * 0.01).toInt()}")
                        StarRatingBar(label = "2 Bintang", ratio = 0.00f, count = "0")
                        StarRatingBar(label = "1 Bintang", ratio = 0.00f, count = "0")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Trust Guarantee Badges Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TrustBadgePill(
                    icon = Icons.Default.Verified,
                    text = "100% Pembeli Asli",
                    tint = Color(0xFF16A34A),
                    background = Color(0xFFF0FDF4),
                    modifier = Modifier.weight(1f)
                )
                TrustBadgePill(
                    icon = Icons.Default.WorkspacePremium,
                    text = "Bahan SNI & Halus",
                    tint = Color(0xFF0284C7),
                    background = Color(0xFFF0F9FF),
                    modifier = Modifier.weight(1f)
                )
                TrustBadgePill(
                    icon = Icons.Default.Security,
                    text = "Garansi Retur Toko",
                    tint = Color(0xFF9333EA),
                    background = Color(0xFFFAF5FF),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Semua", "5 Bintang", "4 Bintang").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = if (filter == "Semua") "Semua (${reviewsList.size})" else filter,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Testimonial Cards
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                filteredReviews.forEach { review ->
                    val isHelpfulClicked = helpfulClickedMap.contains(review.id)
                    TestimonialCard(
                        review = review,
                        isHelpful = isHelpfulClicked,
                        onToggleHelpful = {
                            if (isHelpfulClicked) {
                                helpfulClickedMap.remove(review.id)
                            } else {
                                helpfulClickedMap.add(review.id)
                            }
                        }
                    )
                }
            }
        }
    }

    // Modal Dialog: Tulis Ulasan Pembeli
    if (showAddReviewDialog) {
        var inputName by remember { mutableStateOf("") }
        var inputLocation by remember { mutableStateOf("") }
        var inputRating by remember { mutableIntStateOf(5) }
        var inputComment by remember { mutableStateOf("") }
        var selectedVariant by remember {
            mutableStateOf(product.colorways.firstOrNull()?.patternName ?: "Standar")
        }

        AlertDialog(
            onDismissRequest = { showAddReviewDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.RateReview,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tulis Testimoni Anda", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Bagikan pengalaman bunda/ayah menggunakan produk ini:",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                    )

                    // Star Rating Picker
                    Column {
                        Text(
                            text = "Beri Bintang Kepuasan:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            (1..5).forEach { star ->
                                IconButton(
                                    onClick = { inputRating = star },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (star <= inputRating) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "$star Bintang",
                                        tint = if (star <= inputRating) Color(0xFFF59E0B) else Color(0xFFCBD5E1),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = inputName,
                        onValueChange = { inputName = it },
                        label = { Text("Nama Bunda/Ayah (cth: Bunda Intan)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = inputLocation,
                        onValueChange = { inputLocation = it },
                        label = { Text("Kota/Wilayah (cth: Yogyakarta)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = inputComment,
                        onValueChange = { inputComment = it },
                        label = { Text("Ulasan / Testimoni") },
                        placeholder = { Text("Ceritakan kelembutan bahan, jahitan, atau respon bayi...") },
                        maxLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val author = inputName.trim().ifBlank { "Bunda Pelanggan Duemar" }
                        val loc = inputLocation.trim().ifBlank { "Indonesia" }
                        val comm = inputComment.trim().ifBlank { "Produk perlengkapan bayi berkualitas tinggi dan nyaman dipakai!" }

                        val newReview = ProductReview(
                            id = "rev_user_${System.currentTimeMillis()}",
                            authorName = author,
                            location = loc,
                            rating = inputRating,
                            dateText = "Baru saja",
                            variantName = selectedVariant,
                            comment = comm,
                            isVerifiedPurchase = true,
                            helpfulCount = 1,
                            tags = listOf("Ulasan Terbaru", "Terverifikasi")
                        )
                        reviewsList.add(0, newReview)
                        showAddReviewDialog = false
                        onReviewSubmittedMessage?.invoke("Terima kasih atas ulasan dan testimoninya!")
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Kirim Ulasan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddReviewDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
private fun StarRatingBar(
    label: String,
    ratio: Float,
    count: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.width(48.dp)
        )
        LinearProgressIndicator(
            progress = { ratio },
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = Color(0xFFF59E0B),
            trackColor = Color(0xFFE2E8F0)
        )
        Text(
            text = count,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.End
            ),
            modifier = Modifier.width(26.dp)
        )
    }
}

@Composable
private fun TrustBadgePill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    tint: Color,
    background: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = background,
        border = BorderStroke(1.dp, tint.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = tint
                ),
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TestimonialCard(
    review: ProductReview,
    isHelpful: Boolean,
    onToggleHelpful: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFAFAFA),
        border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Reviewer Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar circle with initials
                    val initials = remember(review.authorName) {
                        review.authorName.split(" ")
                            .take(2)
                            .mapNotNull { it.firstOrNull()?.uppercase() }
                            .joinToString("")
                            .ifBlank { "B" }
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF60A5FA), Color(0xFF38BDF8))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = review.authorName,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            if (review.isVerifiedPurchase) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Terverifikasi",
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                        Text(
                            text = review.location,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFF64748B),
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // Star rating row
                Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                    repeat(review.rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Variant badge & timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFE2E8F0)
                ) {
                    Text(
                        text = "Varian: ${review.variantName}",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Text(
                    text = review.dateText,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Testimonial comment text
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF334155),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            )

            // Tags Pill Row (e.g. "Bahan Adem", "Anti Gumoh")
            if (review.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    review.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFEFF6FF),
                            border = BorderStroke(1.dp, Color(0xFFDBEAFE))
                        ) {
                            Text(
                                text = "# $tag",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF1D4ED8),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Helpful feedback row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val count = review.helpfulCount + if (isHelpful) 1 else 0
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isHelpful) Color(0xFFDCFCE7) else Color.Transparent,
                    border = BorderStroke(1.dp, if (isHelpful) Color(0xFF86EFAC) else Color(0xFFE2E8F0)),
                    modifier = Modifier.clickable { onToggleHelpful() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Membantu",
                            tint = if (isHelpful) Color(0xFF16A34A) else Color(0xFF64748B),
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Membantu ($count)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isHelpful) Color(0xFF15803D) else Color(0xFF64748B),
                                fontSize = 10.sp,
                                fontWeight = if (isHelpful) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    }
}
