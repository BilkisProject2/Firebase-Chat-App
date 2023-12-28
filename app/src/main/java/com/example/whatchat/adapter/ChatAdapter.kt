package com.example.whatchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatchat.Activityss.ChatPage
import com.example.whatchat.Model.UserModel
import com.example.whatchat.R
import com.example.whatchat.databinding.ChatSampleBinding

class ChatAdapter(var context: Context, var list:ArrayList<UserModel>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    inner  class  ChatViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding:ChatSampleBinding= ChatSampleBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_sample,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.chatsampleimage)
        holder.binding.chatsamplename.text=user.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatPage::class.java)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }

    }

}