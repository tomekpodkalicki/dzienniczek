package com.example.dziennikaktywnosci123.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.TransactionType
import com.example.dziennikaktywnosci123.databinding.TransactionRowBinding
import java.text.SimpleDateFormat
import java.util.Date

class TransactionsAdapter(private val transactions: List<Transaction>, private val onClick: (Transaction, Int) -> Unit )
: RecyclerView.Adapter<TransactionsAdapter.TransactionVewHolder>() {

    inner class TransactionVewHolder(binding: TransactionRowBinding):
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {

                    onClick(transactions[adapterPosition], adapterPosition)
                }
            }


        val price = binding.priceTv
        val category = binding.categoryTv
        val date = binding.dateTv
        val type = binding.typeTv
        val imageView = binding.imageView



        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TransactionVewHolder {
        return TransactionVewHolder(
            TransactionRowBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionVewHolder, position: Int) {
        bindData(holder, position)

    }

    @SuppressLint("SimpleDateFormat")
    private fun bindData(holder: TransactionVewHolder, position: Int) {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(transactions[position].date)
        val datePlaceHolder = sdf.format(date)

        val typeIcon = when(transactions[position].type) {
            TransactionType.INCOME -> R.drawable.wallet_add
            TransactionType.OUTCOME -> R.drawable.wallet__remove
        }

        holder.price.text = transactions[position].price.toString()
        holder.category.text = transactions[position].category.name
        holder.date.text = datePlaceHolder
        holder.type.text = transactions[position].type.name
        holder.imageView.setImageResource(typeIcon)

    }
}