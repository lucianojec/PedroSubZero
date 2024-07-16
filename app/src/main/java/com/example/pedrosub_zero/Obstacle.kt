package com.example.pedrosub_zero

import android.graphics.Canvas
import android.graphics.Paint

class Obstacle(screenX: Int, screenY: Int) {

    private var x: Int = screenX
    private val width: Int = 100
    private val height: Int = screenY / 3
    private var y: Int = (Math.random() * (screenY - height)).toInt()
    private val speed: Int = 15
    private val paint: Paint = Paint().apply {
        color = 0xFF000000.toInt()
    }

    val isOffScreen: Boolean
        get() = x + width < 0

    fun update() {
        x -= speed
    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(), paint)
    }

    fun checkCollision(characterX: Int, characterY: Int, characterWidth: Int, characterHeight: Int): Boolean {
        return characterX < x + width && characterX + characterWidth > x &&
                characterY < y + height && characterY + characterHeight > y
    }
}
