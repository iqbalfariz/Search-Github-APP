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
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.databinding.ActivityMainBinding
import com.izo.apigithubuserapp.viewmodel.MainViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
//    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }


        // Mengatur search view
        activityMainBinding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Jika text di submit
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query).observe(this@MainActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                setData(result.data)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@MainActivity,
                                    "Terjadi kesalahan" + result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                activityMainBinding.svUser.clearFocus()
                return true
            }

            // jika text berubah
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })


    }

    private fun getData(username: String?, mainViewModel: MainViewModel) {
        mainViewModel.findUser(username).observe(this@MainActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setData(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this@MainActivity,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
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