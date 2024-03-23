package com.example.thenest_maintrial.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.SharedPreferenceManager
import com.example.thenest_maintrial.data.remote.LoginDetails
import com.example.thenest_maintrial.databinding.LoginBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFrag : Fragment() {
    private lateinit var binding: LoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(inflater, container, false)
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        loadingDialog = LoadingDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            continueSqBtn.setOnClickListener {
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
                            showToast(resource.message.toString())

                            //store token in shared preference
                            resource.data?.data?.let { token ->
                                sharedPreferenceManager.storeToken(token)
                            }

                            //store role in shared preference
                            resource.data?.role?.let { role ->
                                sharedPreferenceManager.storeRole(role)
                            }
                            val action = LoginFragDirections.actionLoginFragToDashboardFragment()
                            findNavController().navigate(action)

                            loadingDialog.dismiss()

                            println("token: ${sharedPreferenceManager.getToken()}") //todo remove print statement
                            println("role: ${sharedPreferenceManager.getRole()}")
                        }

                        Status.ERROR -> {
                            showToast(resource.message.toString())
                            loadingDialog.dismiss()
                            println("resource.message: $resource")
                        }

                        Status.LOADING -> {
                            loadingDialog.show()
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
