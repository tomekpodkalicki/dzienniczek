package com.example.dziennikaktywnosci123.ui.data_picker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TransactionDatePicker(private val onDateSetListener:(Int, Int, Int) -> Unit)
        :DialogFragment()
        , DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireContext(), this, year, month, day)
        }

            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                onDateSetListener(dayOfMonth, month, year)
            }
}