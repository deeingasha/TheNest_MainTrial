package com.example.thenest_maintrial.createaccount

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.databinding.AccCreationBinding

class CreateAccountFragment: Fragment() {

    private lateinit var binding: AccCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccCreationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            continueCaBtn.setOnClickListener {



                if (validateForm()) {

                    hideErrorMessage()
                    val action =
                        CreateAccountFragmentDirections.actionCreateAccountFragmentToSecurityQuestionFragment(
                            binding.fName.text.toString(),
                            binding.lName.text.toString(),
                            binding.idNo.text.toString(),
                            binding.emailInput.text.toString(),
                            binding.phoneNo.text.toString(),
                        )
                    findNavController().navigate(action)

                }
                //TODO() uncomment validation

            }

            backCaBtn.setOnClickListener {
                findNavController().navigateUp()

            }
        }

    }
    //TODO work on this function


    private fun validateForm():Boolean{
        val firstName = (binding.fName.text).toString()
        val lastName = binding.lName.text.toString()
        val idNo = binding.idNo.text.toString()
        val email = binding.emailInput.text.toString()
        val phoneNo = binding.phoneNo.text.toString()

        binding.apply {
            when {
                firstName.isEmpty() -> {
                    fnameInputLayout.isErrorEnabled = true
                    fnameInputLayout.error = "Please input first name"
                    return false
                }
                lastName.isEmpty() -> {
                    lnameInputLayout.isErrorEnabled = true
                    lnameInputLayout.error = "Please input last name"
                    return false
                }
                idNo.isEmpty() -> {
                    idnoInputLayout.isErrorEnabled = true
                    idnoInputLayout.error = "Please input ID number"
                    return false
                }
                idNo.length <7 -> {
                    idnoInputLayout.isErrorEnabled = true
                    idnoInputLayout.error = "ID number should be at least 7 characters"
                    return false
                }
                email.isEmpty() -> {
                    emailInputLayout.isErrorEnabled = true
                    emailInputLayout.error= "Please input email address"
                    return false
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInputLayout.isErrorEnabled = true
                    emailInputLayout.error= "Please input a valid email address"
                    return false
                }
                phoneNo.isEmpty() -> {
                    phoneNoLayout.isErrorEnabled = true
                    phoneNoLayout.error = "Please input phone number"
                    return false
                }
                phoneNo.length<9 -> {
                    phoneNoLayout.isErrorEnabled = true
                    phoneNoLayout.error = "Phone number should be 9 numbers"
                    return false
                }
                //TODO figure this out still proceeds despite error
                else -> return true
            }
        }
    }

    private fun hideErrorMessage(){
        binding.apply {
            fnameInputLayout.isErrorEnabled = false
            lnameInputLayout.isErrorEnabled = false
            idnoInputLayout.isErrorEnabled = false
            emailInputLayout.isErrorEnabled = false
            phoneNoLayout.isErrorEnabled = false
        }
    }
}