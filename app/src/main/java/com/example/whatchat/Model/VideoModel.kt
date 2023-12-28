package com.example.whatchat.Model

import java.security.Timestamp


data class VideoModel(

    var videoId:String="",
    var title:String="",
    var url:String="",
    var uploadId:String="",
    var creadTime: com.google.firebase.Timestamp=com.google.firebase.Timestamp.now()

)
