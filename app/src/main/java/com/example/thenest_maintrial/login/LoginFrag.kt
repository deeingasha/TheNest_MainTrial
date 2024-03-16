package com.example.thenest_maintrial.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.data.remote.LoginDetails
import com.example.thenest_maintrial.databinding.LoginBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFrag : Fragment() {
    private lateinit var binding: LoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            continueSqBtn.setOnClickListener {
//                val action = LoginFragDirections.actionLoginFragToDashboardFragment()
//                findNavController().navigate(action)
                if (validateForm()) {
                    val email = emailLoginInput.text.toString()
                    val password = passwordLoginInput.text.toString()
                    val loginDetails = LoginDetails(email,password)
                    loginUser(loginDetails)
                }
            }
            forgotpinBtn.setOnClickListener {
                val action = LoginFragDirections.actionLoginFragToForgotPinFragment()
                findNavController().navigate(action)
            }
            backSqBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun loginUser(loginDetails: LoginDetails) {
        viewModel.loginUser(loginDetails).observe(viewLifecycleOwner) {
            binding.apply {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(context, resource.message.toString(), Toast.LENGTH_SHORT).show()
                            val action = LoginFragDirections.actionLoginFragToDashboardFragment()
                            findNavController().navigate(action)

                        }

                        Status.ERROR -> {
                            showToast(resource.message.toString())
                            println("resource.message: ${resource})")
                        }

                        Status.LOADING -> {
                            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    private fun validateForm(): Boolean {
        val email = binding.emailLoginInput.text.toString()
        val password = binding.passwordLoginInput.text.toString()

        binding.apply {
            hideErrorMessage()
            when {
                email.isEmpty()->{
                   emailLoginInputLayout.isErrorEnabled = true
                   emailLoginInputLayout.error = "Email is required"
                   emailLoginInput.requestFocus()
                    return false
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailLoginInputLayout.isErrorEnabled = true
                    emailLoginInputLayout.error = "Valid email is required"
                    emailLoginInput.requestFocus()
                    return false
                }

                password.isEmpty()-> {
                    passwordLoginInputLayout.isErrorEnabled = true
                    passwordLoginInputLayout.error = "Password is required"
                    passwordLoginInput.requestFocus()
                    return false
                }
            }
        }
        return true
    }
    private fun hideErrorMessage() {
        binding.apply {
            emailLoginInputLayout.isErrorEnabled = false
            passwordLoginInputLayout.isErrorEnabled = false
        }
    }


}
