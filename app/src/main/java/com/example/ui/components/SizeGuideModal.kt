package com.example.ui.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BabySizeCatalog
import com.example.model.BabySizeGuideItem
import com.example.util.DuemarConfig

@Composable
fun SizeGuideModal(
    currentSelectedSize: String,
    onSelectSize: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.88f)
                .testTag("size_guide_modal"),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // 1. Header with Title & Close Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8FAFC))
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E7FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Straighten,
                                contentDescription = "Panduan Ukuran",
                                tint = Color(0xFF4F46E5),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Panduan Ukuran Pakaian Bayi",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "Pilih ukuran 100% pas & bebas retur salah size",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF16A34A),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_size_guide_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                // 2. Navigation Tabs
                val tabTitles = listOf("Kalkulator Pas", "Tabel Ukuran", "Cara Ukur")
                val tabIcons = listOf(Icons.Default.Calculate, Icons.Default.TableChart, Icons.Default.Info)

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color(0xFFF1F5F9),
                    contentColor = MaterialTheme.colorScheme.primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = MaterialTheme.colorScheme.primary,
                            height = 3.dp
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 12.sp
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = tabIcons[index],
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            modifier = Modifier.testTag("size_guide_tab_$index")
                        )
                    }
                }

                // 3. Tab Contents with Vertical Scroll
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (selectedTabIndex) {
                        0 -> SizeCalculatorTab(
                            currentSelectedSize = currentSelectedSize,
                            onSelectSize = {
                                onSelectSize(it)
                                onDismiss()
                            }
                        )
                        1 -> SizeTableTab(
                            currentSelectedSize = currentSelectedSize,
                            onSelectSize = {
                                onSelectSize(it)
                                onDismiss()
                            }
                        )
                        2 -> HowToMeasureTab(context = context)
                    }
                }

                // 4. Bottom Footer Note
                Surface(
                    color = Color(0xFFF8FAFC),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Color(0xFF16A34A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Garansi tukar ukuran jika tidak pas*",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF15803D),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        TextButton(
                            onClick = {
                                val msg = "Halo Admin Duemar (${DuemarConfig.ADMIN_WHATSAPP_DISPLAY}), saya ingin tanya saran rekomendasi ukuran pakaian bayi yang paling pas untuk anak saya."
                                DuemarConfig.openWhatsApp(context, msg)
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tanya Admin WA", color = Color(0xFF16A34A), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SizeCalculatorTab(
    currentSelectedSize: String,
    onSelectSize: (String) -> Unit
) {
    var babyWeight by remember { mutableFloatStateOf(6.5f) }
    var babyAgeMonths by remember { mutableIntStateOf(4) }

    val recommendedItem = remember(babyWeight, babyAgeMonths) {
        BabySizeCatalog.recommendSize(babyWeight, babyAgeMonths)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFEEF2FF),
            border = BorderStroke(1.dp, Color(0xFFC7D2FE))
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color(0xFF4F46E5),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Geser berat badan & usia si kecil di bawah untuk mendapatkan rekomendasi ukuran paling pas secara instan:",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF3730A3),
                        lineHeight = 16.sp
                    )
                )
            }
        }

        // Stepper / Slider 1: Berat Badan Bayi (kg)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Berat Badan Bayi:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF4F46E5)
                    ) {
                        Text(
                            text = String.format(java.util.Locale.US, "%.1f kg", babyWeight),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledTonalIconButton(
                        onClick = { if (babyWeight > 2.5f) babyWeight = (babyWeight - 0.5f).coerceAtLeast(2.5f) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Kurang Berat")
                    }

                    Slider(
                        value = babyWeight,
                        onValueChange = { babyWeight = it },
                        valueRange = 2.5f..15.0f,
                        steps = 24,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF4F46E5),
                            activeTrackColor = Color(0xFF6366F1)
                        )
                    )

                    FilledTonalIconButton(
                        onClick = { if (babyWeight < 15.0f) babyWeight = (babyWeight + 0.5f).coerceAtMost(15.0f) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Berat")
                    }
                }
            }
        }

        // Stepper / Slider 2: Usia Bayi (Bulan)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Usia Bayi Saat Ini:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0284C7)
                    ) {
                        Text(
                            text = "$babyAgeMonths Bulan",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledTonalIconButton(
                        onClick = { if (babyAgeMonths > 0) babyAgeMonths-- },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Kurang Usia")
                    }

                    Slider(
                        value = babyAgeMonths.toFloat(),
                        onValueChange = { babyAgeMonths = it.toInt() },
                        valueRange = 0f..24f,
                        steps = 23,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF0284C7),
                            activeTrackColor = Color(0xFF38BDF8)
                        )
                    )

                    FilledTonalIconButton(
                        onClick = { if (babyAgeMonths < 24) babyAgeMonths++ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Usia")
                    }
                }
            }
        }

        // 3. Recommended Size Highlight Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
            border = BorderStroke(1.5.dp, Color(0xFF86EFAC)),
            modifier = Modifier.testTag("recommended_size_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Rekomendasi Terbaik Untuk Si Kecil",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF15803D)
                            )
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFDCFCE7)
                    ) {
                        Text(
                            text = "Akurasi 99%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534),
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = recommendedItem.label,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A),
                        fontSize = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = recommendedItem.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF166534),
                        lineHeight = 17.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Rentang Berat:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(recommendedItem.weightRange, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Panjang Badan:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(recommendedItem.heightRange, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Lingkar Dada:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(recommendedItem.chestCircumference, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onSelectSize(recommendedItem.label) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("apply_recommended_size_btn")
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Pilih Ukuran ${recommendedItem.label}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // Anti-Return Tips Note
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFFBEB),
            border = BorderStroke(1.dp, Color(0xFFFDE68A))
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFFD97706),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Tips Mencegah Salah Ukuran (Zero Return):",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Pertumbuhan bayi sangat pesat terutama di 6 bulan pertama. Jika berat badan si kecil mendekati batas maksimal suatu ukuran (misal 7.2 kg pada size 3-6 bulan), disarankan memilih 1 tingkat ukuran di atasnya (Size 6-12 Bulan) agar pakaian awet dipakai dan tetap leluasa saat memakai pampers tebal.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF78350F),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SizeTableTab(
    currentSelectedSize: String,
    onSelectSize: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Tabel Ukuran Lengkap Duemar Baby Shop",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Klik kartu ukuran yang diinginkan untuk memilih ukuran produk:",
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
        )

        BabySizeCatalog.standardSizes.forEach { item ->
            val isCurrent = currentSelectedSize.contains(item.code) || currentSelectedSize.contains(item.ageRange)

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) Color(0xFFF0FDF4) else Color(0xFFF8FAFC)
                ),
                border = BorderStroke(
                    width = if (isCurrent) 1.5.dp else 1.dp,
                    color = if (isCurrent) Color(0xFF16A34A) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectSize(item.label) }
                    .testTag("size_table_card_${item.code}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isCurrent) Color(0xFF16A34A) else Color(0xFF0F172A)
                            ) {
                                Text(
                                    text = item.code,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color(0xFF15803D) else Color(0xFF0F172A)
                                )
                            )
                        }

                        if (isCurrent) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFDCFCE7)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("Terpilih", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                                }
                            }
                        } else {
                            OutlinedButton(
                                onClick = { onSelectSize(item.label) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Pilih", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 0.8.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Berat Badan:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(item.weightRange, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                        }
                        Column {
                            Text("Tinggi / Panjang:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(item.heightRange, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                        }
                        Column {
                            Text("Lingkar Dada:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(item.chestCircumference, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                        }
                        Column {
                            Text("Pjg Baju:", fontSize = 10.sp, color = Color(0xFF64748B))
                            Text(item.bodyLength, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF64748B),
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun HowToMeasureTab(context: Context) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Cara Mengukur Tubuh Bayi Dengan Tepat di Rumah",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Gunakan pita meteran kain fleksibel saat si kecil dalam posisi rileks atau sedang tidur:",
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B), fontSize = 11.sp)
        )

        // Step 1
        MeasureGuideStep(
            number = "1",
            title = "Panjang Tubuh (Tinggi Badan)",
            guide = "Baringkan bayi di tempat tidur rata. Ukur mulai dari puncak ubun-ubun kepala lurus ke bawah menyusuri tulang belakang sampai ujung tumit kaki si kecil.",
            tip = "Tips: Luruskan kaki bayi perlahan tanpa dipaksakan."
        )

        // Step 2
        MeasureGuideStep(
            number = "2",
            title = "Lingkar Dada (Chest Width)",
            guide = "Lingkarkan pita meteran tepat di bawah ketiak mengelilingi bagian dada paling menonjol. Berikan kelonggaran 1-2 cm agar bayi dapat bernapas dan bergerak leluasa.",
            tip = "Tips: Ukur saat bayi membuang napas rileks."
        )

        // Step 3
        MeasureGuideStep(
            number = "3",
            title = "Ruang Popok & Pampers (Diaper Allowance)",
            guide = "Untuk setelan jumper/romper berkancing selangkangan, selalu pertimbangkan ketebalan popok basah. Jangan memilih ukuran yang terlalu ngepas agar kancing snap tidak mudah terbuka saat bayi aktif menendang.",
            tip = "Tips: Khusus jumper tidur, naikkan 1 tingkat ukuran untuk kenyamanan popok tebal."
        )

        Spacer(modifier = Modifier.height(4.dp))

        // WhatsApp Assistance Box
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFF0FDF4),
            border = BorderStroke(1.dp, Color(0xFFBBF7D0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Masih Ragu Menentukan Ukuran?",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF15803D)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Customer Service Duemar Baby Shop siap membantu merekomendasikan ukuran pakaian yang paling tepat untuk berat badan si kecil agar terhindar dari retur.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF166534),
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val msg = "Halo CS Duemar (${DuemarConfig.ADMIN_WHATSAPP_DISPLAY}), saya ingin konsultasi ukuran pakaian bayi agar pas dan tidak salah pesan."
                        DuemarConfig.openWhatsApp(context, msg)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Chat CS Konsultasi Ukuran via WA", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun MeasureGuideStep(
    number: String,
    title: String,
    guide: String,
    tip: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF4F46E5),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(number, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        fontSize = 13.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = guide,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF334155),
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tip,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF2563EB),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}
