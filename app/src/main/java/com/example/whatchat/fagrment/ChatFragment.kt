package com.example.whatchat.fagrment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.whatchat.Activityss.Recysatus
import com.example.whatchat.Model.UserModel
import com.example.whatchat.R
import com.example.whatchat.adapter.ChatAdapter
import com.example.whatchat.databinding.ActivityChatPageBinding
import com.example.whatchat.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    var database: FirebaseDatabase? = null
    lateinit var userlist: ArrayList<UserModel>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
    binding= FragmentChatBinding.inflate(layoutInflater)

    database = FirebaseDatabase.getInstance()
    userlist = ArrayList()
    database!!.reference.child("users")
        .addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (snapshot1 in snapshot.children){
                    val user = snapshot1.getValue(UserModel::class.java)
                    if (user!!.uid!=FirebaseAuth.getInstance().uid){
                          userlist.add(user)
                    }
                }
                binding.recyclerView.adapter = ChatAdapter(requireContext(),userlist)

            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(requireContext(),"failed ${error.message.toString()}",Toast.LENGTH_LONG).show()
            }

        })

    binding.buttonsatus.setOnClickListener {
        startActivity(Intent(context,Recysatus::class.java))
    }
        return binding.root
    }
}