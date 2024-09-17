package com.example.dziennikaktywnosci123.ui.outcome_fragment

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.databinding.FragmentOutcomeBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class OutcomeFragment : Fragment() {

    private val viewModel by viewModels<OutcomeViewModel>()
    private var _binding: FragmentOutcomeBinding? = null
    private val mainVm by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.outcomePieChart.apply {

            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(15f)
            setEntryLabelColor(Color.WHITE)
            centerText = "Wydatki"
            setCenterTextSize(24f)
            description.isEnabled = false
            setTransparentCircleAlpha(50)

            setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val value = e?.y?.toString() ?: "0"
                    Log.d("OutcomeFragment", "Selected value: $value")
                    binding.outcomePieChart.centerText = "$value PLN"
                    binding.outcomePieChart.invalidate()
                }

                override fun onNothingSelected() {
                    binding.outcomePieChart.centerText = "Wydatki"
                    binding.outcomePieChart.invalidate()
                }
            })

        }

        mainVm.getSumOfOutcomeGroupByCategory().observe(viewLifecycleOwner) { transactions ->

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
            pieData.setValueFormatter(PercentFormatter(binding.outcomePieChart))
            pieData.setValueTextSize(15f)
            pieData.setValueTextColor(Color.WHITE)

            binding.outcomePieChart.legend.isEnabled = false
            binding.outcomePieChart.data = pieData
            binding.outcomePieChart.animateY(500, Easing.EaseInOutQuad)

            binding.outcomePieChart.invalidate()

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}