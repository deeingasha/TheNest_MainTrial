package com.example.thenest_maintrial.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thenest_maintrial.databinding.ForgotPinBinding

class ForgotPinFrag : Fragment() {
    private lateinit var binding: ForgotPinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ForgotPinBinding.inflate(inflater, container, false)
        return binding.root
    }


}