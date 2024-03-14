package com.example.thenest_maintrial.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.databinding.LoginBinding

class LoginFrag : Fragment() {
    private lateinit var binding: LoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            continueSqBtn.setOnClickListener {
                val action = LoginFragDirections.actionLoginFragToDashboardFragment()
                findNavController().navigate(action)
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


}
