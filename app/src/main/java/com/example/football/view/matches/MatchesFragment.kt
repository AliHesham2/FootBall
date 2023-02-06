package com.example.football.view.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.football.base.BaseFragment
import com.example.football.databinding.FragmentMatchesBinding
import com.example.football.view.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment : BaseFragment() {
    private lateinit var binding:FragmentMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        initialization(inflater)

        setupAdapter()

        initObservers()

        return binding.root
    }

    private fun initialization(inflater: LayoutInflater) {
        binding = FragmentMatchesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun setupAdapter() {
        binding.listContainer.adapter = MatchesAdapter(MatchesAdapter.OnClickListener{/* Handle Clicks */})
    }

    private fun initObservers() {
        viewModel.offlineMovies.observe(viewLifecycleOwner){
            if (viewModel.askForOfflineData.value == true && viewModel.data.value == null){
                viewModel.loadOfflineData()
            }
        }

    }


}