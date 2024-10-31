package com.example.dziennikaktywnosci123.ui.login_fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dziennikaktywnosci123.MainViewModel
import com.example.dziennikaktywnosci123.R
import com.example.dziennikaktywnosci123.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private val vM: MainViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuVisibility(false)
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            binding.przejscieLogin
                .setOnClickListener {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }

            vM.loginUser(username, password).observe(viewLifecycleOwner) { user ->
                if(user != null) {
                    Toast.makeText(context, "Logowanie udane", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_transactionsFragment)
                    setMenuVisibility(true)
                } else {
                    Toast.makeText(context, "Błąd logowania", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}