package com.izo.apigithubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        activityMainBinding.rvUser.addItemDecoration(itemDecoration)



        activityMainBinding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // Jika text di submit
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                activityMainBinding.svUser.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        // observe list data
        mainViewModel.listData.observe(this@MainActivity){ items ->
            setData(items)
        }

        // observe loading progress bar
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }



    private fun setData(items: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()
        listUser.addAll(items)
        val adapter = UserAdapter(listUser)
        activityMainBinding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data.login)
                startActivity(intentToDetail)
//                Toast.makeText(this@MainActivity, "${data.login}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun showLoading(isLoading: Boolean){
        activityMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}