package com.example.threed

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Point3D(val x: Float, val y: Float, val z: Float) {
    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)
    operator fun times(factor: Float) = Point3D(x * factor, y * factor, z * factor)

    fun length(): Float = sqrt(x * x + y * y + z * z)

    fun normalized(): Point3D {
        val len = length()
        return if (len > 0.0001f) Point3D(x / len, y / len, z / len) else Point3D(0f, 1f, 0f)
    }

    fun cross(other: Point3D): Point3D {
        return Point3D(
            x = y * other.z - z * other.y,
            y = z * other.x - x * other.z,
            z = x * other.y - y * other.x
        )
    }

    fun dot(other: Point3D): Float = x * other.x + y * other.y + z * other.z

    fun rotateX(radians: Float): Point3D {
        val cosA = cos(radians)
        val sinA = sin(radians)
        return Point3D(
            x = x,
            y = y * cosA - z * sinA,
            z = y * sinA + z * cosA
        )
    }

    fun rotateY(radians: Float): Point3D {
        val cosA = cos(radians)
        val sinA = sin(radians)
        return Point3D(
            x = x * cosA + z * sinA,
            y = y,
            z = -x * sinA + z * cosA
        )
    }

    fun rotateZ(radians: Float): Point3D {
        val cosA = cos(radians)
        val sinA = sin(radians)
        return Point3D(
            x = x * cosA - y * sinA,
            y = x * sinA + y * cosA,
            z = z
        )
    }

    fun project(
        viewWidth: Float,
        viewHeight: Float,
        scale: Float,
        focalLength: Float = 600f,
        cameraDistance: Float = 550f
    ): ProjectedPoint {
        val denom = cameraDistance + z
        val factor = if (denom > 10f) focalLength / denom else 1f
        val px = viewWidth / 2f + x * factor * (scale / 100f)
        val py = viewHeight / 2f - y * factor * (scale / 100f)
        return ProjectedPoint(px, py, z)
    }
}

data class ProjectedPoint(val x: Float, val y: Float, val z: Float) {
    fun toOffset() = Offset(x, y)
}

data class Face3D(
    val indices: List<Int>,
    val color: Color,
    val isOutline: Boolean = false,
    val tag: String? = null
)

data class Mesh3D(
    val vertices: List<Point3D>,
    val faces: List<Face3D>,
    val name: String
)
