package com.example.football.base

import androidx.fragment.app.Fragment
import com.example.football.util.Util
import com.example.football.view.SharedViewModel

open class BaseFragment : Fragment() {
    open lateinit var viewModel : SharedViewModel


    override fun onStart() {
        initObserver()
        super.onStart()
    }


    private fun initObserver() {
        viewModel.showErrorMessage.observe(viewLifecycleOwner){
            if (it != null) {
                Util.alertMsg(requireView(), msg = it)
                viewModel.resetErrorMsg()
            }
        }

        viewModel.showToast.observe(viewLifecycleOwner){
            if (it != null) {
                Util.toastMsg(requireContext(), msg = it)
                viewModel.resetToast()
            }
        }
    }

}