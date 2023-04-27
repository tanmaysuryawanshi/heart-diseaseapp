package com.tanmaysuryawanshi.mlproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView


class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var topAnim: Animation
        lateinit var botAnim:Animation
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim)
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        val doge = findViewById<CardView>(R.id.logoCard)
        val appname = findViewById<TextView>(R.id.textViewAppname)
        val slogan = findViewById<TextView>(R.id.slogan)
        doge.animation = topAnim
        appname.animation = botAnim
        slogan.animation = botAnim
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@splash, homeActivity::class.java))
            finish()
        }, 3500)

    }
}