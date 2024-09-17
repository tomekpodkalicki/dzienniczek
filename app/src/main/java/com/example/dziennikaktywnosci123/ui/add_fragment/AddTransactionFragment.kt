package com.example.dziennikaktywnosci123.ui.add_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dziennikaktywnosci123.MainActivity
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.TransactionCategory
import com.example.dziennikaktywnosci123.data.models.TransactionType
import com.example.dziennikaktywnosci123.databinding.FragmentAddTransactionBinding
import com.example.dziennikaktywnosci123.ui.data_picker.TransactionDatePicker
import java.util.Calendar

class AddTransactionFragment : Fragment() {

    private val viewModel by viewModels<AddTransactionViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (requireActivity() as MainActivity).setBottomNavVisibility(true)
            isEnabled = false
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleOnBackPressed()

        val adapter = ArrayAdapter(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            TransactionCategory.values().map { enum -> enum.name }
        )

        binding.categorySpinner.adapter = adapter

        binding.calendarIv.setOnClickListener {
            showDatePicker()
        }
        binding.saveBtn.setOnClickListener {
            val trans = createTransaction()
            mainVm.insertTransaction(trans)
            findNavController().popBackStack()
        }
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun showDatePicker() {
        val newDatePick = TransactionDatePicker { day, month, year ->
            binding.dayTv.text = day.toString()
            binding.monthTv.text = month.toString()
            binding.yearTv.text = year.toString()

            val date = Calendar.getInstance()
            date.set(year, month , day)
            viewModel.date = date.timeInMillis
        }
        newDatePick.show(parentFragmentManager, "DatePicker")
    }

    private fun createTransaction(): Transaction {
        val type = when (binding.typeRg.checkedRadioButtonId) {
            binding.incomeRb.id -> TransactionType.PRZYCHOD
            else -> TransactionType.WYDATEK
        }
        val category = when (binding.categorySpinner.selectedItem.toString()) {
            "JEDZENIE" -> TransactionCategory.JEDZENIE
            "MIESZKANIE" -> TransactionCategory.MIESZKANIE
            "RACHUNKI" -> TransactionCategory.RACHUNKI
            "INTERNET" -> TransactionCategory.INTERNET
            else -> TransactionCategory.INNE
        }

        val amount = binding.amountEt.text.toString()
        val desc = binding.descEt.text.toString()

        return Transaction(0, viewModel.date, amount.toFloat(), desc, type, category)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
