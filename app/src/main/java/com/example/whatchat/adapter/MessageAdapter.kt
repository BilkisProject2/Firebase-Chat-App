package com.example.whatchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.whatchat.Model.MessageModel
import com.example.whatchat.R
import com.example.whatchat.databinding.RevereritemlayoutBinding
import com.example.whatchat.databinding.SentitemlayoutBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list:ArrayList<MessageModel>):Adapter<RecyclerView.ViewHolder>() {

    var ITEM_SENT=1
    var ITEM_RECEVER=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return if (viewType==ITEM_SENT){
          SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sentitemlayout,parent,false))
      }else{
          RecevierViewHolder(LayoutInflater.from(context).inflate(R.layout.revereritemlayout,parent,false))
      }
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid==list[position].senderId) ITEM_SENT else ITEM_RECEVER
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messgae =list[position]
        if (holder.itemViewType==ITEM_SENT){
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.sendtext.text=messgae.message
        }else{
            val viewHolder = holder as RecevierViewHolder
            viewHolder.binding.revertext.text = messgae.message
        }
    }

    inner class SentViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding:SentitemlayoutBinding = SentitemlayoutBinding.bind(view)
    }

    inner class RecevierViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding:RevereritemlayoutBinding = RevereritemlayoutBinding.bind(view)

    }


}