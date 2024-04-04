package com.example.thenest_maintrial.createaccount

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.VerifyPopup
import com.example.thenest_maintrial.databinding.AccCreationBinding
import com.example.thenest_maintrial.model.User
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import com.example.thenest_maintrial.utils.validatePassword
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private lateinit var binding: AccCreationBinding
    private val viewModel: CreateAccountViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog
    private val popup = VerifyPopup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccCreationBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            continueCaBtn.setOnClickListener {

                if (validateForm()) {


//                    popup.createVerifyPopup(context)

                    val firstName = (binding.fName.text).toString()
                    val lastName = binding.lName.text.toString()
                    val idNo = binding.idNo.text.toString()
                    val email = binding.emailInput.text.toString()
                    val phoneNo = binding.phoneNo.text.toString()
                    val confirmPassword = binding.passwordConInput.text.toString()
                    val user = User(
                        fName = firstName,
                        lName = lastName,
                        idNumber = idNo,
                        email = email,
                        phoneNo = phoneNo,
                        password = confirmPassword,
                    )


//                    Handler().postDelayed({
//                        popup.dialog.dismiss()
//                        popup.timeCountdown.cancel()
//                        //TODO change back to actually verifying number before moving on
//                    }, 6000)

//                    val phoneNumber = binding.phoneNo.text.toString()
//                    var phoneStart = phoneNumber?.subSequence(0, 4)
//                    var phoneEnd = phoneNumber?.subSequence(7, 9)
//                    popup.numberText.text =
//                        "We’ve sent a verification code to +254${phoneStart}***${phoneEnd}"
//                    popup.timeCountdown.start()

                   saveUserDetails(user)
                }

            }

            backCaBtn.setOnClickListener {
                findNavController().navigateUp()

            }
        }

    }

    private fun saveUserDetails(user: User) {
        viewModel.saveUserDetails(user).observe(viewLifecycleOwner) {
            binding.apply {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            showToast(resource.message.toString())
                            println("resource.data: $resource")
                            val action =
                                CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginFrag()
                            findNavController().navigate(action)

                            loadingDialog.dismiss()
                        }

                        Status.ERROR -> {
                            showToast(resource.message.toString())
                            println("resource.error: $resource")
                            loadingDialog.dismiss()
                        }

                        Status.LOADING -> {
                            loadingDialog.show()
                        }
                    }
                }
            }
        }
    }

    private fun bindData() {
        //todo improve
    }

    private fun validateForm(): Boolean {
        val firstName = (binding.fName.text).toString()
        val lastName = binding.lName.text.toString()
        val idNo = binding.idNo.text.toString()
        val email = binding.emailInput.text.toString()
        val phoneNo = binding.phoneNo.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.passwordConInput.text.toString()

        binding.apply {
            hideErrorMessage()
            when {
                firstName.isEmpty() -> {
                    fnameInputLayout.isErrorEnabled = true
                    fnameInputLayout.error = "Please input first name"
                    return false
                }

                firstName.length < 3 -> {
                    fnameInputLayout.isErrorEnabled = true
                    fnameInputLayout.error = "First name should be at least 3 characters"
                    return false
                }

                lastName.isEmpty() -> {
                    lnameInputLayout.isErrorEnabled = true
                    lnameInputLayout.error = "Please input last name"
                    return false
                }

                lastName.length < 2 -> {
                    lnameInputLayout.isErrorEnabled = true
                    lnameInputLayout.error = "Last name should be at least 2 characters"
                    return false
                }

                idNo.isEmpty() -> {
                    idnoInputLayout.isErrorEnabled = true
                    idnoInputLayout.error = "Please input ID number"
                    return false
                }

                idNo.length < 7 -> {
                    idnoInputLayout.isErrorEnabled = true
                    idnoInputLayout.error = "ID number should be at least 7 characters"
                    return false
                }

                email.isEmpty() -> {
                    emailInputLayout.isErrorEnabled = true
                    emailInputLayout.error = "Please input email address"
                    return false
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInputLayout.isErrorEnabled = true
                    emailInputLayout.error = "Please input a valid email address"
                    return false
                }

                phoneNo.isEmpty() -> {
                    phoneNoLayout.isErrorEnabled = true
                    accLl.background = null
                    phoneNoLayout.error = "Please input phone number"
                    return false
                }

                phoneNo.length < 9 -> {
                    phoneNoLayout.isErrorEnabled = true
                    accLl.background = null
                    phoneNoLayout.error = "Number should be 9 numbers"
                    return false
                }
                //TODO figure this out still proceeds despite error
                password.isEmpty() -> {
                    passwordInputLayout.isErrorEnabled = true
                    passwordInputLayout.error = "Please input password"
                    return false
                }

                !validatePassword(password) -> {
                    passwordInputLayout.isErrorEnabled = true
                    passwordInputLayout.error =
                        "Password should contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number and 1 special character and no spaces"
                    return false
                }

                confirmPassword != password -> {
                    passwordConInputLayout.isErrorEnabled = true
                    passwordConInputLayout.error = "Passwords do not match"
                    return false
                }

                else -> return true
            }
        }
    }

    private fun hideErrorMessage() {
        binding.apply {
            fnameInputLayout.isErrorEnabled = false
            lnameInputLayout.isErrorEnabled = false
            idnoInputLayout.isErrorEnabled = false
            emailInputLayout.isErrorEnabled = false
            phoneNoLayout.isErrorEnabled = false
            passwordInputLayout.isErrorEnabled = false
            passwordConInputLayout.isErrorEnabled = false
        }
    }
}