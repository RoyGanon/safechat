package com.example.safechat.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.safechat.data.AppRepository
import com.example.safechat.models.AppUser
import com.example.safechat.models.ClassRoom
import com.example.safechat.auth.LoginActivity
import com.example.safechat.R
import com.example.safechat.models.Role

class AdminActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var btnAddUser: Button
    private lateinit var btnEditUser: Button
    private lateinit var btnDeleteUser: Button
    private lateinit var btnCreateClass: Button
    private lateinit var btnMoveStudent: Button
    private lateinit var btnAssignTeacher: Button
    private lateinit var btnUpdateGroup: Button
    private lateinit var btnAssignChildToParent: Button
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btnLogout = findViewById(R.id.btnLogout)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnEditUser = findViewById(R.id.btnEditUser)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)
        btnCreateClass = findViewById(R.id.btnCreateClass)
        btnMoveStudent = findViewById(R.id.btnMoveStudent)
        btnAssignTeacher = findViewById(R.id.btnAssignTeacher)
        btnUpdateGroup = findViewById(R.id.btnUpdateGroup)
        btnAssignChildToParent = findViewById(R.id.btnAssignChildToParent)
        tvStatus = findViewById(R.id.tvStatus)

        val me = AppRepository.currentUser
        if (me?.role != Role.ADMIN) {
            finish()
            return
        }

        btnLogout.setOnClickListener {
            AppRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnAddUser.setOnClickListener { showAddUserDialog() }
        btnEditUser.setOnClickListener { showEditUserDialog() }
        btnDeleteUser.setOnClickListener { showDeleteUserDialog() }
        btnCreateClass.setOnClickListener { showCreateClassDialog() }
        btnMoveStudent.setOnClickListener { showMoveStudentDialog() }
        btnAssignTeacher.setOnClickListener { showAssignTeacherDialog() }
        btnUpdateGroup.setOnClickListener { showUpdateGroupDialog() }
        btnAssignChildToParent.setOnClickListener { showAssignChildToParentDialog() }

        tvStatus.text = "Ready ✅"
    }

    private fun classes(): List<ClassRoom> = AppRepository.classes.toList()
    private fun usersNoAdmin(): List<AppUser> = AppRepository.users.filter { it.role != Role.ADMIN }
    private fun students(): List<AppUser> = AppRepository.getStudents()
    private fun teachers(): List<AppUser> = AppRepository.getTeachers()
    private fun parents(): List<AppUser> = AppRepository.getParents()

    private fun Spinner.bindLabels(labels: List<String>) {
        adapter =
            ArrayAdapter(this@AdminActivity, android.R.layout.simple_spinner_dropdown_item, labels)
    }

    private fun showStatus(msg: String) {
        tvStatus.text = msg
    }

    private fun showAddUserDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_add_user, null)

        val etName = v.findViewById<EditText>(R.id.etUserName)
        val etPass = v.findViewById<EditText>(R.id.etUserPass)
        val rgRole = v.findViewById<RadioGroup>(R.id.rgRole)
        val rbStudent = v.findViewById<RadioButton>(R.id.rbStudent)
        val rbTeacher = v.findViewById<RadioButton>(R.id.rbTeacher)
        val rbParent = v.findViewById<RadioButton?>(R.id.rbParent)
        val spClass = v.findViewById<Spinner>(R.id.spStudentClass)

        val cls = classes()
        spClass.bindLabels(cls.map { "${it.name} (${it.id})" })

        fun selectedRole(): Role {
            return when {
                rbTeacher.isChecked -> Role.TEACHER
                rbParent?.isChecked == true -> Role.PARENT
                else -> Role.STUDENT
            }
        }

        fun updateClassVisibility() {
            spClass.visibility = if (selectedRole() == Role.STUDENT) View.VISIBLE else View.GONE
        }

        updateClassVisibility()
        rgRole.setOnCheckedChangeListener { _, _ -> updateClassVisibility() }

        AlertDialog.Builder(this)
            .setTitle("Add User")
            .setView(v)
            .setPositiveButton("Create") { _, _ ->
                val name = etName.text.toString().trim()
                val pass = etPass.text.toString().trim()

                if (name.isEmpty() || pass.isEmpty()) {
                    showStatus("Name & password required")
                    return@setPositiveButton
                }

                val role = selectedRole()
                val classId = if (role == Role.STUDENT) cls.getOrNull(spClass.selectedItemPosition)?.id else null

                if (role == Role.STUDENT && classId.isNullOrBlank()) {
                    showStatus("Student must have class")
                    return@setPositiveButton
                }

                val user = AppRepository.createUser(name, pass, role, classId)
                showStatus("Created: ${user.name} (${user.role}) ✅")

                if (role == Role.PARENT) {
                    showAssignChildToParentDialog(prefilledParentId = user.id)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteUserDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_pick_user, null)
        val sp = v.findViewById<Spinner>(R.id.spUser)

        val list = usersNoAdmin()
        if (list.isEmpty()) {
            showStatus("No users to delete")
            return
        }

        sp.bindLabels(list.map {
            val extra = when (it.role) {
                Role.STUDENT, Role.TEACHER -> " | class=${it.classId ?: "-"}"
                Role.PARENT -> " | children=${it.childIds.size}"
                Role.ADMIN -> ""
            }
            "${it.name} (${it.id}) - ${it.role}$extra"
        })

        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Pick a user to delete")
            .setView(v)
            .setPositiveButton("Delete") { _, _ ->
                val u = list.getOrNull(sp.selectedItemPosition) ?: return@setPositiveButton
                AppRepository.deleteUser(u.id)
                showStatus("Deleted: ${u.name} 🗑️")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditUserDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_edit_user, null)
        val spUser = v.findViewById<Spinner>(R.id.spUser)
        val etName = v.findViewById<EditText>(R.id.etNewName)
        val etPass = v.findViewById<EditText>(R.id.etNewPass)

        val list = usersNoAdmin()
        if (list.isEmpty()) {
            showStatus("No users to edit")
            return
        }

        spUser.bindLabels(list.map { "${it.name} (${it.id}) - ${it.role}" })

        AlertDialog.Builder(this)
            .setTitle("Edit User")
            .setView(v)
            .setPositiveButton("Save") { _, _ ->
                val selected = list.getOrNull(spUser.selectedItemPosition) ?: return@setPositiveButton
                val newName = etName.text.toString().trim()
                val newPass = etPass.text.toString().trim()

                val idx = AppRepository.users.indexOfFirst { it.id == selected.id }
                if (idx == -1) return@setPositiveButton

                val fixedName = if (newName.isBlank()) selected.name else newName
                val fixedPass = if (newPass.isBlank()) selected.password else newPass

                AppRepository.users[idx] = AppRepository.users[idx].copy(
                    name = fixedName,
                    password = fixedPass
                )

                showStatus("Updated: $fixedName ✏️")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showCreateClassDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_create_class, null)
        val et = v.findViewById<EditText>(R.id.etClassName)

        AlertDialog.Builder(this)
            .setTitle("Add Class")
            .setView(v)
            .setPositiveButton("Create") { _, _ ->
                val name = et.text.toString().trim()
                if (name.isEmpty()) {
                    showStatus("Class name required")
                    return@setPositiveButton
                }

                val c = AppRepository.createClass(name)
                showStatus("Created class: ${c.name} ✅")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showMoveStudentDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_move_student, null)
        val spStudent = v.findViewById<Spinner>(R.id.spStudent)
        val spToClass = v.findViewById<Spinner>(R.id.spMoveToClass)

        val sts = students()
        val cls = classes()

        if (sts.isEmpty() || cls.isEmpty()) {
            showStatus("Need students & classes first")
            return
        }

        spStudent.bindLabels(sts.map { "${it.name} (${it.id})" })
        spToClass.bindLabels(cls.map { "${it.name} (${it.id})" })

        AlertDialog.Builder(this)
            .setTitle("Move Student")
            .setView(v)
            .setPositiveButton("Move") { _, _ ->
                val student = sts.getOrNull(spStudent.selectedItemPosition) ?: return@setPositiveButton
                val toClass = cls.getOrNull(spToClass.selectedItemPosition) ?: return@setPositiveButton

                AppRepository.assignStudentToClass(student.id, toClass.id)
                showStatus("Moved ${student.name} → ${toClass.name} ✅")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAssignTeacherDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_assign_teacher, null)
        val spClass = v.findViewById<Spinner>(R.id.spClassForTeacher)
        val spTeacher = v.findViewById<Spinner>(R.id.spTeacher)

        val cls = classes()
        val ts = teachers()

        if (cls.isEmpty() || ts.isEmpty()) {
            showStatus("Need classes & teachers first")
            return
        }

        spClass.bindLabels(cls.map { "${it.name} (${it.id})" })
        spTeacher.bindLabels(ts.map { "${it.name} (${it.id})" })

        AlertDialog.Builder(this)
            .setTitle("Assign Teacher")
            .setView(v)
            .setPositiveButton("Assign") { _, _ ->
                val c = cls.getOrNull(spClass.selectedItemPosition) ?: return@setPositiveButton
                val t = ts.getOrNull(spTeacher.selectedItemPosition) ?: return@setPositiveButton

                AppRepository.setTeacherForClass(c.id, t.id)
                showStatus("Assigned ${t.name} → ${c.name} ✅")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showUpdateGroupDialog() {
        val v = layoutInflater.inflate(R.layout.dialog_update_group, null)
        val spClass = v.findViewById<Spinner>(R.id.spGroupClass)

        val cls = classes()
        if (cls.isEmpty()) {
            showStatus("No classes yet")
            return
        }

        spClass.bindLabels(cls.map { "${it.name} (${it.id})" })

        AlertDialog.Builder(this)
            .setTitle("Update Class Group")
            .setView(v)
            .setPositiveButton("Update") { _, _ ->
                val c = cls.getOrNull(spClass.selectedItemPosition) ?: return@setPositiveButton
                AppRepository.ensureGroupConversationForClass(c.id)
                showStatus("Group updated for ${c.name} ✅")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAssignChildToParentDialog(prefilledParentId: String? = null) {
        val v = layoutInflater.inflate(R.layout.dialog_assign_child_to_parent, null)
        val spParent = v.findViewById<Spinner>(R.id.spParent)
        val spChild = v.findViewById<Spinner>(R.id.spChild)

        val parentList = parents()
        val childList = students()

        if (parentList.isEmpty()) {
            showStatus("No parents found. Create a parent first.")
            return
        }

        if (childList.isEmpty()) {
            showStatus("No students found.")
            return
        }

        spParent.bindLabels(parentList.map { "${it.name} (${it.id})" })
        spChild.bindLabels(childList.map { "${it.name} (${it.id})" })

        if (prefilledParentId != null) {
            val index = parentList.indexOfFirst { it.id == prefilledParentId }
            if (index >= 0) spParent.setSelection(index)
        }

        AlertDialog.Builder(this)
            .setTitle("Assign Child To Parent")
            .setView(v)
            .setPositiveButton("Assign") { _, _ ->
                val parent = parentList.getOrNull(spParent.selectedItemPosition) ?: return@setPositiveButton
                val child = childList.getOrNull(spChild.selectedItemPosition) ?: return@setPositiveButton

                AppRepository.assignChildToParent(parent.id, child.id)
                showStatus("Assigned child ${child.name} → parent ${parent.name} ✅")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}