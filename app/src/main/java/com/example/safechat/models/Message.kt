package com.example.safechat.models

data class Message(
    val id: String = "msg_${System.currentTimeMillis()}",
    val fromUserId: String,
    val text: String,
    val flagged: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)