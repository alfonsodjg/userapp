package com.alfonso.usersapp.ui.modules.users.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfonso.usersapp.databinding.ItemUserBinding
import com.alfonso.usersapp.ui.modules.users.model.UsersUIModel

class UsersAdapter(
    private val onCheckedChange: (UsersUIModel, Boolean) -> Unit,
    private val onDeleteClick: (UsersUIModel) -> Unit
): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val users = mutableListOf<UsersUIModel>()
    private var isConnected: Boolean = true

    @SuppressLint("NotifyDataSetChanged")
    fun updateConnectionStatus(connected: Boolean) {
        isConnected = connected
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UsersUIModel) {
            binding.tvTitle.text = user.title
            binding.cbCompleted.isChecked = user.completed

            binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange(user, isChecked)
            }
            binding.cbCompleted.isEnabled = !isConnected
            binding.cbCompleted.alpha = if (isConnected) 0.3f else 1f
            binding.btnDelete.isEnabled = !isConnected
            binding.btnDelete.alpha = if (isConnected) 0.3f else 1f
            binding.btnDelete.setOnClickListener {
                onDeleteClick(user)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newUsers: List<UsersUIModel>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}