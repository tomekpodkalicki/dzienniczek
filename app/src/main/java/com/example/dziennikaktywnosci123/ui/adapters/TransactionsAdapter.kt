package com.example.dziennikaktywnosci123.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.TransactionType
import com.example.dziennikaktywnosci123.databinding.TransactionRowBinding
import java.text.SimpleDateFormat
import java.util.Date

class TransactionsAdapter(
    private val transactions: List<Transaction>,
    private val onClick: (Transaction, Int) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(binding: TransactionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val price = binding.priceTv
        val category = binding.categoryTv
        val date = binding.dateTv
        val type = binding.typeTv
        val imageView = binding.imageView

        init {
            binding.root.setOnClickListener {
                onClick(transactions[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = TransactionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        // Bind data to the ViewHolder
        bindData(holder, position)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    @SuppressLint("SimpleDateFormat")
    private fun bindData(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        // Ensure the date is valid
        val date = if (transaction.date > 0) Date(transaction.date) else Date()
        val dateFormatted = sdf.format(date)

        val typeIcon = when (transaction.type) {
            TransactionType.PRZYCHOD -> R.drawable.wallet_add
            TransactionType.WYDATEK -> R.drawable.wallet__remove
        }

        holder.price.text = transaction.price.toString()
        holder.category.text = transaction.category.name
        holder.date.text = dateFormatted
        holder.type.text = transaction.type.name
        holder.imageView.setImageResource(typeIcon)

        // Logging for debugging purposes
        Log.d("TransactionsAdapter", "Binding data for transaction at position $position: $transaction")
    }
}
