package com.example.safechat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.data.AppRepository
import com.example.safechat.models.Conversation
import com.example.safechat.models.Role
import com.example.safechat.databinding.ItemConversationBinding

class ConversationsAdapter(
    private var items: List<Conversation>,
    private val onClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ConversationsAdapter.VH>() {

    class VH(val b: ItemConversationBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<Conversation>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemConversationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(b)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val c = items[position]
        val me = AppRepository.currentUser   // ✅ בלי .value

        h.b.tvConvoTitle.text = c.title

        val flaggedCount = c.messages.count { it.flagged }
        h.b.tvConvoSub.text =
            if (me?.role == Role.TEACHER && flaggedCount > 0)
                "⚠ Flagged: $flaggedCount"
            else
                c.type.name

        h.b.root.setOnClickListener { onClick(c) }
    }
}