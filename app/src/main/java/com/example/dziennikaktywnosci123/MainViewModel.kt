package com.example.dziennikaktywnosci123

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.dziennikaktywnosci123.data.TransactionsRepository
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.User
import com.example.dziennikaktywnosci123.data.room.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var isBottomNavVisibile = true
    private var selectedTransaction: Transaction? = null
    private val repo = TransactionsRepository(app)
    private val userRepo = UserRepository(app)
    private var loggedInUserId: Int? = null

    fun insertTransaction(transaction: Transaction) {
        Log.d("MainViewModel", "Inserting transaction: $transaction")
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transactions: List<Transaction>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTransaction(transactions)
        }
    }

    fun getAllTransactions(): LiveData<List<Transaction>> {
        return repo.getAllTransactions().asLiveData(viewModelScope.coroutineContext)
    }

    fun getAllIncomes() = repo.getAllIncomes().asLiveData(viewModelScope.coroutineContext)
    fun getAllOutcomes() = repo.getAllOutcomes().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfIncomeGroupByCategory() = repo.getSumOfIncomeGroupByCategory().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfOutcomeGroupByCategory() = repo.getSumOfOutcomeGroupByCategory().asLiveData(viewModelScope.coroutineContext)

    fun selectTransaction(transaction: Transaction) {
        selectedTransaction = transaction
    }

    fun unSelectTransaction() {
        selectedTransaction = null
    }

    fun getSelectedTransaction() = selectedTransaction

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.registerUser(user)
        }
    }

    fun loginUser(username: String, password: String) = liveData(Dispatchers.IO) {
        val user = userRepo.loginUser(username, password)
        user?.let {
            setLoggedInUserId(it.userId) // Ensure the user ID is set here
            emit(it)
        } ?: emit(null)
    }

    fun setLoggedInUserId(id: Int) {
        loggedInUserId = id
        Log.d("MainViewModel", "User logged in with ID: $loggedInUserId") // Debug log
    }

    fun getLoggedInUserId() = loggedInUserId

    fun logoutUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("user_id").apply()
        loggedInUserId = null
    }

    fun getUserTransactions() = liveData(viewModelScope.coroutineContext) {
        loggedInUserId?.let { userId ->
            emit(repo.getTransactionsByUserId(userId))
        } ?: emit(emptyList<Transaction>())
    }
}
