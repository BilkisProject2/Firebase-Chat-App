package com.example.whatchat.Model

import java.security.Timestamp

data class MessageModel(
    var message:String?="",
    var senderId:String?="",
    var timestamp: Long ?=0
)
