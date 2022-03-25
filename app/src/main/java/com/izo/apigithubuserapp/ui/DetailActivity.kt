package com.izo.apigithubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.izo.apigithubuserapp.R
import com.izo.apigithubuserapp.adapter.SectionsPagerAdapter
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.databinding.ActivityDetailBinding
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import com.izo.apigithubuserapp.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
//    private val detailViewModel by detailViewModels<DetailViewModel>()
    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    val detailViewModel: DetailViewModel by viewModels {
        factory
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private const val TAG = "DetailActivity"
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val username = intent.getStringExtra(DATA)

        // mengirimkan data username ke fragment
        val bundle = Bundle()
        bundle.putString(DATA, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        // Mengambil data api
            detailViewModel.getDetailUser(username).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            setRecapLayout(result.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                this,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }



    }

    @SuppressLint("SetTextI18n")
    private fun setRecapLayout(items: DetailUserResponse) {
        // ambil gambar avatar
        Glide.with(this)
            .load(items.avatarUrl)
            .circleCrop()
            .into(detailBinding.ivAvatar)

        detailBinding.tvUsername.text = items.login
        detailBinding.tvName.text = items.name
        detailBinding.tvRepository.text = "Repository : ${items.publicRepos}"
        detailBinding.tvCompany.text = items.company
        detailBinding.tvLocation.text = items.location
        detailBinding.tvTotalFollowers.text = "Followers : ${items.followers}"
        detailBinding.tvTotalFollowing.text = "Following : ${items.following}"

        val favoriteUser = FavoriteEntity(items.id, items.login, items.avatarUrl, items.url)
        detailBinding.button.setOnClickListener {
            detailViewModel.insertData(favoriteUser)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}