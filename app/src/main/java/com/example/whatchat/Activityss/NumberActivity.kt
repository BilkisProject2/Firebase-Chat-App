package com.example.whatchat.Activityss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatchat.R
import com.example.whatchat.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
    lateinit var binding: ActivityNumberBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople)

        auth=FirebaseAuth.getInstance()
        if (auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.verfibtn.setOnClickListener {
            var phone = binding.editTextPhone.text.toString()
            if (phone.isNullOrEmpty()){
                Toast.makeText(this,"FILED IS EMPTY",Toast.LENGTH_LONG).show()
            }else{
                var intent = Intent(this,OtpActivity::class.java)
                intent.putExtra("number",binding.editTextPhone.text.toString())
                startActivity(intent)
            }
        }
    }
}