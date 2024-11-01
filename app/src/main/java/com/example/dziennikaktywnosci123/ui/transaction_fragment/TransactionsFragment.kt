package com.example.dziennikaktywnosci123.ui.transaction_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dziennikaktywnosci123.MainActivity
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.databinding.FragmentTransactionsBinding
import com.example.dziennikaktywnosci123.ui.adapters.TransactionsAdapter

class TransactionsFragment : Fragment() {
    val mainVm: MainViewModel by activityViewModels()
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        adapter = TransactionsAdapter(mutableListOf()) { transaction, position ->
            mainVm.selectTransaction(transaction)
            (requireActivity() as MainActivity).setBottomNavVisibility(false)
            findNavController().navigate(R.id.editTransactionFragment)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Set the bottom navigation visibility
        mainVm.isBottomNavVisibile = true
        (activity as MainActivity).setBottomNavVisibility(true)

        val loggedInUserId = mainVm.getLoggedInUserId()
        Log.d("TransactionsFragment", "Logged in user ID: $loggedInUserId")

        if (loggedInUserId == null) {
            Log.d("TransactionsFragment", "No user logged in.")
            return
        }


        // Observe transactions and filter for the logged-in user
        mainVm.getAllTransactions().observe(viewLifecycleOwner) { transactions ->
            Log.d("TransactionsFragment", "Total transactions received: ${transactions.size}")
            transactions.forEach { transaction ->
                Log.d("TransactionsFragment", "Transaction: $transaction")
            }

            val userTransactions = transactions.filter { it.userId == loggedInUserId }

            // Log the filtered transactions for debugging
            Log.d("TransactionsFragment", "Filtered user transactions: $userTransactions")

            if (userTransactions.isNotEmpty()) {
                Log.d("TransactionsFragment", "User transactions count: ${userTransactions.size}")
                adapter.submitList(userTransactions) // Update the adapter's list
            } else {
                Log.d("TransactionsFragment", "No transactions found for user.")
                adapter.submitList(emptyList()) // Clear the list in the adapter if no transactions found
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding reference to avoid memory leaks
    }
}
