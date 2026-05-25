package com.example.mindgarden.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.example.mindgarden.domain.model.FlowerType
import com.example.mindgarden.domain.model.GrowthStage
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FlowerCanvas(
    flowerType: FlowerType,
    stage: GrowthStage,
    modifier: Modifier = Modifier,
    isAlive: Boolean = true
) {
    val alpha = if (isAlive) 1f else 0.4f
    val grayscale = !isAlive

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val groundY = h - h * 0.08f
        val stemColor = if (grayscale) Color(0xFF888888) else Color(0xFF4A7C3F)
        val leafColor = if (grayscale) Color(0xFFAAAAAA) else Color(0xFF5A9048)

        drawOval(
            color = Color(0xFF6B4226).copy(alpha = alpha),
            topLeft = Offset(cx - w * 0.34f, groundY - h * 0.04f),
            size = Size(w * 0.68f, h * 0.09f)
        )

        if (stage == GrowthStage.SEED) return@Canvas

        val stemHeight = when (stage) {
            GrowthStage.SPROUT -> h * 0.15f
            GrowthStage.LEAVES -> h * 0.35f
            GrowthStage.BUD -> h * 0.55f
            GrowthStage.OPENING -> h * 0.62f
            GrowthStage.BLOOM -> h * 0.65f
            else -> h * 0.1f
        }
        val flowerTopY = groundY - stemHeight

        drawStem(cx, groundY, flowerTopY, stemColor, alpha)

        if (stage >= GrowthStage.LEAVES) {
            drawLeaf(cx, flowerTopY + stemHeight * 0.55f, left = true, leafColor, alpha)
            drawLeaf(cx, flowerTopY + stemHeight * 0.30f, left = false, leafColor, alpha)
        }

        if (stage >= GrowthStage.BUD) {
            val pc = if (grayscale) Color(0xFF999999) else flowerType.petalColor
            val cc = if (grayscale) Color(0xFF666666) else flowerType.centerColor
            val lc = if (grayscale) Color(0xFFBBBBBB) else flowerType.lightColor

            when (stage) {
                GrowthStage.BUD -> drawBud(cx, flowerTopY, cc, pc, alpha)
                GrowthStage.OPENING -> drawOpening(flowerType, cx, flowerTopY, pc, cc, lc, alpha)
                GrowthStage.BLOOM -> drawFullBloom(flowerType, cx, flowerTopY, pc, cc, lc, alpha)
                else -> {}
            }
        }
    }
}

private fun DrawScope.drawStem(
    cx: Float, groundY: Float, tipY: Float,
    color: Color, alpha: Float
) {
    val path = Path().apply {
        moveTo(cx, groundY - 8f)
        quadraticTo(cx + 8f, (groundY + tipY) / 2f, cx, tipY)
    }
    drawPath(
        path = path,
        color = color,
        alpha = alpha,
        style = Stroke(width = 5f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawLeaf(
    cx: Float, y: Float, left: Boolean,
    color: Color, alpha: Float
) {
    val sign = if (left) -1f else 1f
    val path = Path().apply {
        moveTo(cx, y)
        cubicTo(cx + sign * 30f, y - 15f, cx + sign * 28f, y - 40f, cx + sign * 2f, y - 42f)
        close()
    }
    drawPath(path, color.copy(alpha = alpha * 0.9f))
}

private fun DrawScope.drawBud(
    cx: Float, topY: Float,
    centerColor: Color, petalColor: Color, alpha: Float
) {
    drawOval(
        color = petalColor.copy(alpha = alpha),
        topLeft = Offset(cx - 12f, topY - 20f),
        size = Size(24f, 36f)
    )
    drawOval(
        color = centerColor.copy(alpha = alpha * 0.8f),
        topLeft = Offset(cx - 10f, topY),
        size = Size(20f, 18f)
    )
    val sepPath = Path().apply {
        moveTo(cx - 4f, topY + 5f)
        quadraticTo(cx, topY - 3f, cx + 4f, topY + 5f)
    }
    drawPath(sepPath, Color(0xFF4A7C3F).copy(alpha = alpha))
}

private fun DrawScope.drawOpening(
    type: FlowerType, cx: Float, topY: Float,
    pc: Color, cc: Color, lc: Color, alpha: Float
) {
    when (type) {
        FlowerType.SUNFLOWER -> {
            repeat(8) { i ->
                val angle = Math.toRadians((i * 45.0) - 90.0)
                val px = cx + cos(angle).toFloat() * 20f
                val py = topY + sin(angle).toFloat() * 20f
                rotate(i * 45f, pivot = Offset(px, py)) {
                    drawOval(pc.copy(alpha = alpha), Offset(px - 5f, py - 12f), Size(10f, 24f))
                }
            }
            drawCircle(cc.copy(alpha = alpha), 14f, Offset(cx, topY))
        }
        FlowerType.LAVENDER -> {
            repeat(5) { i ->
                val ox = if (i % 2 == 0) 7f else -7f
                val oy = 10f - i * 16f
                drawOval(
                    color = if (i < 2) lc.copy(alpha = alpha) else pc.copy(alpha = alpha),
                    topLeft = Offset(cx + ox - 7f, topY + oy - 9f),
                    size = Size(14f, 18f)
                )
            }
        }
        FlowerType.TULIP -> {
            val path = Path().apply {
                moveTo(cx - 18f, topY + 16f)
                quadraticTo(cx - 22f, topY - 10f, cx, topY - 20f)
                quadraticTo(cx + 22f, topY - 10f, cx + 18f, topY + 16f)
                quadraticTo(cx, topY + 24f, cx - 18f, topY + 16f)
                close()
            }
            drawPath(path = path, color = pc, alpha = alpha)
            drawOval(lc.copy(alpha = alpha * 0.7f), Offset(cx - 7f, topY - 6f), Size(14f, 20f))
        }
        FlowerType.ROSE -> {
            repeat(5) { i ->
                val angle = Math.toRadians((i * 72.0))
                val px = cx + cos(angle).toFloat() * 16f
                val py = topY + sin(angle).toFloat() * 16f
                drawCircle(lc.copy(alpha = alpha), 11f, Offset(px, py))
            }
            drawCircle(pc.copy(alpha = alpha), 12f, Offset(cx, topY))
            drawCircle(cc.copy(alpha = alpha), 7f, Offset(cx, topY))
        }
    }
}

private fun DrawScope.drawFullBloom(
    type: FlowerType, cx: Float, topY: Float,
    pc: Color, cc: Color, lc: Color, alpha: Float
) {
    when (type) {
        FlowerType.SUNFLOWER -> {
            repeat(12) { i ->
                val angle = Math.toRadians((i * 30.0) - 90.0)
                val px = cx + cos(angle).toFloat() * 26f
                val py = topY + sin(angle).toFloat() * 26f
                val fillColor = if (i % 2 == 0) pc else lc
                rotate(i * 30f, pivot = Offset(px, py)) {
                    drawOval(fillColor.copy(alpha = alpha), Offset(px - 6f, py - 14f), Size(12f, 28f))
                }
            }
            drawCircle(cc.copy(alpha = alpha), 18f, Offset(cx, topY))
            drawCircle(Color(0xFF2C2C2A).copy(alpha = alpha), 10f, Offset(cx, topY))
            repeat(6) { i ->
                val a = Math.toRadians((i * 60.0))
                val dotX = cx + cos(a).toFloat() * 5f
                val dotY = topY + sin(a).toFloat() * 5f
                drawCircle(lc.copy(alpha = alpha), 2f, Offset(dotX, dotY))
            }
        }
        FlowerType.LAVENDER -> {
            repeat(8) { i ->
                val ox = if (i % 2 == 0) 8f else -8f
                val oy = 12f - i * 14f
                val fillColor = if (i < 3) lc else pc
                drawOval(fillColor.copy(alpha = alpha), Offset(cx + ox - 8f, topY + oy - 10f), Size(16f, 20f))
                drawCircle(cc.copy(alpha = alpha * 0.7f), 3f, Offset(cx + ox, topY + oy))
            }
        }
        FlowerType.TULIP -> {
            val path = Path().apply {
                moveTo(cx - 22f, topY + 20f)
                quadraticTo(cx - 26f, topY - 14f, cx, topY - 26f)
                quadraticTo(cx + 26f, topY - 14f, cx + 22f, topY + 20f)
                quadraticTo(cx, topY + 30f, cx - 22f, topY + 20f)
                close()
            }
            drawPath(path = path, color = pc, alpha = alpha)
            val innerPath = Path().apply {
                moveTo(cx - 12f, topY + 14f)
                quadraticTo(cx - 14f, topY - 7f, cx, topY - 16f)
                quadraticTo(cx + 14f, topY - 7f, cx + 12f, topY + 14f)
            }
            drawPath(path = innerPath, color = lc, alpha = alpha * 0.65f)
            drawOval(cc.copy(alpha = alpha * 0.55f), Offset(cx - 7f, topY + 2f), Size(14f, 22f))
        }
        FlowerType.ROSE -> {
            repeat(5) { i ->
                val angle = Math.toRadians((i * 72.0))
                val px = cx + cos(angle).toFloat() * 20f
                val py = topY + sin(angle).toFloat() * 20f
                drawCircle(lc.copy(alpha = alpha), 14f, Offset(px, py))
            }
            repeat(5) { i ->
                val angle = Math.toRadians((i * 72.0) + 36.0)
                val px = cx + cos(angle).toFloat() * 12f
                val py = topY + sin(angle).toFloat() * 12f
                drawCircle(pc.copy(alpha = alpha), 13f, Offset(px, py))
            }
            drawCircle(cc.copy(alpha = alpha), 10f, Offset(cx, topY))
        }
    }
}
