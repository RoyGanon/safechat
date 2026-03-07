package com.example.safechat.ui.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.R
import com.example.safechat.data.AppRepository
import com.example.safechat.models.Conversation
import com.example.safechat.moderation.ModerationRules
import com.example.safechat.utils.AppConstants

class ChatActivity : AppCompatActivity() {

    private lateinit var btnBack: Button
    private lateinit var btnExport: Button
    private lateinit var btnSend: Button
    private lateinit var etMessage: EditText
    private lateinit var tvTitle: TextView
    private lateinit var rvMessages: RecyclerView

    private lateinit var adapter: MessagesAdapter
    private lateinit var convo: Conversation

    private var warnDialog: AlertDialog? = null
    private var isSendingDialogOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        btnBack = findViewById(R.id.btnBack)
        btnExport = findViewById(R.id.btnExport)
        btnSend = findViewById(R.id.btnSend)
        etMessage = findViewById(R.id.etMessage)
        tvTitle = findViewById(R.id.tvTitle)
        rvMessages = findViewById(R.id.rvMessages)

        val convoId = intent.getStringExtra(AppConstants.EXTRA_CONVO_ID)
        if (convoId == null) {
            finish()
            return
        }

        val found = AppRepository.getConversationById(convoId)
        if (found == null) {
            finish()
            return
        }
        convo = found

        tvTitle.text = convo.title
        btnBack.setOnClickListener { finish() }

        adapter = MessagesAdapter(convo.messages)
        rvMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        rvMessages.adapter = adapter

        btnExport.setOnClickListener {
            Toast.makeText(this, "Export coming next step ✅", Toast.LENGTH_SHORT).show()
        }

        btnSend.setOnClickListener {
            if (isSendingDialogOpen) return@setOnClickListener

            val me = AppRepository.currentUser
            if (me == null) {
                Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val text = etMessage.text.toString().trim()
            if (text.isEmpty()) return@setOnClickListener

            val shouldWarn = ModerationRules.shouldWarn(text)
            val risk = ModerationRules.riskScore(text)

            if (shouldWarn || risk >= AppConstants.RISK_THRESHOLD_WARN) {
                showWarnDialog(text)
            } else {
                sendMessage(text)
            }
        }

        refresh()
    }

    private fun showWarnDialog(text: String) {
        if (isFinishing || isDestroyed) return
        if (warnDialog?.isShowing == true) return

        isSendingDialogOpen = true

        warnDialog = AlertDialog.Builder(this)
            .setTitle("Think Before Sending")
            .setMessage(
                "This message may contain harmful or offensive language.\n\n" +
                        "Are you sure you want to send it?"
            )
            .setPositiveButton("Send anyway") { _, _ ->
                sendMessage(text)
            }
            .setNegativeButton("Cancel", null)
            .setOnDismissListener {
                isSendingDialogOpen = false
                warnDialog = null
            }
            .create()

        warnDialog?.show()
    }

    private fun sendMessage(text: String) {
        AppRepository.sendMessage(convo.id, text)
        etMessage.setText("")
        refresh()
    }

    private fun refresh() {
        val updated = AppRepository.getConversationById(convo.id)
        if (updated != null) {
            convo = updated
        }

        adapter.submit(convo.messages.toList())
        if (convo.messages.isNotEmpty()) {
            rvMessages.scrollToPosition(convo.messages.size - 1)
        }
    }

    override fun onDestroy() {
        warnDialog?.dismiss()
        warnDialog = null
        isSendingDialogOpen = false
        super.onDestroy()
    }
}