package com.example.threed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BabyProduct
import com.example.model.Model3DType
import com.example.model.ProductColorway
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min

enum class Render3DMode {
    SHADED_FABRIC,
    WIREFRAME_CAD,
    CLAY_STUDIO
}

@Composable
fun Baby3DViewer(
    product: BabyProduct,
    selectedColorway: ProductColorway,
    modifier: Modifier = Modifier,
    initialAutoRotate: Boolean = true,
    showControls: Boolean = true
) {
    var rotX by remember { mutableFloatStateOf(0.35f) } // slight downward angle
    var rotY by remember { mutableFloatStateOf(0.45f) }
    var scaleFactor by remember { mutableFloatStateOf(105f) }
    var isAutoRotating by remember { mutableStateOf(initialAutoRotate) }
    var renderMode by remember { mutableStateOf(Render3DMode.SHADED_FABRIC) }
    var showDimensions by remember { mutableStateOf(true) }

    // Automated 3D rotation engine
    LaunchedEffect(isAutoRotating) {
        if (isAutoRotating) {
            var lastTime = withFrameMillis { it }
            while (true) {
                val currentTime = withFrameMillis { it }
                val delta = (currentTime - lastTime) / 1000f
                lastTime = currentTime
                rotY = (rotY + delta * 0.75f) % (2f * PI.toFloat())
            }
        }
    }

    val mesh = remember(product.modelType, selectedColorway.color) {
        Baby3DMeshGenerator.generateMesh(product.modelType, selectedColorway.color)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF1F5F9),
                        Color(0xFFE2E8F0),
                        Color(0xFFF8FAFC)
                    )
                )
            )
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(20.dp))
    ) {
        // Top Badges & Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Autorenew,
                        contentDescription = "3D Otomatis",
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = if (isAutoRotating) "3D Otomatis: Aktif (360°)" else "3D Manual: Sentuh & Putar",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF0FDF4),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
            ) {
                Text(
                    text = "3D High-Res",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF15803D),
                        fontSize = 10.sp
                    )
                )
            }
        }

        // 3D Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isAutoRotating = false },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            rotY += dragAmount.x * 0.012f
                            rotX = (rotX - dragAmount.y * 0.012f).coerceIn(-1.2f, 1.2f)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scaleFactor = (scaleFactor * zoom).coerceIn(60f, 170f)
                    }
                }
                .testTag("baby_3d_canvas"),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Studio floor drop shadow
                drawOval(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x35000000), Color(0x10000000), Color.Transparent),
                        center = Offset(w / 2f, h * 0.82f),
                        radius = w * 0.38f * (scaleFactor / 100f)
                    ),
                    topLeft = Offset(w * 0.12f, h * 0.74f),
                    size = Size(w * 0.76f, h * 0.18f)
                )

                // Project & Rotate Vertices
                val projectedVertices = mesh.vertices.map { v ->
                    val rotated = v.rotateX(rotX).rotateY(rotY)
                    rotated.project(viewWidth = w, viewHeight = h, scale = scaleFactor)
                }

                // Light direction vector (fixed in camera space)
                val lightDir = Point3D(0.5f, 0.85f, -0.65f).normalized()

                // Calculate faces depth & normals for depth sorting (Painter's algorithm)
                data class RenderableFace(
                    val face: Face3D,
                    val avgZ: Float,
                    val projectedPts: List<ProjectedPoint>,
                    val normal: Point3D
                )

                val renderableFaces = mesh.faces.mapNotNull { face ->
                    if (face.indices.size < 3) return@mapNotNull null
                    val pts = face.indices.map { projectedVertices[it] }
                    val avgZ = pts.map { it.z }.average().toFloat()

                    // Calculate 3D normal for lighting
                    val p0 = mesh.vertices[face.indices[0]].rotateX(rotX).rotateY(rotY)
                    val p1 = mesh.vertices[face.indices[1]].rotateX(rotX).rotateY(rotY)
                    val p2 = mesh.vertices[face.indices[2]].rotateX(rotX).rotateY(rotY)
                    val v1 = p1 - p0
                    val v2 = p2 - p0
                    val normal = v1.cross(v2).normalized()

                    RenderableFace(face, avgZ, pts, normal)
                }.sortedByDescending { it.avgZ } // Back to front

                // Draw faces
                for (rFace in renderableFaces) {
                    val path = Path().apply {
                        val first = rFace.projectedPts.first()
                        moveTo(first.x, first.y)
                        for (i in 1 until rFace.projectedPts.size) {
                            lineTo(rFace.projectedPts[i].x, rFace.projectedPts[i].y)
                        }
                        close()
                    }

                    when (renderMode) {
                        Render3DMode.SHADED_FABRIC -> {
                            val diffuse = max(0.28f, rFace.normal.dot(lightDir))
                            val shadedColor = applyLighting(rFace.face.color, diffuse)
                            drawPath(path, color = shadedColor, style = Fill)
                            // Subtle stitching seam line
                            drawPath(path, color = Color(0x22000000), style = Stroke(width = 1f))
                        }
                        Render3DMode.WIREFRAME_CAD -> {
                            // Holographic CAD tech wireframe
                            drawPath(path, color = Color(0x200284C7), style = Fill)
                            drawPath(path, color = Color(0xFF0284C7), style = Stroke(width = 1.4f))
                        }
                        Render3DMode.CLAY_STUDIO -> {
                            val diffuse = max(0.35f, rFace.normal.dot(lightDir))
                            val clayColor = applyLighting(Color(0xFFF8FAFC), diffuse)
                            drawPath(path, color = clayColor, style = Fill)
                            drawPath(path, color = Color(0x3064748B), style = Stroke(width = 1f))
                        }
                    }
                }

                // 3D Dimension markers if enabled
                if (showDimensions && projectedVertices.isNotEmpty()) {
                    val topPt = projectedVertices.minByOrNull { it.y } ?: projectedVertices[0]
                    val botPt = projectedVertices.maxByOrNull { it.y } ?: projectedVertices[0]
                    val leftPt = projectedVertices.minByOrNull { it.x } ?: projectedVertices[0]
                    val rightPt = projectedVertices.maxByOrNull { it.x } ?: projectedVertices[0]

                    // Width dimension indicator
                    drawLine(
                        color = Color(0xFF0284C7),
                        start = Offset(leftPt.x, botPt.y + 16f),
                        end = Offset(rightPt.x, botPt.y + 16f),
                        strokeWidth = 1.8f
                    )
                    // End ticks
                    drawLine(
                        color = Color(0xFF0284C7),
                        start = Offset(leftPt.x, botPt.y + 10f),
                        end = Offset(leftPt.x, botPt.y + 22f),
                        strokeWidth = 1.8f
                    )
                    drawLine(
                        color = Color(0xFF0284C7),
                        start = Offset(rightPt.x, botPt.y + 10f),
                        end = Offset(rightPt.x, botPt.y + 22f),
                        strokeWidth = 1.8f
                    )
                }
            }

            // Interactive HUD Overlay Helper
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.55f)
                ) {
                    Text(
                        text = "Geser untuk memutar 360°",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontSize = 10.sp)
                    )
                }
            }
        }

        // 3D Controls Bar
        if (showControls) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                // Control Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play/Pause Auto Rotate
                    FilledTonalIconButton(
                        onClick = { isAutoRotating = !isAutoRotating },
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("toggle_auto_rotate")
                    ) {
                        Icon(
                            imageVector = if (isAutoRotating) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isAutoRotating) "Jeda 3D Otomatis" else "Mulai 3D Otomatis",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Reset angle
                    IconButton(
                        onClick = {
                            rotX = 0.35f
                            rotY = 0.45f
                            scaleFactor = 105f
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("reset_3d_view")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Sudut 3D",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Render mode toggle chips
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = renderMode == Render3DMode.SHADED_FABRIC,
                            onClick = { renderMode = Render3DMode.SHADED_FABRIC },
                            label = { Text("Kain/Warna", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(14.dp))
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        FilterChip(
                            selected = renderMode == Render3DMode.WIREFRAME_CAD,
                            onClick = { renderMode = Render3DMode.WIREFRAME_CAD },
                            label = { Text("Mesh CAD", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.GridView, contentDescription = null, modifier = Modifier.size(14.dp))
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Zoom scale slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Zoom",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = scaleFactor,
                        onValueChange = { scaleFactor = it },
                        valueRange = 60f..160f,
                        modifier = Modifier
                            .weight(1f)
                            .height(24.dp)
                            .testTag("zoom_slider"),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${scaleFactor.toInt()}%",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Lighting calculation helper
private fun applyLighting(color: Color, diffuse: Float): Color {
    val r = (color.red * diffuse).coerceIn(0f, 1f)
    val g = (color.green * diffuse).coerceIn(0f, 1f)
    val b = (color.blue * diffuse).coerceIn(0f, 1f)
    return Color(red = r, green = g, blue = b, alpha = color.alpha)
}
