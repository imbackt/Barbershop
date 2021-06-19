package com.imbackt.barbershop

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_SCREEN = 5000L
    }

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        image.animation = topAnimation
        logo.animation = bottomAnimation
        slogan.animation = bottomAnimation

        Handler().postDelayed({
            val pairs = arrayOf(
                Pair<View, String>(image, "logo_image"),
                Pair<View, String>(logo, "logo_text")

            )
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
            startActivity(Intent(this, Login::class.java), options.toBundle())
            finish()
        }, SPLASH_SCREEN)
    }
}