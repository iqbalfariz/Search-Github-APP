package com.izo.apigithubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.databinding.ItemRowRvBinding

class FavoriteAdapter(private val listFavoriteUser: List<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowRvBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listFavoriteUser[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .circleCrop()
            .into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = data.username
        holder.binding.tvId.text = data.userId.toString()
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
//        }
    }

    override fun getItemCount(): Int = listFavoriteUser.size

    inner class ViewHolder(var binding: ItemRowRvBinding) : RecyclerView.ViewHolder(binding.root)

//    interface OnItemClickCallback {
//        fun onItemClicked(data: FavoriteEntity)
//    }

}