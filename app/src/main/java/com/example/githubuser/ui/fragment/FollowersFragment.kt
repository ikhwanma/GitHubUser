package com.example.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowAdapter
import com.example.githubuser.dataclass.Users
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewmodel.DetailViewModel

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        val viewModel = ViewModelProvider(requireActivity(),ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        viewModel.listFollowers.observe(requireActivity(),{
            setAdapter(it)
        })
        viewModel.isLoadingFollowing.observe(requireActivity(),{
            setLoading(it)
        })
    }

    private fun setLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setAdapter(it: ArrayList<Users>) {
        val adapter = FollowAdapter(it)
        binding.rvUsers.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}