package com.example.thenest_maintrial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.thenest_maintrial.databinding.SplashActivityBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity: AppCompatActivity() {

    private lateinit var binding: SplashActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            delay(1500)
            Intent(this@SplashActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}