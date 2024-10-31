package com.example.dziennikaktywnosci123.ui.register_fragment

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
import com.example.dziennikaktywnosci123.data.models.User
import com.example.dziennikaktywnosci123.databinding.FragmentLoginBinding
import com.example.dziennikaktywnosci123.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
private val vM by viewModels<MainViewModel>()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuVisibility(false)

        binding.registerButton.setOnClickListener{
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val newUser = User(username = username, password = password)
            vM.registerUser(newUser)
            Toast.makeText(context, "Rejestracja udana", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}