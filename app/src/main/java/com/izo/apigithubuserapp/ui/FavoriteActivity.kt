package com.izo.apigithubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.R
import com.izo.apigithubuserapp.adapter.FavoriteAdapter
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.databinding.ActivityFavoriteBinding
import com.izo.apigithubuserapp.viewmodel.DetailViewModel
import com.izo.apigithubuserapp.viewmodel.FavoriteViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

//        favoriteBinding?.rvFavorite?.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = favoriteAdapter
//        }

        favoriteViewModel.getData().observe(this) { result ->
            setRecyclerView(result)
        }
    }

    private fun setRecyclerView(result: List<FavoriteEntity>) {
        val favoriteAdapter = FavoriteAdapter(result)
        favoriteBinding.rvFavorite.adapter = favoriteAdapter
        val layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        favoriteBinding.rvFavorite.addItemDecoration(itemDecoration)
        favoriteBinding.rvFavorite.setHasFixedSize(true)
    }


}