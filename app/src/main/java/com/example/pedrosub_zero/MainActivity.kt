package com.example.pedrosub_zero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var gameView: GameView? = null

    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)
    }

    protected fun onResume() {
        super.onResume()
        gameView!!.resume()
    }

    protected fun onPause() {
        super.onPause()
        gameView!!.pause()
    }
}