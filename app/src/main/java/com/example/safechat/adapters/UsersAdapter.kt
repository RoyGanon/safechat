package com.example.safechat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safechat.databinding.ItemUserBinding
import com.example.safechat.models.AppUser

class UsersAdapter(
    private var items: List<AppUser>,
    private val onClick: (AppUser) -> Unit
) : RecyclerView.Adapter<UsersAdapter.VH>() {

    class VH(val b: ItemUserBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<AppUser>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val u = items[position]
        h.b.tvUserName.text = u.name
        h.b.tvUserRole.text = u.role.name.lowercase()
        h.b.root.setOnClickListener { onClick(u) }
    }
}