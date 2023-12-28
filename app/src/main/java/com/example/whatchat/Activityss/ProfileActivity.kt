package com.example.whatchat.Activityss

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.whatchat.Model.UserModel
import com.example.whatchat.R
import com.example.whatchat.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var database:FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var selectImg:Uri
    lateinit var dialog:AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople);

        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile")
            .setCancelable(false)
        database= FirebaseDatabase.getInstance()
        storage= FirebaseStorage.getInstance()
        auth= FirebaseAuth.getInstance()

        binding.circleImageView.setOnClickListener {
            val intent =Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)
        }
        binding.probtn.setOnClickListener { 
            if (binding.nameedit.text.isNullOrEmpty()){
                Toast.makeText(this,"Filed is Empty",Toast.LENGTH_LONG).show()
            }else if (selectImg==null){
                Toast.makeText(this,"Selected Image",Toast.LENGTH_LONG).show()
            }else uploadData()
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectImg).addOnCompleteListener{
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadInfo(task.toString())
                    
                }
            }
        }
        
    }

    private fun uploadInfo(imgUrl: String) {
val user = UserModel(auth.uid.toString(),binding.nameedit.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl )
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data Add Sucessfully",Toast.LENGTH_LONG).show()
                startActivity(
                    Intent(this
                ,MainActivity::class.java)
                )
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){
                selectImg =data.data!!
                binding.circleImageView.setImageURI(selectImg)
            }
        }
    }
}