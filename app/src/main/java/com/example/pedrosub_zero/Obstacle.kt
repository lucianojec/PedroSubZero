package com.example.pedrosub_zero

import android.graphics.Canvas
import android.graphics.Paint


class Obstacle(private var x: Int, screenY: Int) {
    private val y: Int
    private val width = 100
    private val height = screenY / 3
    private val speed = 15
    private val paint: Paint

    init {
        y = (Math.random() * (screenY - height)).toInt()
        paint = Paint()
        paint.color = -0x1000000
    }

    fun update() {
        x -= speed
    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(
            x.toFloat(),
            y.toFloat(),
            (x + width).toFloat(),
            (y + height).toFloat(),
            paint
        )
    }

    val isOffScreen: Boolean
        get() = x + width < 0

    fun checkCollision(
        characterX: Int,
        characterY: Int,
        characterWidth: Int,
        characterHeight: Int
    ): Boolean {
        return characterX < x + width && characterX + characterWidth > x && characterY < y + height && characterY + characterHeight > y
    }
}
