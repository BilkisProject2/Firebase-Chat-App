package com.example.whatchat.Activityss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whatchat.Model.VideoModel
import com.example.whatchat.R
import com.example.whatchat.adapter.VideoListAdapter
import com.example.whatchat.databinding.ActivityRecysatusBinding
import com.example.whatchat.fagrment.Stausragment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Recysatus : AppCompatActivity() {
    lateinit var binding: ActivityRecysatusBinding
    lateinit var adapter:VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecysatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople);

//        setupviewpager()

        updateAdapter()

        binding.addsatus.setOnClickListener {
            startActivity(Intent(this,satusactivityof::class.java))
        }

    }


//    private fun setupviewpager() {
//        val options = FirestoreRecyclerOptions.Builder<VideoModel>().setQuery(
//            Firebase.firestore.collection("videos")
//            , VideoModel::class.java
//        ).build()
//
////        adapter= VideoListAdapter(options)
////        adapter.notifyDataSetChanged()
////
////
////        adapter.op(updatedOptions)
//        adapter.
//    }

    private fun updateAdapter() {
        val updatedOptions = FirestoreRecyclerOptions.Builder<VideoModel>().setQuery(
            Firebase.firestore.collection("videos"), VideoModel::class.java
        ).build()
        adapter=VideoListAdapter(updatedOptions)
        adapter.updateOptions(updatedOptions)
        adapter.notifyDataSetChanged()
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}