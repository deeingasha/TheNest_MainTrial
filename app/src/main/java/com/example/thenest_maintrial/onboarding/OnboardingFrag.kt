package com.example.thenest_maintrial.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.databinding.OnboardingFragBinding

class OnboardingFrag: Fragment() {

    private lateinit var binding:OnboardingFragBinding
    private lateinit var onboardingItemAdapter: OnboardingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        binding = OnboardingFragBinding.inflate(inflater, container, false)
        setOnboardingItems()
        setUpIndicators()
        setCurrentIndicator(0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
             showAccLookup()
        }
        binding.GetStartedBtn.setOnClickListener {

            val action = OnboardingFragDirections.actionOnboardingFragToCreateAccountFragment()
            findNavController().navigate(action)
        }
    }

   private fun showAccLookup(){

       val action = OnboardingFragDirections.actionOnboardingFragToAccLookup()
        findNavController().navigate(action)
    }

    private fun setOnboardingItems(){

        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItemModel(
                    onboardingImage = R.drawable.group_1,
                    onboardingText1 = "Welcome to The Nest",
                    onboardingText2 = "Manage your property with ease from\n" +
                            "One place"
                 ),
                OnboardingItemModel(
                  onboardingImage = R.drawable.group_1,
                 onboardingText1 = "Manage Properties",
                    onboardingText2 = "Making the renting experience easier\n" +
                            "At your fingertips"
                 ),

                OnboardingItemModel(
                    onboardingImage = R.drawable.group_1,
                    onboardingText1 ="View statements",
                    onboardingText2 = "View rent payments statements to\n" +
                            "analyze your finances"
                )
            )
        )

        binding.ViewPager.adapter = onboardingItemAdapter
        binding.ViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                setCurrentIndicator(position)

            }
        })

    }

    private fun setUpIndicators(){
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val  layoutParams : LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        layoutParams.setMargins(8,0,8,0)

        for (i in indicators.indices){

            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        context?.applicationContext!!,
                        R.drawable.tab_indicator_default
                    )
                )

                it.layoutParams = layoutParams
                binding.ScrollInd.addView(it)
            }
        }
    }

    private  fun setCurrentIndicator(position: Int){
        val  childCount = binding.ScrollInd.childCount

        for (i in 0 until childCount){

            val image = binding.ScrollInd.getChildAt(i) as ImageView

            if( i == position){

                image.setImageDrawable(
                    ContextCompat.getDrawable(
                            context?.applicationContext!!,
                        R.drawable.tab_indicator_selected
                            )
                    )
            }
            else{
                image.setImageDrawable(
                    ContextCompat.getDrawable(
                            context?.applicationContext!!,
                        R.drawable.tab_indicator_default)
                )
            }
        }
    }
}