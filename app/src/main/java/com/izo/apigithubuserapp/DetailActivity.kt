package com.izo.apigithubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.izo.apigithubuserapp.adapter.SectionsPagerAdapter
import com.izo.apigithubuserapp.api.ApiConfig
import com.izo.apigithubuserapp.databinding.ActivityDetailBinding
import com.izo.apigithubuserapp.response.DetailUserResponse
import com.izo.apigithubuserapp.viewmodel.DetailViewModel
import com.izo.apigithubuserapp.viewmodel.FollowersViewModel
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

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

        detailViewModel.findDetailUser(username)

        // observe detailUser
        detailViewModel.detailUser.observe(this){items ->
            setRecapLayout(items)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun setRecapLayout(items: DetailUserResponse) {
        // ambil gambar avatar
        Glide.with(this)
            .load(items.avatarUrl)
            .circleCrop()
            .into(detailBinding.ivAvatar)

        detailBinding.tvUsername.text = items.login
        detailBinding.tvName.text = items.name
        detailBinding.tvRepository.text = items.publicRepos.toString()
        detailBinding.tvCompany.text = items.company
        detailBinding.tvLocation.text = items.location
    }


    private fun showLoading(isLoading: Boolean){
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}