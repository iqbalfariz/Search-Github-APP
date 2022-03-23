package com.izo.apigithubuserapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.adapter.FollowAdapter
import com.izo.apigithubuserapp.databinding.FragmentFollowingBinding
import com.izo.apigithubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<FollowingViewModel>()

    companion object {
        private const val TAG = "FollowingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data username dari detail
        val args = arguments
        val username = args?.getString(DetailActivity.DATA)
        Log.e(TAG, "username following: ${username}")

        // Mengambil data api
        if (savedInstanceState == null) {
            followingViewModel.findFollowing(username)
            Log.e(TAG, "Get Following")
        }

        // Observe list following
        followingViewModel.listFollowing.observe(requireActivity()) { items ->
            setRecyclerView(items)
        }

        followingViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }


    private fun setRecyclerView(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        val listFollowing = ArrayList<ItemsItem>()
        listFollowing.addAll(items)
        val adapter = FollowAdapter(listFollowing)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}