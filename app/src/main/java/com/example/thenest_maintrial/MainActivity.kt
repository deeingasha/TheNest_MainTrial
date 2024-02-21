package com.example.thenest_maintrial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    var contentHasLoaded = false
    companion object {

        fun newIntent(context: Context): Intent {

            val intent = Intent(context, MainActivity::class.java)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startLoadingContent()

        setUpSplashScreen(splashScreen)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    private fun startLoadingContent(){
        Timer().schedule(2000){
            contentHasLoaded = true
        }
    }

    private fun setUpSplashScreen(splashScreen: SplashScreen){

        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener (
            object : ViewTreeObserver.OnPreDrawListener {

                override fun onPreDraw(): Boolean {

                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}