package com.tanmaysuryawanshi.mlproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class homefragment: Fragment() {
    lateinit var bottomAnim: Animation
    lateinit var topAnim: Animation
   // var next: Button? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val v: View = inflater.inflate(R.layout.fragment_home, container, false)
bottomAnim= AnimationUtils.loadAnimation(activity, R.anim.bottom_anim)
       topAnim= AnimationUtils.loadAnimation(activity, R.anim.top_anim)

       val tip:CardView= v.findViewById(R.id.tipsCard)
       tip.setOnClickListener(View.OnClickListener {

           val i = Intent(activity, UnderDevelopmentActivity::class.java)
           startActivity(i)
       })

       val report:CardView= v.findViewById(R.id.reportCard)

       report.setOnClickListener(View.OnClickListener {

           val i = Intent(activity,UnderDevelopmentActivity::class.java)
           startActivity(i)
       })

       val next:CardView = v.findViewById(R.id.checkupCard)
        next.setOnClickListener(View.OnClickListener {

            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        })

       val appname:TextView=v.findViewById(R.id.appName)
       val appDesc:TextView=v.findViewById(R.id.appDesc)
       appDesc.animation=topAnim
       appname.animation=topAnim


       next.animation = bottomAnim
       tip.animation=bottomAnim
       report.animation=bottomAnim

        return v
    }
}