package com.example.safechat.models

data class AppUser(
    val id: String,
    val name: String,
    val password: String,
    val role: Role,
    val classId: String? = null,
    val childIds: List<String> = emptyList() // בשביל הורה
)