package com.example.dziennikaktywnosci123.ui.add_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
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
            if(trans != null) {
                mainVm.insertTransaction(trans)
                (requireActivity() as MainActivity).setBottomNavVisibility(true)
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Pomyślnie dodano transakcje", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Błąd dodawania transakcji", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backIv.setOnClickListener {
            (requireActivity() as MainActivity).setBottomNavVisibility(true)
            findNavController().popBackStack()
        }
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val newDatePick = TransactionDatePicker { day, month, year ->

            val dayPlaceholder = if(day < 10) "0$day" else "$day"
            binding.dayTv.text = dayPlaceholder // visibility user

            val monthPlaceholder = if(month + 1 < 10) "0${ month + 1 } " else "${ month + 1}"
            binding.monthTv.text = monthPlaceholder // add +1 to visibility user
            binding.yearTv.text = year.toString()

            val date = Calendar.getInstance()
            date.set(year, month , day)
            viewModel.date = date.timeInMillis
        }
        newDatePick.show(parentFragmentManager, "DatePicker")
    }

    private fun createTransaction(): Transaction? {

        val checkId = binding.typeRg.checkedRadioButtonId
        if (checkId == -1 ) {
            Toast.makeText(requireContext(), "Wybierz rodzaj transakcji", Toast.LENGTH_SHORT).show()
            return null
            }


        val type = when (checkId) {
            binding.incomeRb.id -> TransactionType.PRZYCHÓD
            binding.outcomeRb.id -> TransactionType.WYDATEK
            else -> {
                Toast.makeText(requireContext(), "Wybierz rodzaj transakcji", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        val category = when (binding.categorySpinner.selectedItem.toString()) {
            "JEDZENIE" -> TransactionCategory.JEDZENIE
            "MIESZKANIE" -> TransactionCategory.MIESZKANIE
            "RACHUNKI" -> TransactionCategory.RACHUNKI
            "INTERNET" -> TransactionCategory.INTERNET
            else -> TransactionCategory.INNE
        }

        val amountString = binding.amountEt.text.toString()
        val amount = amountString.toFloatOrNull()
        if (amount == null) {
            binding.amountEt.error = "Podaj liczbe"
            Toast.makeText(requireContext(), "Podaj liczbe", Toast.LENGTH_SHORT).show()
            return null
        }
        val desc = binding.descEt.text.toString()
        val userId = mainVm.getLoggedInUserId() ?: 0

        return Transaction(0, viewModel.date, amount.toFloat(), desc, type, category, userId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
