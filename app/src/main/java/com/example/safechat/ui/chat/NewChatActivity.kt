package com.example.safechat.ui.chat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.R
import com.example.safechat.adapters.UsersAdapter
import com.example.safechat.data.AppRepository
import com.example.safechat.models.AppUser
import com.example.safechat.models.Role
import com.example.safechat.utils.AppConstants

class NewChatActivity : AppCompatActivity() {

    private lateinit var btnBack: Button
    private lateinit var etSearchUser: EditText
    private lateinit var rvUsers: RecyclerView
    private lateinit var adapter: UsersAdapter

    private var allCandidates: List<AppUser> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        btnBack = findViewById(R.id.btnBack)
        etSearchUser = findViewById(R.id.etSearchUser)
        rvUsers = findViewById(R.id.rvUsers)

        btnBack.setOnClickListener { finish() }

        val me = AppRepository.currentUser
        if (me == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        adapter = UsersAdapter(emptyList()) { user ->
            val convo = AppRepository.createDmWith(user.id)
            startActivity(
                Intent(this, ChatActivity::class.java).apply {
                    putExtra(AppConstants.EXTRA_CONVO_ID, convo.id)
                }
            )
            finish()
        }

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = adapter

        allCandidates = when (me.role) {
            Role.STUDENT -> AppRepository.users.filter {
                it.id != me.id && (it.role == Role.STUDENT || it.role == Role.TEACHER)
            }

            Role.TEACHER -> AppRepository.users.filter {
                it.id != me.id && (it.role == Role.STUDENT || it.role == Role.TEACHER)
            }

            Role.PARENT -> emptyList()
            Role.ADMIN -> emptyList()
        }

        adapter.submit(allCandidates)

        if (allCandidates.isEmpty()) {
            Toast.makeText(this, "No users available for chat", Toast.LENGTH_SHORT).show()
        }

        etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterUsers(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun filterUsers(query: String) {
        val normalized = query.trim().lowercase()

        val filtered = if (normalized.isEmpty()) {
            allCandidates
        } else {
            allCandidates.filter { user ->
                user.name.lowercase().contains(normalized)
            }
        }

        adapter.submit(filtered)
    }
}