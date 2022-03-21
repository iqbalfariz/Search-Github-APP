package com.izo.apigithubuserapp

import com.izo.apigithubuserapp.adapter.FollowAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.databinding.FragmentFollowersBinding
import com.izo.apigithubuserapp.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel by viewModels<FollowersViewModel>()

    companion object {
        private const val TAG = "FollowersFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val username = args?.getString(DetailActivity.DATA)
        Log.e(TAG, "username follower: ${username}")

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        // panggil view model
        followersViewModel.findFollowers(username)

        // observe list followers
        followersViewModel.listFollowers.observe(requireActivity()){ items ->
            setRecyclerView(items)
        }

        followersViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }
    }



    private fun setRecyclerView(items: List<ItemsItem>) {
        val listFollowers = ArrayList<ItemsItem>()
        listFollowers.addAll(items)
        val adapter = FollowAdapter(listFollowers)
        binding.rvFollowers.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}