package com.example.pedrosub_zero
//noinspection SuspiciousImport
import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: MainActivity) : SurfaceView(context), Runnable {
    private var thread: Thread? = null
    private var isPlaying = false
    private val holder: SurfaceHolder = getHolder()
    private val paint = Paint()
    private val character: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.character)
    private val characterX = 100
    private var characterY: Int
    private var characterSpeed: Int
    private var screenX = 0
    private var screenY = 0
    private val obstacles: MutableList<Obstacle>
    private var score: Int

    init {
        characterY = 100
        characterSpeed = 10
        obstacles = ArrayList()
        score = 0
    }

    override fun run() {
        while (isPlaying) {
            if (!holder.surface.isValid) continue

            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            update()
            draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun update() {
        characterY += characterSpeed
        if (characterY <= 0) characterY = 0
        if (characterY >= screenY - character.height) characterY = screenY - character.height

        val obstaclesToRemove: MutableList<Obstacle> = ArrayList()
        for (obstacle in obstacles) {
            obstacle.update()
            if (obstacle.isOffScreen) {
                obstaclesToRemove.add(obstacle)
                score++
            }
            if (obstacle.checkCollision(
                    characterX,
                    characterY,
                    character.width,
                    character.height
                )
            ) {
                // Game Over logic
                isPlaying = false
            }
        }
        obstacles.removeAll(obstaclesToRemove)

        if (obstacles.isEmpty() || obstacles[obstacles.size - 1].isOffScreen) {
            obstacles.add(Obstacle(screenX, screenY))
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(character, characterX.toFloat(), characterY.toFloat(), paint)
        for (obstacle in obstacles) {
            obstacle.draw(canvas)
        }
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("Score: $score", 50f, 100f, paint)
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread!!.start()
    }

    fun pause() {
        try {
            isPlaying = false
            thread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> characterSpeed = -15
            MotionEvent.ACTION_UP -> characterSpeed = 10
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenX = w
        screenY = h
    }
}
