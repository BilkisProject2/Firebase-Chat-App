package com.example.whatchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.whatchat.Model.VideoModel
import com.example.whatchat.databinding.SatussampleBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class VideoListAdapter(options: FirestoreRecyclerOptions<VideoModel>) :
    FirestoreRecyclerAdapter<VideoModel, VideoListAdapter.VideoViewHolder>(options) {

    inner class VideoViewHolder(val binding: SatussampleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindVideo(videoModel: VideoModel) {
            binding.videoView.apply {
                setVideoPath(videoModel.url)
                setOnPreparedListener {
                    it.start()
                    it.isLooping = true
                }

                //play paush
                setOnClickListener {
                    if (isPlaying) {
                        pause()
                        binding.pausebtn.visibility = View.VISIBLE
                    } else {
                        start()
                        binding.pausebtn.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = SatussampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bindVideo(model)

    }

}