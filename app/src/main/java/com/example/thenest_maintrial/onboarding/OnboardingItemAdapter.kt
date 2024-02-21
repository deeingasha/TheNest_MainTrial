package com.example.thenest_maintrial.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.databinding.OnboardingLayoutBinding

class OnboardingItemAdapter (
    private val onboardingItems: List<OnboardingItemModel>
): RecyclerView.Adapter<OnboardingItemAdapter.OnboardingViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnboardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnboardingLayoutBinding.inflate(inflater, parent,false)

        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OnboardingViewHolder,
        position: Int
    ) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingViewHolder(private val binding: OnboardingLayoutBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun  bind(onboardingItems: OnboardingItemModel){

            binding.welcomeImg.setBackgroundResource(onboardingItems.onboardingImage)

            binding.welcomeTxt.text = onboardingItems.onboardingText1
            binding.welcomeTxt2.text = onboardingItems.onboardingText2

        }
    }
}