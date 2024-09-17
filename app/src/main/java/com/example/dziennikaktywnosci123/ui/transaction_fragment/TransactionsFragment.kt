package com.example.dziennikaktywnosci123.ui.transaction_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dziennikaktywnosci123.MainActivity
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.databinding.FragmentTransactionsBinding
import com.example.dziennikaktywnosci123.ui.adapters.TransactionsAdapter


class TransactionsFragment : Fragment() {

    private val viewModel by viewModels<TransactionsViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TransactionsAdapter(emptyList()) { transaction, position ->
            Log.d("TEST", "Trans: ${transaction.toString()}")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe transactions
        mainVm.getAllTransactions().observe(viewLifecycleOwner) { transactions ->
            Log.d("TEST", "Transactions received: ${transactions.size}")
            if (transactions.isNotEmpty()) {
                binding.recyclerView.adapter =
                    TransactionsAdapter(transactions) { transaction, position ->
                        mainVm.selectTransaction(transaction)
                        (requireActivity() as MainActivity).setBottomNavVisibility(false)
                        findNavController().navigate(R.id.editTransactionFragment)
                    }
            } else {
                Log.d("TEST", "No transactions found.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
