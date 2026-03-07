package com.example.safechat.data

import com.example.safechat.utils.AppConstants
import com.example.safechat.models.AppUser
import com.example.safechat.models.ClassRoom
import com.example.safechat.models.Conversation
import com.example.safechat.models.ConversationType
import com.example.safechat.models.Message
import com.example.safechat.moderation.ModerationRules
import com.example.safechat.models.ReportStatus
import com.example.safechat.models.Role
import com.example.safechat.models.TeacherReport
import java.util.UUID

object AppRepository {

    // ---------- CLASSES ----------
    val classes: MutableList<ClassRoom> = mutableListOf(
        ClassRoom("c1", "Class A", teacherId = "t1"),
        ClassRoom("c2", "Class B", teacherId = "t2"),
        ClassRoom("c3", "Class C", teacherId = "t3"),
        ClassRoom("c4", "Class D", teacherId = "t4")
    )

    // ---------- USERS ----------
    val users: MutableList<AppUser> = mutableListOf(
        // Admin
        AppUser("admin", "Admin", "admin", Role.ADMIN),

        // Teachers
        AppUser("t1", "Teacher Dana", "1234", Role.TEACHER, classId = "c1"),
        AppUser("t2", "Teacher Yael", "1234", Role.TEACHER, classId = "c2"),
        AppUser("t3", "Teacher Michal", "1234", Role.TEACHER, classId = "c3"),
        AppUser("t4", "Teacher Roni", "1234", Role.TEACHER, classId = "c4"),

        // Students - Class A
        AppUser("s1", "Noam", "1111", Role.STUDENT, classId = "c1"),
        AppUser("s2", "Maya", "2222", Role.STUDENT, classId = "c1"),
        AppUser("s3", "Liam", "3333", Role.STUDENT, classId = "c1"),
        AppUser("s4", "Omer", "4444", Role.STUDENT, classId = "c1"),
        AppUser("s5", "Ella", "5555", Role.STUDENT, classId = "c1"),

        // Students - Class B
        AppUser("s6", "Daniel", "6666", Role.STUDENT, classId = "c2"),
        AppUser("s7", "Amit", "7777", Role.STUDENT, classId = "c2"),
        AppUser("s8", "Shira", "8888", Role.STUDENT, classId = "c2"),
        AppUser("s9", "Yuval", "9999", Role.STUDENT, classId = "c2"),
        AppUser("s10", "Tamar", "1010", Role.STUDENT, classId = "c2"),

        // Students - Class C
        AppUser("s11", "Itay", "1112", Role.STUDENT, classId = "c3"),
        AppUser("s12", "Noga", "1212", Role.STUDENT, classId = "c3"),
        AppUser("s13", "Alon", "1313", Role.STUDENT, classId = "c3"),
        AppUser("s14", "Roni", "1414", Role.STUDENT, classId = "c3"),
        AppUser("s15", "Gaya", "1515", Role.STUDENT, classId = "c3"),

        // Students - Class D
        AppUser("s16", "Ori", "1616", Role.STUDENT, classId = "c4"),
        AppUser("s17", "Lian", "1717", Role.STUDENT, classId = "c4"),
        AppUser("s18", "Ido", "1818", Role.STUDENT, classId = "c4"),
        AppUser("s19", "Sahar", "1919", Role.STUDENT, classId = "c4"),
        AppUser("s20", "Niv", "2020", Role.STUDENT, classId = "c4"),

        // Parents
        AppUser(
            id = "p1",
            name = "Parent Noam",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s1")
        ),
        AppUser(
            id = "p2",
            name = "Parent Maya",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s2")
        ),
        AppUser(
            id = "p3",
            name = "Parent Liam",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s3")
        ),
        AppUser(
            id = "p4",
            name = "Parent Omer",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s4")
        ),
        AppUser(
            id = "p5",
            name = "Parent Ella",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s5")
        ),
        AppUser(
            id = "p6",
            name = "Parent Daniel",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s6")
        ),
        AppUser(
            id = "p7",
            name = "Parent Amit",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s7")
        ),
        AppUser(
            id = "p8",
            name = "Parent Shira",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s8")
        ),
        AppUser(
            id = "p9",
            name = "Parent Yuval",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s9")
        ),
        AppUser(
            id = "p10",
            name = "Parent Tamar",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s10")
        ),
        AppUser(
            id = "p11",
            name = "Parent Itay",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s11")
        ),
        AppUser(
            id = "p12",
            name = "Parent Noga",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s12")
        ),
        AppUser(
            id = "p13",
            name = "Parent Alon",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s13")
        ),
        AppUser(
            id = "p14",
            name = "Parent Roni",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s14")
        ),
        AppUser(
            id = "p15",
            name = "Parent Gaya",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s15")
        ),
        AppUser(
            id = "p16",
            name = "Parent Ori",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s16")
        ),
        AppUser(
            id = "p17",
            name = "Parent Lian",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s17")
        ),
        AppUser(
            id = "p18",
            name = "Parent Ido",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s18")
        ),
        AppUser(
            id = "p19",
            name = "Parent Sahar",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s19")
        ),
        AppUser(
            id = "p20",
            name = "Parent Niv",
            password = "1234",
            role = Role.PARENT,
            childIds = listOf("s20")
        )
    )

    // ---------- CURRENT USER ----------
    var currentUser: AppUser? = null

    // ---------- CONVERSATIONS ----------
    val conversations: MutableList<Conversation> = mutableListOf()

    // ---------- REPORTS ----------
    val reports: MutableList<TeacherReport> = mutableListOf()

    init {
        classes.forEach { c ->
            ensureGroupConversationForClass(c.id)
        }
    }

    // ---------- AUTH ----------
    fun login(name: String, password: String): Boolean {
        val u = users.firstOrNull { it.name == name && it.password == password }
        currentUser = u
        return u != null
    }

    fun logout() {
        currentUser = null
    }

    // ---------- BASIC GETTERS ----------
    fun getUserName(id: String): String =
        users.firstOrNull { it.id == id }?.name ?: id

    fun getUserById(id: String): AppUser? =
        users.firstOrNull { it.id == id }

    fun getClassById(id: String?): ClassRoom? {
        if (id == null) return null
        return classes.firstOrNull { it.id == id }
    }

    // ---------- CONVERSATIONS ----------
    fun myConversations(): List<Conversation> {
        val me = currentUser ?: return emptyList()
        return conversations.filter { it.participantIds.contains(me.id) }
    }

    fun createDmWith(targetUserId: String): Conversation {
        val me = currentUser ?: error("Not logged in")

        val existing = conversations.firstOrNull {
            it.type == ConversationType.DM &&
                    it.participantIds.toSet() == setOf(me.id, targetUserId)
        }
        if (existing != null) return existing

        val title = "${getUserName(me.id)} ↔ ${getUserName(targetUserId)}"
        val convo = Conversation(
            id = "dm_" + UUID.randomUUID().toString(),
            title = title,
            type = ConversationType.DM,
            participantIds = listOf(me.id, targetUserId),
            messages = mutableListOf()
        )

        conversations.add(convo)
        return convo
    }

    fun getConversationById(conversationId: String): Conversation? {
        return conversations.firstOrNull { it.id == conversationId }
    }

    fun sendMessage(conversationId: String, text: String): Message? {
        val me = currentUser ?: return null
        val convo = conversations.firstOrNull { it.id == conversationId } ?: return null

        val risk = ModerationRules.riskScore(text)
        val flagged = risk >= AppConstants.RISK_THRESHOLD_WARN

        val message = Message(
            id = "msg_" + UUID.randomUUID().toString(),
            fromUserId = me.id,
            text = text,
            flagged = flagged
        )

        convo.messages.add(message)

        if (me.role == Role.STUDENT && risk >= AppConstants.RISK_THRESHOLD_REPORT) {
            createTeacherReportForStudentMessage(
                student = me,
                conversation = convo,
                message = message
            )
        }

        return message
    }

    private fun createTeacherReportForStudentMessage(
        student: AppUser,
        conversation: Conversation,
        message: Message
    ) {
        val cls = getClassById(student.classId)
        val teacherId = cls?.teacherId ?: return

        reports.add(
            TeacherReport(
                id = "rep_" + UUID.randomUUID().toString(),
                studentId = student.id,
                conversationId = conversation.id,
                messageId = message.id,
                messageText = message.text,
                teacherId = teacherId,
                teacherNote = "",
                status = ReportStatus.PENDING_REVIEW,
                isHandled = false,
                handledNote = ""
            )
        )
    }

    // ---------- CLASS GROUPS ----------
    fun ensureGroupConversationForClass(classId: String) {
        val cls = classes.firstOrNull { it.id == classId } ?: return

        val participants = users
            .filter { it.classId == classId && it.role != Role.PARENT && it.role != Role.ADMIN }
            .map { it.id }

        if (participants.isEmpty()) return

        val groupId = "group_$classId"

        val existing = conversations.firstOrNull { it.id == groupId }
        if (existing == null) {
            conversations.add(
                Conversation(
                    id = groupId,
                    title = "${cls.name} Group 👩‍🏫",
                    type = ConversationType.GROUP,
                    participantIds = participants,
                    messages = mutableListOf(
                        Message(
                            fromUserId = participants.first(),
                            text = "Welcome to ${cls.name}!"
                        )
                    )
                )
            )
        } else {
            val idx = conversations.indexOfFirst { it.id == groupId }
            if (idx != -1) {
                val keepMessages = conversations[idx].messages
                conversations[idx] = conversations[idx].copy(
                    participantIds = participants,
                    messages = keepMessages
                )
            }
        }
    }

    // ---------- ADMIN HELPERS ----------
    fun getStudents(): List<AppUser> =
        users.filter { it.role == Role.STUDENT }

    fun getTeachers(): List<AppUser> =
        users.filter { it.role == Role.TEACHER }

    fun getParents(): List<AppUser> =
        users.filter { it.role == Role.PARENT }

    fun getChildrenCandidates(): List<AppUser> =
        users.filter { it.role == Role.STUDENT }

    // ---------- ADMIN ACTIONS ----------
    fun createClass(name: String): ClassRoom {
        val c = ClassRoom(
            id = "c_" + UUID.randomUUID().toString().take(8),
            name = name.trim()
        )
        classes.add(c)
        ensureGroupConversationForClass(c.id)
        return c
    }

    fun createUser(
        name: String,
        password: String,
        role: Role,
        classId: String? = null,
        childIds: List<String> = emptyList()
    ): AppUser {
        val id = when (role) {
            Role.ADMIN -> "admin_" + UUID.randomUUID().toString().take(6)
            Role.TEACHER -> "t_" + UUID.randomUUID().toString().take(6)
            Role.STUDENT -> "s_" + UUID.randomUUID().toString().take(6)
            Role.PARENT -> "p_" + UUID.randomUUID().toString().take(6)
        }

        val fixedClassId = when (role) {
            Role.STUDENT -> {
                require(!classId.isNullOrBlank()) { "Student must have class" }
                classId
            }
            Role.TEACHER -> classId
            Role.ADMIN, Role.PARENT -> null
        }

        val fixedChildIds = if (role == Role.PARENT) childIds else emptyList()

        val u = AppUser(
            id = id,
            name = name.trim(),
            password = password,
            role = role,
            classId = fixedClassId,
            childIds = fixedChildIds
        )

        users.add(u)

        if (!fixedClassId.isNullOrBlank()) {
            ensureGroupConversationForClass(fixedClassId)
        }

        return u
    }

    fun deleteUser(userId: String) {
        val user = users.firstOrNull { it.id == userId } ?: return
        val oldClassId = user.classId

        users.removeAll { it.id == userId }

        if (user.role == Role.STUDENT) {
            val parentIndexes = users.withIndex()
                .filter { it.value.role == Role.PARENT && userId in it.value.childIds }
                .map { it.index }

            parentIndexes.forEach { idx ->
                val parent = users[idx]
                users[idx] = parent.copy(
                    childIds = parent.childIds.filter { it != userId }
                )
            }
        }

        reports.removeAll {
            it.studentId == userId || it.teacherId == userId
        }

        conversations.removeAll {
            it.type == ConversationType.DM && it.participantIds.contains(userId)
        }

        if (!oldClassId.isNullOrBlank()) {
            ensureGroupConversationForClass(oldClassId)
        }
    }

    fun assignUserToClass(userId: String, classId: String?) {
        val idx = users.indexOfFirst { it.id == userId }
        if (idx == -1) return

        val oldClass = users[idx].classId
        val user = users[idx]

        if (user.role == Role.PARENT || user.role == Role.ADMIN) return

        users[idx] = user.copy(classId = classId)

        if (!oldClass.isNullOrBlank()) ensureGroupConversationForClass(oldClass)
        if (!classId.isNullOrBlank()) ensureGroupConversationForClass(classId)
    }

    fun assignStudentToClass(studentId: String, classId: String) {
        val u = users.firstOrNull { it.id == studentId } ?: return
        if (u.role != Role.STUDENT) return
        assignUserToClass(studentId, classId)
    }

    fun setTeacherForClass(classId: String, teacherId: String) {
        val teacher = users.firstOrNull { it.id == teacherId } ?: return
        if (teacher.role != Role.TEACHER) return

        val classIdx = classes.indexOfFirst { it.id == classId }
        if (classIdx == -1) return

        val oldTeacherId = classes[classIdx].teacherId

        classes[classIdx] = classes[classIdx].copy(teacherId = teacherId)

        val teacherIdx = users.indexOfFirst { it.id == teacherId }
        if (teacherIdx != -1) {
            val oldClass = users[teacherIdx].classId
            users[teacherIdx] = users[teacherIdx].copy(classId = classId)

            if (!oldClass.isNullOrBlank()) ensureGroupConversationForClass(oldClass)
        }

        if (!oldTeacherId.isNullOrBlank()) {
            // נשאיר כמו שהוא כרגע
        }

        ensureGroupConversationForClass(classId)
    }

    fun assignChildToParent(parentId: String, childId: String) {
        val parentIdx = users.indexOfFirst { it.id == parentId }
        if (parentIdx == -1) return

        val parent = users[parentIdx]
        if (parent.role != Role.PARENT) return

        val child = users.firstOrNull { it.id == childId } ?: return
        if (child.role != Role.STUDENT) return

        if (childId in parent.childIds) return

        users[parentIdx] = parent.copy(
            childIds = parent.childIds + childId
        )
    }

    fun removeChildFromParent(parentId: String, childId: String) {
        val parentIdx = users.indexOfFirst { it.id == parentId }
        if (parentIdx == -1) return

        val parent = users[parentIdx]
        if (parent.role != Role.PARENT) return

        users[parentIdx] = parent.copy(
            childIds = parent.childIds.filter { it != childId }
        )
    }

    // ---------- TEACHER REPORTS ----------
    fun getReportsForTeacher(teacherId: String): List<TeacherReport> {
        return reports
            .filter { it.teacherId == teacherId }
            .sortedByDescending { it.createdAt }
    }

    fun getPendingReportsForTeacher(teacherId: String): List<TeacherReport> {
        return reports.filter {
            it.teacherId == teacherId &&
                    it.status == ReportStatus.PENDING_REVIEW &&
                    !it.isHandled
        }.sortedByDescending { it.createdAt }
    }

    fun getOpenReportsForTeacher(teacherId: String): List<TeacherReport> {
        return reports.filter {
            it.teacherId == teacherId && !it.isHandled
        }.sortedByDescending { it.createdAt }
    }

    fun approveReportForParent(reportId: String, teacherNote: String) {
        val idx = reports.indexOfFirst { it.id == reportId }
        if (idx == -1) return

        val old = reports[idx]
        reports[idx] = old.copy(
            teacherNote = teacherNote.trim(),
            status = ReportStatus.REVIEWED_PARENT_VISIBLE
        )
    }

    fun keepReportInternal(reportId: String, teacherNote: String) {
        val idx = reports.indexOfFirst { it.id == reportId }
        if (idx == -1) return

        val old = reports[idx]
        reports[idx] = old.copy(
            teacherNote = teacherNote.trim(),
            status = ReportStatus.REVIEWED_INTERNAL
        )
    }

    fun markReportHandled(reportId: String, handledNote: String = "") {
        val idx = reports.indexOfFirst { it.id == reportId }
        if (idx == -1) return

        val old = reports[idx]
        reports[idx] = old.copy(
            isHandled = true,
            handledNote = handledNote.trim()
        )
    }

    // ---------- PARENT ----------
    fun getChildrenOfParent(parentId: String): List<AppUser> {
        val parent = users.firstOrNull { it.id == parentId } ?: return emptyList()
        if (parent.role != Role.PARENT) return emptyList()

        return users.filter { it.id in parent.childIds }
    }

    fun getParentVisibleReports(parentId: String): List<TeacherReport> {
        val parent = users.firstOrNull { it.id == parentId } ?: return emptyList()
        if (parent.role != Role.PARENT) return emptyList()

        return reports.filter {
            it.studentId in parent.childIds &&
                    it.status == ReportStatus.REVIEWED_PARENT_VISIBLE
        }.sortedByDescending { it.createdAt }
    }

    fun getPositiveMessageForParent(parentId: String, childId: String): String {
        val parent = users.firstOrNull { it.id == parentId } ?: return "No parent found."
        if (parent.role != Role.PARENT) return "This user is not a parent."

        val child = users.firstOrNull { it.id == childId } ?: return "No child found."
        if (child.id !in parent.childIds) return "This child is not linked to the parent."

        val hasVisibleReports = reports.any {
            it.studentId == childId &&
                    it.status == ReportStatus.REVIEWED_PARENT_VISIBLE
        }

        return if (!hasVisibleReports) {
            "Good news! There are currently no approved alerts for your child."
        } else {
            "There are teacher-approved alerts available for your child."
        }
    }
}