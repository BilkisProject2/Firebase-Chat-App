package com.example.whatchat.Activityss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.whatchat.R
import com.example.whatchat.adapter.ViewPagerAdapter
import com.example.whatchat.databinding.ActivityMainBinding
import com.example.whatchat.fagrment.ChatFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople);




        val fragmentArrayList =ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
//        fragmentArrayList.add(Stausragment())
//        fragmentArrayList.add(CallFragment())

        auth=FirebaseAuth.getInstance()
        if (auth.currentUser == null){
            startActivity(Intent(this,NumberActivity::class.java))
            finish()
        }

        val adapter = ViewPagerAdapter(this, supportFragmentManager,fragmentArrayList)
        binding.viewpager.adapter=adapter
//        binding.tabs.setupWithViewPager(binding.viewpager)



    }
}