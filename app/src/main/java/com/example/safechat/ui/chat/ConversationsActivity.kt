package com.example.safechat.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.utils.AppConstants
import com.example.safechat.data.AppRepository
import com.example.safechat.auth.LoginActivity
import com.example.safechat.R
import com.example.safechat.models.Role
import com.example.safechat.teacher.TeacherReportsActivity

class ConversationsActivity : AppCompatActivity() {

    private lateinit var rvConversations: RecyclerView
    private lateinit var btnNewChat: Button
    private lateinit var btnLogout: Button
    private lateinit var btnManageReports: Button
    private lateinit var adapter: ConversationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations)

        rvConversations = findViewById(R.id.rvConversations)
        btnNewChat = findViewById(R.id.btnNewChat)
        btnLogout = findViewById(R.id.btnLogout)
        btnManageReports = findViewById(R.id.btnManageReports)

        adapter = ConversationsAdapter(emptyList()) { convo ->
            startActivity(Intent(this, ChatActivity::class.java).apply {
                putExtra(AppConstants.EXTRA_CONVO_ID, convo.id)
            })
        }

        rvConversations.layoutManager = LinearLayoutManager(this)
        rvConversations.adapter = adapter

        val me = AppRepository.currentUser

        if (me?.role == Role.TEACHER) {
            btnManageReports.visibility = View.VISIBLE
            btnManageReports.setOnClickListener {
                startActivity(Intent(this, TeacherReportsActivity::class.java))
            }
        } else {
            btnManageReports.visibility = View.GONE
        }

        btnNewChat.setOnClickListener {
            startActivity(Intent(this, NewChatActivity::class.java))
        }

        btnLogout.setOnClickListener {
            AppRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submit(AppRepository.myConversations())
    }
}