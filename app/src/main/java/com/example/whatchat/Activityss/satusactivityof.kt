package com.example.whatchat.Activityss

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.whatchat.Model.VideoModel
import com.example.whatchat.R
import com.example.whatchat.databinding.ActivitySatusactivityofBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class satusactivityof : AppCompatActivity() {
    lateinit var binding: ActivitySatusactivityofBinding
    var selectedVideoUri: Uri? = null
    lateinit var videoLaincher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySatusactivityofBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople);

        videoLaincher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    selectedVideoUri = result.data!!.data
                    showPostVideo()
                    Toast.makeText(this,"Got Video"+selectedVideoUri.toString(),Toast.LENGTH_LONG).show()
                }

            }
        binding.uploadbtnfirst.setOnClickListener {
            checkPermissionAndOpenVideoPicker()
        }

        binding.addvideo.setOnClickListener {
            postvideo()
        }

        binding.CANCEL.setOnClickListener {
            finish()
        }

    }

    private fun postvideo() {
       if (binding.captionedit.text.toString().isEmpty()){
           binding.captionedit.setText("WRITE SOMETHING !!!!")
           return
       }
        setInProgress(true)
        selectedVideoUri?.apply {
            //store in firebase cloud storage
          val videoref =   FirebaseStorage.getInstance().reference.child("video/"+this.lastPathSegment )
            videoref.putFile(this)
                .addOnSuccessListener {
                    videoref.downloadUrl.addOnSuccessListener { downloadUrl->
                        //video model store in firebase firestore
                        postToFirestore(downloadUrl.toString())



                    }
                }


        }
    }

    private fun postToFirestore(url:String){
        var videoodel=VideoModel(
            FirebaseAuth.getInstance().currentUser?.uid!!+ "_" +Timestamp.now().toString(),
        binding.captionedit.text.toString(),
            url,
        FirebaseAuth.getInstance().currentUser?.uid!!,
            Timestamp.now()
        )
        Firebase.firestore.collection("videos")
            .document(videoodel.uploadId)
            .set(videoodel)
            .addOnSuccessListener {
                setInProgress(false)
                Toast.makeText(this,"Upload ",Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener {
                setInProgress(false)
                Toast.makeText(this,"Failed to Upload ${it.message.toString()} ",Toast.LENGTH_LONG).show()
                Log.e("Bilkis","${it.message.toString()}")

            }

    }

    private fun setInProgress(inProgress:Boolean){
        if (inProgress){
            binding.progressBar.visibility=View.VISIBLE
            binding.addvideo.visibility=View.GONE
        }else{
            binding.progressBar.visibility=View.GONE
            binding.addvideo.visibility=View.VISIBLE
        }
    }

    private fun showPostVideo() {

        selectedVideoUri.let {
            binding.constraintLayout.visibility = View.VISIBLE
            binding.uploadbtnfirst.visibility = View.GONE
            Glide.with(binding.videoupload).load(it).into(binding.videoupload)

        }
    }

    private fun checkPermissionAndOpenVideoPicker() {
        var readExternalVideo: String = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            readExternalVideo = android.Manifest.permission.READ_MEDIA_VIDEO
        } else {
            readExternalVideo = android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                this,
                readExternalVideo
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openVideoPicker()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(readExternalVideo), 100)
        }


    }

    private fun openVideoPicker() {
        var intent = Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type="video/*"
        videoLaincher.launch(intent)
    }
}
