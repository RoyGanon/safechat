package com.example.safechat.ui.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.data.AppRepository
import com.example.safechat.models.Message
import com.example.safechat.R
import com.example.safechat.models.Role
import com.example.safechat.databinding.ItemMessageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessagesAdapter(
    private var items: List<Message>
) : RecyclerView.Adapter<MessagesAdapter.VH>() {

    class VH(val b: ItemMessageBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<Message>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val m = items[position]
        val me = AppRepository.currentUser
        val isMe = (me != null && m.fromUserId == me.id)

        val ctx = h.itemView.context

        // text + from
        val fromName = AppRepository.getUserName(m.fromUserId)
        h.b.tvFrom.text = if (isMe) "Me" else fromName
        h.b.tvText.text = m.text

        // time
        val fmt = SimpleDateFormat("HH:mm", Locale.getDefault())
        h.b.tvTime.text = fmt.format(Date(m.timestamp))

        // flagged (רק Teacher)
        val showFlagged = (me?.role == Role.TEACHER && m.flagged)
        h.b.tvFlagged.visibility = if (showFlagged) View.VISIBLE else View.GONE

        // bubble style + align
        val wrap = h.b.bubbleWrap
        val lp = wrap.layoutParams as LinearLayout.LayoutParams

        if (isMe) {
            // right bubble
            lp.gravity = Gravity.END
            lp.setMargins(60, 0, 0, 0) // spacing from left
            wrap.layoutParams = lp
            wrap.setBackgroundResource(R.drawable.bg_bubble_me)

            h.b.tvFrom.visibility = View.GONE
            h.b.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.white))
            h.b.tvTime.setTextColor(ContextCompat.getColor(ctx, R.color.white))
        } else {
            // left bubble
            lp.gravity = Gravity.START
            lp.setMargins(0, 0, 60, 0) // spacing from right
            wrap.layoutParams = lp
            wrap.setBackgroundResource(R.drawable.bg_bubble_other)

            h.b.tvFrom.visibility = View.VISIBLE
            h.b.tvText.setTextColor(ContextCompat.getColor(ctx, R.color.sc_text))
            h.b.tvTime.setTextColor(ContextCompat.getColor(ctx, R.color.sc_text_muted))
        }
    }
}