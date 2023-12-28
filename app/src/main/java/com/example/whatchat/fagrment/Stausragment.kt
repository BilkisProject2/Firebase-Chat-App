package com.example.whatchat.fagrment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whatchat.Activityss.Recysatus
import com.example.whatchat.Activityss.satusactivityof
import com.example.whatchat.Model.VideoModel
import com.example.whatchat.adapter.VideoListAdapter
import com.example.whatchat.databinding.FragmentStausragmentBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Stausragment : Fragment() {

   lateinit var binding:FragmentStausragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentStausragmentBinding.inflate(layoutInflater)

        binding.addsatus.setOnClickListener { startActivity(Intent(context, satusactivityof::class.java)) }
        binding.seesatus.setOnClickListener { startActivity(Intent(context, Recysatus ::class.java)) }



        return binding.root
    }






}