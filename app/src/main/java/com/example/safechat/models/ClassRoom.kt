package com.example.safechat.models

data class ClassRoom(
    val id: String,
    val name: String,
    val teacherId: String? = null
)