package com.example.dziennikaktywnosci123.ui.income_fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dziennikaktywnosci123.R

class IncomeFragment : Fragment() {

    private val viewModel by viewModels<IncomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_income, container, false)
    }
}