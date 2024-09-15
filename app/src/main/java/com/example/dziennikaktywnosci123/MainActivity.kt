package com.example.dziennikaktywnosci123

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.dziennikaktywnosci123.data.models.Transaction
import com.example.dziennikaktywnosci123.data.models.TransactionCategory
import com.example.dziennikaktywnosci123.data.models.TransactionType
import com.example.dziennikaktywnosci123.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainVm by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavVisibility(mainVm.isBottomNavVisibile)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        mainVm.insertTransaction(createTransaction())


    }

    fun setBottomNavVisibility(bool: Boolean) {

        mainVm.isBottomNavVisibile = bool

        val isVisible = when (bool) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        binding.bottomNavigationView.visibility = isVisible
        binding.bottomNavigationView.postInvalidate()
    }

    private fun createTransaction() =
        Transaction(0, 1L, 69f, "XD", TransactionType.INCOME, TransactionCategory.CHEMIA)

}