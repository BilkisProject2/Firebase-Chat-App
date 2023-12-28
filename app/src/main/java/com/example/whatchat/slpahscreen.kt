package com.example.whatchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.whatchat.Activityss.MainActivity
import com.example.whatchat.Activityss.NumberActivity
import com.google.firebase.auth.FirebaseAuth

class slpahscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_slpahscreen)
        window.statusBarColor = this.resources.getColor(R.color.purople)

        Handler().postDelayed({
            var auth: FirebaseAuth = FirebaseAuth.getInstance()
            if (auth.currentUser!=null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            finish()
        }, 2000)
    }
}