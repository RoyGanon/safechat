package com.example.safechat.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.safechat.R
import com.example.safechat.data.AppRepository
import com.example.safechat.models.Role
import com.example.safechat.ui.admin.AdminActivity
import com.example.safechat.ui.chat.ConversationsActivity
import com.example.safechat.ui.parent.ParentActivity
import com.example.safechat.utils.AppConstants

class LoginActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvError: TextView
    private lateinit var tvDemo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvError = findViewById(R.id.tvError)
        tvDemo = findViewById(R.id.tvDemo)

        tvDemo.text = AppConstants.DEMO_LINES.joinToString("\n", prefix = "Demo:\n")

        btnLogin.setOnClickListener {
            val name = etName.text.toString().trim()
            val pass = etPassword.text.toString()

            val ok = AppRepository.login(name, pass)
            if (!ok) {
                tvError.text = getString(R.string.wrong_name_or_password)
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            tvError.visibility = View.GONE

            val role = AppRepository.currentUser?.role ?: Role.STUDENT

            val next = when (role) {
                Role.ADMIN -> AdminActivity::class.java
                Role.STUDENT -> ConversationsActivity::class.java
                Role.TEACHER -> ConversationsActivity::class.java
                Role.PARENT -> ParentActivity::class.java
            }

            startActivity(Intent(this, next))
            finish()
        }
    }
}