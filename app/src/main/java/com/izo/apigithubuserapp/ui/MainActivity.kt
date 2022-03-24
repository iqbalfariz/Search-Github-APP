package com.izo.apigithubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.adapter.UserAdapter
import com.izo.apigithubuserapp.databinding.ActivityMainBinding
import com.izo.apigithubuserapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Mengatur search view
        activityMainBinding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Jika text di submit
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                activityMainBinding.svUser.clearFocus()
                return true
            }

            // jika text berubah
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        // observe list data
        mainViewModel.listData.observe(this@MainActivity) { items ->
            if (items.size == 0) {
                Toast.makeText(this@MainActivity, "Error : Data tidak ada", Toast.LENGTH_LONG)
                    .show()
            } else {
                setData(items)
            }
        }

        // observe loading progress bar
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setData(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        activityMainBinding.rvUser.addItemDecoration(itemDecoration)

        val listUser = ArrayList<ItemsItem>()
        listUser.addAll(items)
        val adapter = UserAdapter(listUser)
        activityMainBinding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data.login)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}