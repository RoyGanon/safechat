package com.example.safechat.models

data class Conversation(
    val id: String,
    val title: String,
    val type: ConversationType,
    val participantIds: List<String>,
    val messages: MutableList<Message> = mutableListOf()
)
