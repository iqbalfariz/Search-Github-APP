package com.izo.apigithubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.adapter.FollowAdapter
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.databinding.FragmentFollowersBinding
import com.izo.apigithubuserapp.viewmodel.FollowersViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
//    private val followersViewModel by viewModels<FollowersViewModel>()

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

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val followersViewModel: FollowersViewModel by viewModels {
            factory
        }

        // Mengambil data username dari detail
        val args = arguments
        val username = args?.getString(DetailActivity.DATA)
        Log.e(TAG, "username follower: ${username}")

        followersViewModel.getListFollowers(username).observe(viewLifecycleOwner) {result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setRecyclerView(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            requireActivity(),
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }


    private fun setRecyclerView(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        val listFollowers = ArrayList<ItemsItem>()
        listFollowers.addAll(items)
        val adapter = FollowAdapter(listFollowers)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}