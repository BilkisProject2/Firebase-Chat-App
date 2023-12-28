package com.example.whatchat.Activityss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.OutcomeReceiver
import android.widget.Toast
import com.example.whatchat.Model.MessageModel
import com.example.whatchat.R
import com.example.whatchat.adapter.MessageAdapter
import com.example.whatchat.databinding.ActivityChatPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.util.Date

class ChatPage : AppCompatActivity() {
    lateinit var binding: ActivityChatPageBinding
    lateinit var firebaseDatabase: FirebaseDatabase

    lateinit var sendUid:String
    lateinit var receiverUid:String

    lateinit var senderRoom:String
    lateinit var receiverRoom: String

    lateinit var list:ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.purople)

        firebaseDatabase = FirebaseDatabase.getInstance()

        //get sender uid
        sendUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        list=ArrayList()


        senderRoom = sendUid+receiverUid
        receiverRoom = receiverUid+sendUid

        binding.sendbtn.setOnClickListener {
            var messagessedit = binding.sendtext.text.toString()
            if (messagessedit.isEmpty()){
                Toast.makeText(this,"Message Field is Empty",Toast.LENGTH_LONG).show()
            }else{
                val message = MessageModel(messagessedit,sendUid,Date().time)

                //creating randomkey to store msg
                val randomkey = firebaseDatabase.reference.push().key

                firebaseDatabase.reference.child("chats").child(senderRoom).child("message").child(randomkey!!).setValue(message).addOnSuccessListener {
                    firebaseDatabase.reference.child("chats").child(receiverRoom).child("message").child(randomkey).setValue(message).addOnSuccessListener {
                        binding.sendtext.text = null
                        Toast.makeText(this,"Message send :-} ",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        firebaseDatabase.reference.child("chats").child(senderRoom).child("message").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snapshot1 in snapshot.children){
                    val data = snapshot1.getValue(MessageModel::class.java)
                    list.add(data!!)
                }
                binding.resco.adapter=MessageAdapter(this@ChatPage,list)

            }

            override fun onCancelled(error: DatabaseError) {
                sequenceOf(
                    Toast.makeText(
                        this@ChatPage,
                        "FAILURE : ${error.message.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                )
            }

        })

    }
}