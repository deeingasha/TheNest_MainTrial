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
import com.example.thenest_maintrial.data.remote.LoginDetails
import com.example.thenest_maintrial.databinding.ForgotPinBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import com.example.thenest_maintrial.utils.validatePassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPinFrag : Fragment() {
    private lateinit var binding: ForgotPinBinding
    private val viewModel: ForgotPinViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ForgotPinBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            continueFpBtn.setOnClickListener {
                if (validateForm()) {
                    val email = emailFPInput.text.toString()
                    val password = passwordFPInput.text.toString()
                    val loginDetails = LoginDetails(email,password)
                    resetPassword(loginDetails)
                }
            }
            backSqBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            otherMethodFpBtn.setOnClickListener{
                showToast("Options coming soon")
            }
        }
    }

    private fun resetPassword(loginDetails: LoginDetails) {
        viewModel.resetPassword(loginDetails).observe(viewLifecycleOwner) {
           binding.apply {
               it.let { resource ->
                   when (resource.status) {
                       Status.SUCCESS -> {
                           loadingDialog.dismiss()
                           showToast(resource.message.toString())

                           CoroutineScope(Dispatchers.Main).launch {
                               delay(2000)
                               val action = ForgotPinFragDirections.actionForgotPinFragmentToLoginFrag()
                               findNavController().navigate(action)
                           }


                       }
                       Status.LOADING -> {
                            loadingDialog.show()
                          }
                       Status.ERROR -> {
                           loadingDialog.dismiss()
                           showToast(resource.message.toString())
                           println("response from server: $resource")
                       }
                   }
               }
           }
        }
    }
    private fun validateForm(): Boolean {
        val email = binding.emailFPInput.text.toString()
        val password = binding.passwordFPInput.text.toString()
        val confirmPassword = binding.passwordFPConInput.text.toString()

        binding.apply {
            hideErrorMessage()
            when {
                email.isEmpty() -> {
                    emailFPInputLayout.isErrorEnabled = true
                    emailFPInputLayout.error = "Email is required"
                    emailFPInput.requestFocus()
                    return false
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailFPInputLayout.isErrorEnabled = true
                    emailFPInputLayout.error = "Valid email is required"
                    emailFPInput.requestFocus()
                    return false
                }

                password.isEmpty() -> {
                    passwordFPInputLayout.isErrorEnabled = true
                    passwordFPInputLayout.error = "Password is required"
                    passwordFPInput.requestFocus()
                    return false
                }
                !validatePassword(password) -> {
                    passwordFPInputLayout.isErrorEnabled = true
                    passwordFPInputLayout.error =
                        "Password should contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number and 1 special character(@#$%^&+=) and no spaces"
                    passwordFPInput.requestFocus()
                    return false
                }
                confirmPassword != password -> {
                    passwordFPConInputLayout.isErrorEnabled = true
                    passwordFPConInputLayout.error = "Passwords do not match"
                    passwordFPInput.requestFocus()
                    return false
                }

            }
        }
        return true
    }
    private fun hideErrorMessage() {
        binding.apply {
            emailFPInputLayout.isErrorEnabled = false
            passwordFPInputLayout.isErrorEnabled = false
        }
    }



}
