package com.example.threed

import androidx.compose.ui.graphics.Color
import com.example.model.Model3DType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object Baby3DMeshGenerator {

    fun generateMesh(type: Model3DType, primaryColor: Color): Mesh3D {
        return when (type) {
            Model3DType.SOFA_BIASA -> createBabyLoungerSingle(primaryColor)
            Model3DType.SOFA_DOUBLE -> createBabyLoungerDouble(primaryColor)
            Model3DType.BANTAL_MENYUSUI -> createNursingPillow(primaryColor)
            Model3DType.BEDCOVER_SET -> createBedcoverSet(primaryColor)
            Model3DType.BEDCOVER_SINGLE -> createBedcoverSingle(primaryColor)
            Model3DType.KACAMATA_BAYI -> createBabySunglasses(primaryColor)
            Model3DType.BANTAL_SET -> createBantalSet(primaryColor)
            Model3DType.BAK_MANDI -> createBabyBathtub(primaryColor)
            Model3DType.PAKAIAN_BAYI -> createBabyJumper(primaryColor)
            Model3DType.GENERIC_CUSHION -> createBabyLoungerSingle(primaryColor)
        }
    }

    // 1. BANTAL SOFA BAYI BIASA: Oval curved lounger with recessed center & safety belt
    private fun createBabyLoungerSingle(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val numSlices = 16
        val outerRadiusX = 140f
        val outerRadiusY = 110f
        val ringThickness = 32f
        val heightRim = 36f

        // Outer upper ring
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * outerRadiusX
            val z = sin(angle) * outerRadiusY
            vertices.add(Point3D(x, heightRim, z))
        }

        // Outer lower rim
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * (outerRadiusX + 8f)
            val z = sin(angle) * (outerRadiusY + 8f)
            vertices.add(Point3D(x, -heightRim * 0.4f, z))
        }

        // Inner upper slope
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * (outerRadiusX - ringThickness)
            val z = sin(angle) * (outerRadiusY - ringThickness)
            vertices.add(Point3D(x, heightRim * 0.5f, z))
        }

        // Inner bottom center (depressed nest for baby back)
        val centerIdx = vertices.size
        vertices.add(Point3D(0f, -heightRim * 0.6f, 0f))

        val lighterColor = baseColor.copy(alpha = 0.95f)
        val accentBeltColor = Color(0xFFF59E0B) // Golden yellow safety strap
        val buckleColor = Color(0xFF334155)

        // Outer wall quads
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            val top1 = i
            val top2 = next
            val bot1 = numSlices + i
            val bot2 = numSlices + next
            faces.add(Face3D(listOf(top1, top2, bot2, bot1), baseColor))
        }

        // Ring crown quads
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            val out1 = i
            val out2 = next
            val in1 = numSlices * 2 + i
            val in2 = numSlices * 2 + next
            faces.add(Face3D(listOf(out1, out2, in2, in1), lighterColor))
        }

        // Inner nest triangles
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            val in1 = numSlices * 2 + i
            val in2 = numSlices * 2 + next
            faces.add(Face3D(listOf(in1, in2, centerIdx), lighterColor))
        }

        // Safety Belt across center (horizontal strap)
        val beltY = -heightRim * 0.1f
        val b0 = vertices.size
        vertices.add(Point3D(-80f, beltY, -14f))
        vertices.add(Point3D(80f, beltY, -14f))
        vertices.add(Point3D(80f, beltY, 14f))
        vertices.add(Point3D(-80f, beltY, 14f))
        faces.add(Face3D(listOf(b0, b0 + 1, b0 + 2, b0 + 3), accentBeltColor))

        // Center safety buckle
        val bu0 = vertices.size
        vertices.add(Point3D(-16f, beltY + 3f, -18f))
        vertices.add(Point3D(16f, beltY + 3f, -18f))
        vertices.add(Point3D(16f, beltY + 3f, 18f))
        vertices.add(Point3D(-16f, beltY + 3f, 18f))
        faces.add(Face3D(listOf(bu0, bu0 + 1, bu0 + 2, bu0 + 3), buckleColor))

        return Mesh3D(vertices, faces, "Bantal Sofa Bayi Biasa")
    }

    // 2. BANTAL SOFA BAYI DOUBLE: Dual tier, extra plush with elevated headrest & premium belt
    private fun createBabyLoungerDouble(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val numSlices = 16
        val outerRadiusX = 145f
        val outerRadiusY = 115f
        val ringThickness = 34f
        val heightRim = 48f

        // Tier 1 upper ring with elevated head angle
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * outerRadiusX
            val z = sin(angle) * outerRadiusY
            // Elevated head support at negative Z
            val headTilt = if (z < 0) -z * 0.22f else 0f
            vertices.add(Point3D(x, heightRim + headTilt, z))
        }

        // Mid roll crease (giving the 2-tier double cushion look)
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * (outerRadiusX - 10f)
            val z = sin(angle) * (outerRadiusY - 10f)
            vertices.add(Point3D(x, 10f, z))
        }

        // Lower tier outer rim
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * (outerRadiusX + 5f)
            val z = sin(angle) * (outerRadiusY + 5f)
            vertices.add(Point3D(x, -heightRim * 0.5f, z))
        }

        // Inner nest upper contour
        for (i in 0 until numSlices) {
            val angle = (2 * PI * i / numSlices).toFloat()
            val x = cos(angle) * (outerRadiusX - ringThickness)
            val z = sin(angle) * (outerRadiusY - ringThickness)
            vertices.add(Point3D(x, heightRim * 0.4f, z))
        }

        // Recessed plush center point
        val centerIdx = vertices.size
        vertices.add(Point3D(0f, -heightRim * 0.5f, 0f))

        val lighterColor = baseColor.copy(alpha = 0.92f)
        val contrastColor = Color(0xFFF472B6) // Pastel pink secondary contour
        val beltColor = Color(0xFF38BDF8) // Soft cyan strap

        // Upper outer roll quads
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            faces.add(Face3D(listOf(i, next, numSlices + next, numSlices + i), baseColor))
        }

        // Lower outer roll quads
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            faces.add(Face3D(listOf(numSlices + i, numSlices + next, numSlices * 2 + next, numSlices * 2 + i), contrastColor))
        }

        // Inner crown quads
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            faces.add(Face3D(listOf(i, next, numSlices * 3 + next, numSlices * 3 + i), lighterColor))
        }

        // Inner center bed triangles
        for (i in 0 until numSlices) {
            val next = (i + 1) % numSlices
            faces.add(Face3D(listOf(numSlices * 3 + i, numSlices * 3 + next, centerIdx), lighterColor))
        }

        // Premium Dual 3-Point Belt
        val beltBase = vertices.size
        vertices.add(Point3D(-85f, 5f, -15f))
        vertices.add(Point3D(85f, 5f, -15f))
        vertices.add(Point3D(85f, 5f, 15f))
        vertices.add(Point3D(-85f, 5f, 15f))
        faces.add(Face3D(listOf(beltBase, beltBase + 1, beltBase + 2, beltBase + 3), beltColor))

        // Vertical crotch security buckle
        val crotchBase = vertices.size
        vertices.add(Point3D(-12f, -15f, 15f))
        vertices.add(Point3D(12f, -15f, 15f))
        vertices.add(Point3D(12f, -15f, 75f))
        vertices.add(Point3D(-12f, -15f, 75f))
        faces.add(Face3D(listOf(crotchBase, crotchBase + 1, crotchBase + 2, crotchBase + 3), beltColor))

        return Mesh3D(vertices, faces, "Bantal Sofa Bayi Double")
    }

    // 3. BANTAL MENYUSUI: Ergonomic U-shape / horseshoe curve torus
    private fun createNursingPillow(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val arcSegments = 14
        val crossSegments = 8
        val majorRadius = 110f
        val tubeRadius = 38f

        // Generate U-shape curve from -140 degrees to +140 degrees
        val startAngle = -2.4f
        val endAngle = 2.4f
        val angleStep = (endAngle - startAngle) / arcSegments

        for (i in 0..arcSegments) {
            val theta = startAngle + i * angleStep
            val centerX = sin(theta) * majorRadius
            val centerZ = cos(theta) * majorRadius

            val normalX = sin(theta)
            val normalZ = cos(theta)
            val tangentX = cos(theta)
            val tangentZ = -sin(theta)

            for (j in 0 until crossSegments) {
                val phi = (2 * PI * j / crossSegments).toFloat()
                val r = tubeRadius * (1f - 0.15f * cos(phi)) // Plump baby shape
                val localY = sin(phi) * r
                val localR = cos(phi) * r

                val px = centerX + normalX * localR
                val py = localY
                val pz = centerZ + normalZ * localR
                vertices.add(Point3D(px, py, pz))
            }
        }

        val ringCount = arcSegments + 1
        for (i in 0 until arcSegments) {
            for (j in 0 until crossSegments) {
                val nextJ = (j + 1) % crossSegments
                val p1 = i * crossSegments + j
                val p2 = i * crossSegments + nextJ
                val p3 = (i + 1) * crossSegments + nextJ
                val p4 = (i + 1) * crossSegments + j

                val shade = if (j % 2 == 0) baseColor else baseColor.copy(alpha = 0.9f)
                faces.add(Face3D(listOf(p1, p2, p3, p4), shade))
            }
        }

        // End caps for U tips
        val startCap = mutableListOf<Int>()
        for (j in 0 until crossSegments) {
            startCap.add(j)
        }
        faces.add(Face3D(startCap, baseColor))

        val endCap = mutableListOf<Int>()
        val lastRing = arcSegments * crossSegments
        for (j in (crossSegments - 1) downTo 0) {
            endCap.add(lastRing + j)
        }
        faces.add(Face3D(endCap, baseColor))

        return Mesh3D(vertices, faces, "Bantal Menyusui Ergonomis")
    }

    // 4. BEDCOVER SET: Quilted mattress + baby pillow + 2 bolsters
    private fun createBedcoverSet(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        // Quilted Bedcover Base
        val width = 140f
        val length = 190f
        val thick = 22f

        val quiltCols = 4
        val quiltRows = 5
        val xStep = (width * 2) / quiltCols
        val zStep = (length * 2) / quiltRows

        val gridStart = vertices.size
        for (r in 0..quiltRows) {
            val z = -length + r * zStep
            for (c in 0..quiltCols) {
                val x = -width + c * xStep
                // Pillowed quilt puffed surface
                val puff = if (r > 0 && r < quiltRows && c > 0 && c < quiltCols) 6f else 0f
                vertices.add(Point3D(x, thick / 2f + puff, z))
            }
        }

        // Bed bottom plane
        val botStart = vertices.size
        vertices.add(Point3D(-width, -thick / 2f, -length))
        vertices.add(Point3D(width, -thick / 2f, -length))
        vertices.add(Point3D(width, -thick / 2f, length))
        vertices.add(Point3D(-width, -thick / 2f, length))
        faces.add(Face3D(listOf(botStart, botStart + 1, botStart + 2, botStart + 3), baseColor.copy(alpha = 0.8f)))

        // Quilted top face squares with stitched pattern
        val accentColor = Color(0xFFFDE047)
        for (r in 0 until quiltRows) {
            for (c in 0 until quiltCols) {
                val p1 = gridStart + r * (quiltCols + 1) + c
                val p2 = p1 + 1
                val p3 = gridStart + (r + 1) * (quiltCols + 1) + c + 1
                val p4 = p3 - 1
                val quadColor = if ((r + c) % 2 == 0) baseColor else baseColor.copy(alpha = 0.88f)
                faces.add(Face3D(listOf(p1, p2, p3, p4), quadColor))
            }
        }

        // Side borders of bedcover
        val borderIdx = vertices.size
        // Front border
        vertices.add(Point3D(-width, thick / 2f, length))
        vertices.add(Point3D(width, thick / 2f, length))
        vertices.add(Point3D(width, -thick / 2f, length))
        vertices.add(Point3D(-width, -thick / 2f, length))
        faces.add(Face3D(listOf(borderIdx, borderIdx + 1, borderIdx + 2, borderIdx + 3), baseColor))

        // Baby Pillow at head of bed (Z < -90)
        val pillowStart = vertices.size
        val pw = 65f
        val pl = 45f
        val py = thick / 2f + 18f
        vertices.add(Point3D(-pw, py, -length + 15f))
        vertices.add(Point3D(pw, py, -length + 15f))
        vertices.add(Point3D(pw, py, -length + 15f + pl * 2))
        vertices.add(Point3D(-pw, py, -length + 15f + pl * 2))
        faces.add(Face3D(listOf(pillowStart, pillowStart + 1, pillowStart + 2, pillowStart + 3), Color(0xFFFEF3C7)))

        // Pillow ruffles
        val ruffleStart = vertices.size
        vertices.add(Point3D(-pw - 12f, py - 4f, -length + 8f))
        vertices.add(Point3D(pw + 12f, py - 4f, -length + 8f))
        vertices.add(Point3D(pw + 12f, py - 4f, -length + 22f + pl * 2))
        vertices.add(Point3D(-pw - 12f, py - 4f, -length + 22f + pl * 2))
        faces.add(Face3D(listOf(ruffleStart, ruffleStart + 1, ruffleStart + 2, ruffleStart + 3), Color(0xFFFDE68A)))

        // Bolster 1 (Left side cylinder)
        createBolster(vertices, faces, -width + 38f, thick / 2f + 14f, 15f, 65f, Color(0xFFBAE6FD))
        // Bolster 2 (Right side cylinder)
        createBolster(vertices, faces, width - 38f, thick / 2f + 14f, 15f, 65f, Color(0xFFBAE6FD))

        return Mesh3D(vertices, faces, "Bedcover Set Lengkap")
    }

    // Helper for 3D Bolster (Guling Bayi)
    private fun createBolster(
        vertices: MutableList<Point3D>,
        faces: MutableList<Face3D>,
        cx: Float, cy: Float, cz: Float,
        length: Float,
        color: Color
    ) {
        val radius = 16f
        val segments = 8
        val startIdx = vertices.size

        // Ring 1 (back)
        for (i in 0 until segments) {
            val angle = (2 * PI * i / segments).toFloat()
            val x = cx + cos(angle) * radius
            val y = cy + sin(angle) * radius
            vertices.add(Point3D(x, y, cz - length))
        }

        // Ring 2 (front)
        for (i in 0 until segments) {
            val angle = (2 * PI * i / segments).toFloat()
            val x = cx + cos(angle) * radius
            val y = cy + sin(angle) * radius
            vertices.add(Point3D(x, y, cz + length))
        }

        // Cylinder quads
        for (i in 0 until segments) {
            val next = (i + 1) % segments
            val p1 = startIdx + i
            val p2 = startIdx + next
            val p3 = startIdx + segments + next
            val p4 = startIdx + segments + i
            faces.add(Face3D(listOf(p1, p2, p3, p4), color))
        }

        // End ribbon knots
        val knot1 = vertices.size
        vertices.add(Point3D(cx, cy, cz - length - 8f))
        val knot2 = vertices.size
        vertices.add(Point3D(cx, cy, cz + length + 8f))
        for (i in 0 until segments) {
            val next = (i + 1) % segments
            faces.add(Face3D(listOf(startIdx + i, startIdx + next, knot1), Color(0xFFF472B6)))
            faces.add(Face3D(listOf(startIdx + segments + next, startIdx + segments + i, knot2), Color(0xFFF472B6)))
        }
    }

    // 5. BEDCOVER SINGLE: Folded thick blanket with soft layered borders
    private fun createBedcoverSingle(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val w = 120f
        val l = 100f
        val h = 42f // Folded thickness

        // 3-tier fold representation
        for (layer in 0..2) {
            val yBot = -h / 2f + layer * (h / 3f)
            val yTop = yBot + (h / 3.4f)
            val layerStart = vertices.size

            vertices.add(Point3D(-w, yTop, -l))
            vertices.add(Point3D(w, yTop, -l))
            vertices.add(Point3D(w, yTop, l))
            vertices.add(Point3D(-w, yTop, l))

            vertices.add(Point3D(-w, yBot, -l))
            vertices.add(Point3D(w, yBot, -l))
            vertices.add(Point3D(w, yBot, l))
            vertices.add(Point3D(-w, yBot, l))

            val layerCol = if (layer == 1) Color(0xFFFCE7F3) else baseColor
            // Top face
            faces.add(Face3D(listOf(layerStart, layerStart + 1, layerStart + 2, layerStart + 3), layerCol))
            // Front face
            faces.add(Face3D(listOf(layerStart + 3, layerStart + 2, layerStart + 6, layerStart + 7), layerCol))
            // Left face
            faces.add(Face3D(listOf(layerStart, layerStart + 3, layerStart + 7, layerStart + 4), layerCol))
            // Right face
            faces.add(Face3D(listOf(layerStart + 1, layerStart + 5, layerStart + 6, layerStart + 2), layerCol))
        }

        // Satin ribbon tie around folded blanket
        val ribIdx = vertices.size
        vertices.add(Point3D(-14f, h / 2f + 2f, -l - 2f))
        vertices.add(Point3D(14f, h / 2f + 2f, -l - 2f))
        vertices.add(Point3D(14f, h / 2f + 2f, l + 2f))
        vertices.add(Point3D(-14f, h / 2f + 2f, l + 2f))
        faces.add(Face3D(listOf(ribIdx, ribIdx + 1, ribIdx + 2, ribIdx + 3), Color(0xFFEC4899)))

        return Mesh3D(vertices, faces, "Bedcover Single Lembut")
    }

    // 6. KACAMATA BAYI: Baby UV Sunglasses with flexible frame, tinted lenses & silicone strap
    private fun createBabySunglasses(frameColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val lensRadius = 38f
        val rimThickness = 7f
        val rimDepth = 12f
        val bridgeWidth = 26f
        val segments = 12

        val lensTint = Color(0xDD0F172A) // UV Polarized Dark Tint
        val strapColor = Color(0xFF38BDF8) // Soft elastic band

        // Left Rim & Lens (cx = -(lensRadius + bridgeWidth / 2))
        val leftCx = -(lensRadius + bridgeWidth / 2)
        createRimAndLens(vertices, faces, leftCx, lensRadius, rimThickness, rimDepth, segments, frameColor, lensTint)

        // Right Rim & Lens (cx = (lensRadius + bridgeWidth / 2))
        val rightCx = lensRadius + bridgeWidth / 2
        createRimAndLens(vertices, faces, rightCx, lensRadius, rimThickness, rimDepth, segments, frameColor, lensTint)

        // Center Bridge
        val b0 = vertices.size
        vertices.add(Point3D(-bridgeWidth / 2f, 4f, 0f))
        vertices.add(Point3D(bridgeWidth / 2f, 4f, 0f))
        vertices.add(Point3D(bridgeWidth / 2f, -4f, 0f))
        vertices.add(Point3D(-bridgeWidth / 2f, -4f, 0f))
        faces.add(Face3D(listOf(b0, b0 + 1, b0 + 2, b0 + 3), frameColor))

        // Left Temple & Elastic Headband
        val strapZ = 85f
        val s0 = vertices.size
        val leftOuterX = leftCx - lensRadius
        vertices.add(Point3D(leftOuterX, 4f, 0f))
        vertices.add(Point3D(leftOuterX, -4f, 0f))
        vertices.add(Point3D(leftOuterX - 15f, -3f, strapZ))
        vertices.add(Point3D(leftOuterX - 15f, 3f, strapZ))
        faces.add(Face3D(listOf(s0, s0 + 1, s0 + 2, s0 + 3), strapColor))

        // Right Temple & Elastic Headband
        val s1 = vertices.size
        val rightOuterX = rightCx + lensRadius
        vertices.add(Point3D(rightOuterX, 4f, 0f))
        vertices.add(Point3D(rightOuterX, -4f, 0f))
        vertices.add(Point3D(rightOuterX + 15f, -3f, strapZ))
        vertices.add(Point3D(rightOuterX + 15f, 3f, strapZ))
        faces.add(Face3D(listOf(s1, s1 + 1, s1 + 2, s1 + 3), strapColor))

        // Back loop connecting strap
        val loop0 = vertices.size
        vertices.add(Point3D(leftOuterX - 15f, 3f, strapZ))
        vertices.add(Point3D(leftOuterX - 15f, -3f, strapZ))
        vertices.add(Point3D(rightOuterX + 15f, -3f, strapZ))
        vertices.add(Point3D(rightOuterX + 15f, 3f, strapZ))
        faces.add(Face3D(listOf(loop0, loop0 + 1, loop0 + 2, loop0 + 3), strapColor))

        return Mesh3D(vertices, faces, "Kacamata Bayi UV Protection")
    }

    private fun createRimAndLens(
        vertices: MutableList<Point3D>,
        faces: MutableList<Face3D>,
        cx: Float,
        r: Float,
        thick: Float,
        depth: Float,
        segments: Int,
        frameColor: Color,
        lensTint: Color
    ) {
        val outerStart = vertices.size
        for (i in 0 until segments) {
            val a = (2 * PI * i / segments).toFloat()
            vertices.add(Point3D(cx + cos(a) * (r + thick), sin(a) * (r + thick) * 0.85f, -depth / 2f))
        }

        val innerStart = vertices.size
        for (i in 0 until segments) {
            val a = (2 * PI * i / segments).toFloat()
            vertices.add(Point3D(cx + cos(a) * r, sin(a) * r * 0.85f, -depth / 2f))
        }

        // Rim quads
        for (i in 0 until segments) {
            val next = (i + 1) % segments
            val o1 = outerStart + i
            val o2 = outerStart + next
            val i1 = innerStart + i
            val i2 = innerStart + next
            faces.add(Face3D(listOf(o1, o2, i2, i1), frameColor))
        }

        // Lens disk
        val lensCenter = vertices.size
        vertices.add(Point3D(cx, 0f, 0f))
        for (i in 0 until segments) {
            val next = (i + 1) % segments
            val i1 = innerStart + i
            val i2 = innerStart + next
            faces.add(Face3D(listOf(i1, i2, lensCenter), lensTint))
        }
    }

    // 7. BANTAL SET: Bantal peyang crown / concave center + 2 guling
    private fun createBantalSet(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        // Center Bantal Peyang (Crown shaped with center indentation)
        val pWidth = 85f
        val pHeight = 65f
        val pCenterIdx = vertices.size
        vertices.add(Point3D(0f, 0f, -8f)) // Indented head center for anti-flathead

        val rimSegs = 12
        val rimStart = vertices.size
        for (i in 0 until rimSegs) {
            val a = (2 * PI * i / rimSegs).toFloat()
            val yExtra = if (sin(a) > 0.4f) 12f else 0f // Crown ear bumps
            vertices.add(Point3D(cos(a) * pWidth, sin(a) * pHeight + yExtra, 12f))
        }

        // Front cushion slope
        for (i in 0 until rimSegs) {
            val next = (i + 1) % rimSegs
            val p1 = rimStart + i
            val p2 = rimStart + next
            faces.add(Face3D(listOf(p1, p2, pCenterIdx), baseColor))
        }

        // 2 Bolsters beside pillow
        createBolster(vertices, faces, -115f, 0f, 0f, 60f, Color(0xFFFCE7F3))
        createBolster(vertices, faces, 115f, 0f, 0f, 60f, Color(0xFFFCE7F3))

        return Mesh3D(vertices, faces, "Bantal Set Peyang & Guling")
    }

    // 8. BAK MANDI BAYI: Ergonomic bath tub with rim & silicone drain
    private fun createBabyBathtub(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val rimW = 140f
        val rimL = 95f
        val depth = 55f
        val segs = 14

        // Upper outer rim
        val rimStart = vertices.size
        for (i in 0 until segs) {
            val a = (2 * PI * i / segs).toFloat()
            vertices.add(Point3D(cos(a) * rimW, depth / 2f, sin(a) * rimL))
        }

        // Inner bottom floor
        val botStart = vertices.size
        for (i in 0 until segs) {
            val a = (2 * PI * i / segs).toFloat()
            vertices.add(Point3D(cos(a) * (rimW * 0.72f), -depth / 2f, sin(a) * (rimL * 0.7f)))
        }

        // Tub bowl wall
        for (i in 0 until segs) {
            val next = (i + 1) % segs
            val t1 = rimStart + i
            val t2 = rimStart + next
            val b1 = botStart + i
            val b2 = botStart + next
            faces.add(Face3D(listOf(t1, t2, b2, b1), baseColor))
        }

        // Bottom floor
        val drainCenter = vertices.size
        vertices.add(Point3D(0f, -depth / 2f + 1f, 0f))
        for (i in 0 until segs) {
            val next = (i + 1) % segs
            val b1 = botStart + i
            val b2 = botStart + next
            faces.add(Face3D(listOf(b1, b2, drainCenter), Color(0xFFE0F2FE)))
        }

        // Drain plug (silicone star)
        val plugStart = vertices.size
        vertices.add(Point3D(-8f, -depth / 2f + 3f, -8f))
        vertices.add(Point3D(8f, -depth / 2f + 3f, -8f))
        vertices.add(Point3D(8f, -depth / 2f + 3f, 8f))
        vertices.add(Point3D(-8f, -depth / 2f + 3f, 8f))
        faces.add(Face3D(listOf(plugStart, plugStart + 1, plugStart + 2, plugStart + 3), Color(0xFF38BDF8)))

        return Mesh3D(vertices, faces, "Bak Mandi Lipat Ergonomis")
    }

    // 9. PAKAIAN BAYI: Onesie Jumper 3D
    private fun createBabyJumper(baseColor: Color): Mesh3D {
        val vertices = mutableListOf<Point3D>()
        val faces = mutableListOf<Face3D>()

        val w = 65f
        val h = 95f
        val d = 16f

        // Body vertices
        val b0 = vertices.size
        vertices.add(Point3D(-w, h, -d))
        vertices.add(Point3D(w, h, -d))
        vertices.add(Point3D(w, -h * 0.7f, -d))
        vertices.add(Point3D(-w, -h * 0.7f, -d))

        vertices.add(Point3D(-w, h, d))
        vertices.add(Point3D(w, h, d))
        vertices.add(Point3D(w, -h * 0.7f, d))
        vertices.add(Point3D(-w, -h * 0.7f, d))

        // Front and back body
        faces.add(Face3D(listOf(b0, b0 + 1, b0 + 2, b0 + 3), baseColor))
        faces.add(Face3D(listOf(b0 + 4, b0 + 5, b0 + 6, b0 + 7), baseColor.copy(alpha = 0.9f)))

        // Sleeves
        val sLeft = vertices.size
        vertices.add(Point3D(-w, h, -d))
        vertices.add(Point3D(-w - 38f, h - 25f, -d))
        vertices.add(Point3D(-w - 38f, h - 50f, -d))
        vertices.add(Point3D(-w, h - 35f, -d))
        faces.add(Face3D(listOf(sLeft, sLeft + 1, sLeft + 2, sLeft + 3), baseColor))

        val sRight = vertices.size
        vertices.add(Point3D(w, h, -d))
        vertices.add(Point3D(w + 38f, h - 25f, -d))
        vertices.add(Point3D(w + 38f, h - 50f, -d))
        vertices.add(Point3D(w, h - 35f, -d))
        faces.add(Face3D(listOf(sRight, sRight + 1, sRight + 2, sRight + 3), baseColor))

        // Cute cloud baby badge on chest
        val badge = vertices.size
        vertices.add(Point3D(-20f, 35f, -d - 2f))
        vertices.add(Point3D(20f, 35f, -d - 2f))
        vertices.add(Point3D(20f, 5f, -d - 2f))
        vertices.add(Point3D(-20f, 5f, -d - 2f))
        faces.add(Face3D(listOf(badge, badge + 1, badge + 2, badge + 3), Color(0xFFFEF3C7)))

        return Mesh3D(vertices, faces, "Setelan Jumper Bayi Katun")
    }
}
