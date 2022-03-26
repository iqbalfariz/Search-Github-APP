package com.izo.apigithubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.R
import com.izo.apigithubuserapp.adapter.UserAdapter
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.databinding.ActivityMainBinding
import com.izo.apigithubuserapp.viewmodel.MainViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
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

        mainViewModel.getSearch.observe(this) { result ->
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

        // mengatur tema
        mainViewModel.getThemeSetting().observe(this) {isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setData(items: List<ItemsItem>) {
        val mainAdapter = UserAdapter(items)
        activityMainBinding.rvUser.adapter = mainAdapter
        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        activityMainBinding.rvUser.addItemDecoration(itemDecoration)
        activityMainBinding.rvUser.setHasFixedSize(true)

        mainAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data.login)
                startActivity(intentToDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_favorite -> {
                val intentToFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
            }
            R.id.menu_setting_theme -> {
                val intentToSettingTheme = Intent(this, SettingThemeActivity::class.java)
                startActivity(intentToSettingTheme)
            }
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}