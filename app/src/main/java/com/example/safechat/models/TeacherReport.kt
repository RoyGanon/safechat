package com.example.safechat.models

data class TeacherReport(
    val id: String,
    val studentId: String,
    val conversationId: String,
    val messageId: String,
    val messageText: String,
    val teacherId: String? = null,
    val teacherNote: String = "",
    val status: ReportStatus = ReportStatus.PENDING_REVIEW,
    val isHandled: Boolean = false,
    val handledNote: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
