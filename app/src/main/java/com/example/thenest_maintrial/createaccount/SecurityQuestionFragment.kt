package com.example.thenest_maintrial.createaccount


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.databinding.SecurityQuestionsBinding

class SecurityQuestionFragment : Fragment() {
    private lateinit var binding: SecurityQuestionsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SecurityQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpQuestions()

        binding.continueSqBtn.setOnClickListener {

            if (validateForm()) {

                hideErrorMessage()


                val action =
                    SecurityQuestionFragmentDirections.actionSecurityQuestionFragmentToLoginFrag()
                findNavController().navigate(action)


            }
        }
        binding.backSqBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpQuestions() {
        binding.apply {
            aptNameInput.requestFocus()
            val items = listOf(
                "The Nest",
                "New build",
            )
            val adapter =
                ArrayAdapter(requireContext(), R.layout.list_security_questions_layout, items)
            (aptNameLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun validateForm(): Boolean {
        binding.apply {
            return when {
                aptNameInput.text.isNullOrEmpty() -> {
                    aptNameLayout.isErrorEnabled = true
                    aptNameLayout.error = "Ple"
                    false
                }

                unitNoInput.text.isNullOrEmpty() -> {
                    unitNoLayout.isErrorEnabled = true
                    unitNoLayout.error = getString(R.string.please_input_answer)
                    false
                }

                else -> true
            }
        }
    }

    private fun hideErrorMessage() {
        binding.aptNameLayout.error = null
        binding.unitNoLayout.error = null
        binding.startDateLayout.error = null
    }

}
//TODO still carry forward name and ID

