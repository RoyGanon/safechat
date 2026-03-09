# SafeChat

**SafeChat** is an Android application designed to promote **safe communication between students, teachers, and parents** in a school environment.

The app detects harmful or offensive language in student messages, alerts teachers, and allows parents to stay informed when necessary.

---

## Features

### 💬 Chat System
- Private conversations between students and teachers
- Classroom group chats
- Real-time message display
- Conversation list for each user

### 🛡 Message Moderation
- Automatic detection of harmful language
- Risk scoring system for messages
- Warning dialog before sending risky messages

### 👩‍🏫 Teacher Monitoring
- Teachers receive flagged messages
- Teachers can:
  - Send alerts to parents
  - Keep reports internal
  - Mark incidents as handled

### 👨‍👩‍👧 Parent Dashboard
Parents can:
- View alerts related to their child
- See teacher notes
- Know if an incident has been handled

### ⚙ Admin Panel
Admin can manage the school system:

- Create users
- Edit users
- Delete users
- Create classes
- Assign teachers to classes
- Move students between classes
- Assign children to parents
- Update classroom group chats

### 📱 Android UI
- Material Design UI
- RecyclerView chat interface
- Card-based layout
- Search users to start chats

### 🔔 Notifications
- Vibration alerts when new reports appear
- Alerts for teachers and parents


# Screenshots  
  
## Login Screen  
![Login Screen](https://raw.githubusercontent.com/RoyGanon/safechat/master/Login%20Screen.png)  
  
## Conversations Screen  
![Conversations](https://raw.githubusercontent.com/RoyGanon/safechat/master/Conversations.png)  
  
## Chat  
![Chat](https://raw.githubusercontent.com/RoyGanon/safechat/master/chat.png)  
  
## New Chat  
![New Chat](https://raw.githubusercontent.com/RoyGanon/safechat/master/New%20chat%20screen.png)  
  
## Admin Panel  
![Admin](https://raw.githubusercontent.com/RoyGanon/safechat/master/Admin%20screen.png)  
  
## Parent Dashboard  
![Parent](https://raw.githubusercontent.com/RoyGanon/safechat/master/Parent%20screen.png)  
  
## Teacher Reports  
![Teacher Reports](https://raw.githubusercontent.com/RoyGanon/safechat/master/teacher%20report%20screen.png)  
  
## Teacher Decision Dialog  
![Teacher Dialog](https://raw.githubusercontent.com/RoyGanon/safechat/master/teacher%20report%20dialog.png)  
  
## Moderation Warning  
![Warning](https://raw.githubusercontent.com/RoyGanon/safechat/master/wanring%20before%20sending%20harmful%20text.png)

---

## User Roles

### Student
- Send messages
- Participate in chats
- Messages are monitored for harmful content

### Teacher
- Monitor flagged messages
- Manage exceptional events
- Send alerts to parents

### Parent
- View alerts related to their child
- Track teacher actions

### Admin
- Manage the entire system
- Control users and classes

---

## Tech Stack

**Language**
- Kotlin

**Android Components**
- RecyclerView
- Activities
- ViewBinding
- AlertDialog

**Architecture**
- Repository Pattern
- Local in-memory data storage

**Moderation**
- Rule-based message analysis
- Risk scoring system

---
## Project Structure

```mermaid
classDiagram

class AppUser
class ClassRoom
class Conversation
class Message
class TeacherReport
class AppRepository
class ModerationRules

class Role {
  <<enumeration>>
}

class ConversationType {
  <<enumeration>>
}

class ReportStatus {
  <<enumeration>>
}

AppUser --> Role
Conversation --> ConversationType
TeacherReport --> ReportStatus

Conversation *-- Message
TeacherReport --> Message
TeacherReport --> Conversation
TeacherReport --> AppUser

AppRepository o-- AppUser
AppRepository o-- ClassRoom
AppRepository o-- Conversation
AppRepository o-- TeacherReport
AppRepository ..> ModerationRules
