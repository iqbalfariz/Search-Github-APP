package com.izo.apigithubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.databinding.ItemRowRvBinding

class FollowAdapter(private val listFollow: List<ItemsItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowRvBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listFollow[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = data.login
        holder.binding.tvId.text = data.id.toString()
    }

    override fun getItemCount(): Int = listFollow.size

    class ViewHolder(var binding: ItemRowRvBinding) : RecyclerView.ViewHolder(binding.root)

}