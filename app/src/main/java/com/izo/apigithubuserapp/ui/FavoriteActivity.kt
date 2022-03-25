package com.izo.apigithubuserapp.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.R
import com.izo.apigithubuserapp.adapter.FavoriteAdapter
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.databinding.ActivityFavoriteBinding
import com.izo.apigithubuserapp.ui.DetailActivity.Companion.DATA
import com.izo.apigithubuserapp.viewmodel.DetailViewModel
import com.izo.apigithubuserapp.viewmodel.FavoriteViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteEntity) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DATA, data.username)
                startActivity(intentToDetail)
            }

            override fun onDeleteClicked(data: FavoriteEntity) {
                val builder = AlertDialog.Builder(this@FavoriteActivity)
                builder.setMessage("Apakah anda yakin ingin menghapus ?")
                    .setPositiveButton("Ya", DialogInterface.OnClickListener{dialogInterface, i ->
                        favoriteViewModel.deleteData(data)
                    })
                    .setNegativeButton("Tidak", DialogInterface.OnClickListener {dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .show()
//                favoriteViewModel.deleteData(data)
            }

        })

//        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
//            override fun onItemClicked(data: FavoriteEntity) {
//                favoriteViewModel.deleteData(data)
//            }
//
//        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}