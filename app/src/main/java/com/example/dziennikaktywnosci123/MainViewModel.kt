package com.example.dziennikaktywnosci123

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dziennikaktywnosci123.data.TransactionsRepository
import com.example.dziennikaktywnosci123.data.models.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var isBottomNavVisibile = true
    private val repo = TransactionsRepository(app)

    fun insertTransaction(transaction: Transaction) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.insertTransaction(transaction)
        }

    fun updateTransaction(transaction: Transaction) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.updateTransaction(transaction)
        }

    fun deleteTransaction(transactions: List<Transaction>) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteTransaction(transactions)
        }

    fun getAllTransactions() = repo.getAllTransactions().asLiveData(viewModelScope.coroutineContext)
    fun getAllIncomes() = repo.getAllIncomes().asLiveData(viewModelScope.coroutineContext)
    fun getAllOutcomes() = repo.getAllOutcomes().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfIncomeGroupByCategory() = repo.getSumOfIncomeGroupByCategory().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfOutcomeGroupByCategory() = repo.getSumOfOutcomeGroupByCategory().asLiveData(viewModelScope.coroutineContext)



}