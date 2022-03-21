package com.izo.apigithubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.databinding.ItemRowRvBinding

class FollowingAdapter(private val listFollowing: List<ItemsItem>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowRvBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listFollowing[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = data.login
        holder.binding.tvId.text = data.id.toString()
    }

    override fun getItemCount(): Int = listFollowing.size

    class ViewHolder(var binding: ItemRowRvBinding) : RecyclerView.ViewHolder(binding.root)

}