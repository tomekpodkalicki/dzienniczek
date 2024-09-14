package com.example.dziennikaktywnosci123.ui.income_fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.databinding.FragmentIncomeBinding

class IncomeFragment : Fragment() {

    private val viewModel by viewModels<IncomeViewModel>()
        private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeBinding.inflate(layoutInflater, container, false)
            return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}