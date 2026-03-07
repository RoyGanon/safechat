package com.example.safechat.utils

import android.content.Context
import android.net.Uri
import com.example.safechat.data.AppRepository
import com.example.safechat.models.Conversation

object ChatExporter {
    fun toText(conversation: Conversation): String {
        val lines = conversation.messages.map { m ->
            val name = AppRepository.getUserName(m.fromUserId)
            "$name: ${m.text}"
        }
        return "Conversation: ${conversation.title}\n\n" + lines.joinToString("\n")
    }

    fun writeToUri(context: Context, uri: Uri, text: String) {
        context.contentResolver.openOutputStream(uri)?.use { os ->
            os.write(text.toByteArray())
        }
    }
}