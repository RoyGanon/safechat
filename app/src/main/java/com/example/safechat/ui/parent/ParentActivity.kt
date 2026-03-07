package com.example.safechat.ui.parent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safechat.data.AppRepository
import com.example.safechat.models.AppUser
import com.example.safechat.auth.LoginActivity
import com.example.safechat.R
import com.example.safechat.models.Role
import com.example.safechat.services.VibrationHelper

class ParentActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var tvParentContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        btnLogout = findViewById(R.id.btnLogout)
        tvParentContent = findViewById(R.id.tvParentContent)

        val me = AppRepository.currentUser
        if (me == null || me.role != Role.PARENT) {
            Toast.makeText(this, "Parent not logged in", Toast.LENGTH_SHORT).show()
            goToLogin()
            return
        }

        btnLogout.setOnClickListener {
            AppRepository.logout()
            goToLogin()
        }

        loadParentData(me)
    }

    private fun loadParentData(parent: AppUser) {
        val children = AppRepository.getChildrenOfParent(parent.id)
        val reports = AppRepository.getParentVisibleReports(parent.id)

        if (reports.isNotEmpty()) {
            VibrationHelper.vibrateShort(this)
        }

        val text = StringBuilder()

        if (children.isEmpty()) {
            text.append("No children linked to this parent.")
        } else {
            children.forEach { child ->
                text.append("Child: ${child.name}\n")

                val childReports = reports.filter { it.studentId == child.id }

                if (childReports.isEmpty()) {
                    text.append("Everything is okay right now. No active alerts for your child.\n\n")
                } else {
                    childReports.forEach { report ->
                        text.append("Alert approved by teacher\n")
                        text.append("Message: ${report.messageText}\n")
                        text.append("Teacher note: ${report.teacherNote.ifBlank { "No note" }}\n")
                        text.append("Handled: ${if (report.isHandled) "Yes ✅" else "Not yet"}\n")
                        if (report.handledNote.isNotBlank()) {
                            text.append("Handled note: ${report.handledNote}\n")
                        }
                        text.append("\n")
                    }
                }
            }
        }

        tvParentContent.text = text.toString()
    }

    private fun goToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }
}