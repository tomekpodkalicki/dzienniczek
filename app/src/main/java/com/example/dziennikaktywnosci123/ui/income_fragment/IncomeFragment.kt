package com.example.dziennikaktywnosci123.ui.income_fragment

import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.incomePieChart.apply {

            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(15f)
            setEntryLabelColor(Color.WHITE)
            centerText = "Przychody"
            setCenterTextSize(24f)
            description.isEnabled = false
            setTransparentCircleAlpha(50)

        }

        mainVm.getSumOfIncomeGroupByCategory().observe(viewLifecycleOwner) { transactions ->

            val entries = ArrayList<PieEntry>()
            for (trans in transactions) {
                val pieEntry = PieEntry(trans.total, trans.category.name.lowercase())
                entries.add(pieEntry)
            }

                    val pieDataSet = PieDataSet(entries, "")
            val colors = listOf(
                Color.parseColor("#94fee1"),
                Color.parseColor("#fe9ca9"),
                Color.parseColor("#2c0470"),
                Color.parseColor("#f03f3e"))

            pieDataSet.colors = colors

            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            pieData.setValueFormatter(PercentFormatter(binding.incomePieChart))
            pieData.setValueTextSize(15f)
            pieData.setValueTextColor(Color.WHITE)

            binding.incomePieChart.legend.isEnabled = false
            binding.incomePieChart.data = pieData
            binding.incomePieChart.animateY(500, Easing.EaseInOutQuad)

            binding.incomePieChart.invalidate()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}