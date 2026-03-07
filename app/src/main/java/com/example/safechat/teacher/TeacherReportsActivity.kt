package com.example.safechat.teacher

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.safechat.data.AppRepository
import com.example.safechat.databinding.ActivityTeacherReportsBinding
import com.example.safechat.models.ReportStatus
import com.example.safechat.models.Role
import com.example.safechat.models.TeacherReport
import com.example.safechat.services.VibrationHelper

class TeacherReportsActivity : AppCompatActivity() {

    private lateinit var b: ActivityTeacherReportsBinding
    private var currentReports: List<TeacherReport> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTeacherReportsBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnBack.setOnClickListener { finish() }

        val me = AppRepository.currentUser
        if (me == null || me.role != Role.TEACHER) {
            Toast.makeText(this, "Teacher not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadReports(me.id)

        b.lvReports.setOnItemClickListener { _, _, position, _ ->
            val report = currentReports.getOrNull(position) ?: return@setOnItemClickListener
            showDecisionDialog(report)
        }
    }

    override fun onResume() {
        super.onResume()
        val me = AppRepository.currentUser
        if (me != null && me.role == Role.TEACHER) {
            loadReports(me.id)
        }
    }

    private fun loadReports(teacherId: String) {
        currentReports = AppRepository.getReportsForTeacher(teacherId)

        if (currentReports.isNotEmpty()) {
            VibrationHelper.vibrateShort(this)
        }

        if (currentReports.isEmpty()) {
            b.lvReports.adapter = ArrayAdapter(
                this,
                R.layout.simple_list_item_1,
                listOf("No exceptional events ✅")
            )
            return
        }

        val items = currentReports.map { report ->
            val studentName = AppRepository.getUserById(report.studentId)?.name ?: "Unknown student"

            val statusText = when {
                report.isHandled -> "Handled ✅"
                report.status == ReportStatus.PENDING_REVIEW -> "Pending review"
                report.status == ReportStatus.REVIEWED_INTERNAL -> "Internal only"
                report.status == ReportStatus.REVIEWED_PARENT_VISIBLE -> "Sent to parent"
                else -> "Unknown"
            }

            "Student: $studentName\nStatus: $statusText\nMessage: ${report.messageText.take(70)}"
        }

        b.lvReports.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            items
        )
    }

    private fun showDecisionDialog(report: TeacherReport) {
        val noteInput = EditText(this).apply {
            hint = "Teacher note (optional)"
            setText(
                when {
                    report.teacherNote.isNotBlank() -> report.teacherNote
                    report.handledNote.isNotBlank() -> report.handledNote
                    else -> ""
                }
            )
            setPadding(32, 24, 32, 24)
        }

        val studentName = AppRepository.getUserById(report.studentId)?.name ?: "Unknown student"

        val statusText = when {
            report.isHandled -> "Handled ✅"
            report.status == ReportStatus.PENDING_REVIEW -> "Pending review"
            report.status == ReportStatus.REVIEWED_INTERNAL -> "Internal only"
            report.status == ReportStatus.REVIEWED_PARENT_VISIBLE -> "Sent to parent"
            else -> "Unknown"
        }

        AlertDialog.Builder(this)
            .setTitle("Exceptional Event Management")
            .setMessage(
                "Student: $studentName\n\n" +
                        "Status: $statusText\n\n" +
                        "Message:\n${report.messageText}\n\n" +
                        "Choose what to do:"
            )
            .setView(noteInput)
            .setPositiveButton("Send To Parent") { _, _ ->
                AppRepository.approveReportForParent(
                    reportId = report.id,
                    teacherNote = noteInput.text.toString()
                )
                VibrationHelper.vibrateShort(this)
                Toast.makeText(this, "Event sent to parent", Toast.LENGTH_SHORT).show()
                reload()
            }
            .setNegativeButton("Keep Internal") { _, _ ->
                AppRepository.keepReportInternal(
                    reportId = report.id,
                    teacherNote = noteInput.text.toString()
                )
                VibrationHelper.vibrateShort(this)
                Toast.makeText(this, "Event kept internal", Toast.LENGTH_SHORT).show()
                reload()
            }
            .setNeutralButton("Mark Handled") { _, _ ->
                AppRepository.markReportHandled(
                    reportId = report.id,
                    handledNote = noteInput.text.toString()
                )
                VibrationHelper.vibrateShort(this)
                Toast.makeText(this, "Event marked as handled", Toast.LENGTH_SHORT).show()
                reload()
            }
            .show()
    }

    private fun reload() {
        val me = AppRepository.currentUser ?: return
        if (me.role != Role.TEACHER) return
        loadReports(me.id)
    }
}