package com.rahul.nobrokerapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.rahul.nobrokerapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME = 3000
    lateinit var bottomAnim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeAnimation()

        //handler which will start a new activity after 3 seconds
        Handler().postDelayed(Runnable {
            run {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        }, SPLASH_TIME.toLong())
    }

    //animation for our logo
    private fun initializeAnimation() {
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        imageLogo.animation = bottomAnim
    }
}