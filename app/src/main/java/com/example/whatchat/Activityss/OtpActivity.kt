package com.example.whatchat.Activityss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.whatchat.R
import com.example.whatchat.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpBinding
    lateinit var auth: FirebaseAuth
    lateinit var verificationId: String
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Otp Loading")
        builder.setIcon(R.drawable.baseline_cached_24)
        dialog = builder.create()
        dialog.show()

        val phonenumber = "+91" + intent.getStringExtra("number")
        binding.setphonenumber.text = phonenumber

        val option = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OtpActivity, "Verification Failed ${p0.message.toString()}", Toast.LENGTH_LONG)
                        .show()
                    Log.e("TAG","failed${p0.message.toString()}")
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dialog.dismiss()
                    verificationId = p0
                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(option)
        binding.verfibtn.setOnClickListener {
            if (binding.otpedit.text.isNullOrEmpty()) {
                Toast.makeText(this@OtpActivity, "Verification Failed", Toast.LENGTH_LONG).show()
            } else {
                dialog.show()

                val credential =
                    PhoneAuthProvider.getCredential(verificationId, binding.otpedit.text.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            dialog.dismiss()
                            startActivity(Intent(this, ProfileActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@OtpActivity,
                                "Error${it.exception}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}