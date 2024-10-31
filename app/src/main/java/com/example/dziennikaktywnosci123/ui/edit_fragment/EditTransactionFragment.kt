package com.example.dziennikaktywnosci123.ui.edit_fragment

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
import com.example.dziennikaktywnosci123.databinding.FragmentEditTransactionBinding
import com.example.dziennikaktywnosci123.ui.data_picker.TransactionDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar

class EditTransactionFragment : Fragment() {

    private val viewModel by viewModels<EditTransactionViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentEditTransactionBinding? = null
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
        _binding = FragmentEditTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
        setTransactionData(mainVm.getSelectedTransaction()!!)
        setupOnClicks()

        binding.backIv.setOnClickListener {
            (requireActivity() as MainActivity).setBottomNavVisibility(true)
            findNavController().popBackStack()
        }
    }

    private fun setupOnClicks() {
        binding.calendarIv.setOnClickListener {
            showDatePicker()
        }

        binding.deleteBtn.setOnClickListener {
            mainVm.getSelectedTransaction()?.let { trans ->
                mainVm.deleteTransaction(listOf(trans))
                mainVm.unSelectTransaction()
            }
            requireActivity().onBackPressedDispatcher.onBackPressed()
            Toast.makeText(requireContext(),"Pomyślnie usunieto transakcje!", Toast.LENGTH_SHORT).show()
        }

        binding.saveBtn.setOnClickListener {
            updateTransaction()
        }
    }

    private fun updateTransaction() {
        val updateTrans = createTransaction()
        if (updateTrans != null) {
            mainVm.updateTransaction(updateTrans)
            requireActivity().onBackPressedDispatcher.onBackPressed()
            Toast.makeText(requireContext(), "Pomyślnie zaktualizowano transakcje", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Błąd aktualizacji transakcji", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker() {
        val newDatePick = TransactionDatePicker { day, month, year ->
            val dayPlaceholder = if (day < 10) "0$day" else "$day"
            binding.dayTv.text = dayPlaceholder

            val monthPlaceholder = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
            binding.monthTv.text = monthPlaceholder
            binding.yearTv.text = year.toString()

            val date = Calendar.getInstance()
            date.set(year, month, day)
            viewModel.date = date.timeInMillis
        }
        newDatePick.show(parentFragmentManager, "DatePicker")
    }

    private fun createTransaction(): Transaction? {
        val selectedTransaction = mainVm.getSelectedTransaction()
        if (selectedTransaction == null) {
            return null
        }

        val type = when (binding.typeRg.checkedRadioButtonId) {
            binding.incomeRb.id -> TransactionType.PRZYCHÓD
            else -> TransactionType.WYDATEK
        }
        val category = when (binding.categorySpinner.selectedItem.toString()) {
            "JEDZENIE" -> TransactionCategory.JEDZENIE
            "MIESZKANIE" -> TransactionCategory.MIESZKANIE
            "RACHUNKI" -> TransactionCategory.RACHUNKI
            "INTERNET" -> TransactionCategory.INTERNET
            else -> TransactionCategory.INNE
        }

        // Handle amount safely
        val amountString = binding.amountEt.text.toString()
        val amount = amountString.toFloatOrNull()
        if (amount == null) {
            binding.amountEt.error = "Podaj liczbę"
            Toast.makeText(requireContext(), "Podaj liczbę", Toast.LENGTH_SHORT).show()
            return null
        }

        val desc = binding.descEt.text.toString()
        val userId = mainVm.getLoggedInUserId() ?: 0

        return Transaction(selectedTransaction.uid, viewModel.date, amount, desc, type, category, userId)
    }

    private fun setTransactionData(transaction: Transaction) {
        setCurrentAmount(transaction.price)
        setCurrentType(transaction.type)
        setCurrentCategory(transaction.category)
        setCurrentDate(transaction.date)
        setCurrentDescription(transaction.desc)
    }

    private fun setCurrentDescription(desc: String) {
        binding.descEt.setText(desc)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDate(date: Long) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val datePlaceHolder = sdf.format(date)
        val list = datePlaceHolder.split("/")

        if (list.size == 3) {
            binding.dayTv.text = list[0]
            binding.monthTv.text = list[1]
            binding.yearTv.text = list[2]
        }
    }

    private fun setCurrentCategory(category: TransactionCategory) {
        val adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            TransactionCategory.values().map { enum -> enum.name }
        )
        binding.categorySpinner.adapter = adapter
        val position = adapter.getPosition(category.name)
        binding.categorySpinner.setSelection(position)
    }

    private fun setCurrentType(type: TransactionType) {
        val checkId = when (type) {
            TransactionType.PRZYCHÓD -> binding.incomeRb.id
            TransactionType.WYDATEK -> binding.outcomeRb.id
        }
        binding.typeRg.check(checkId)
    }

    private fun setCurrentAmount(price: Float) {
        binding.amountEt.setText(price.toString()) // Display the current price in the EditText
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
}
