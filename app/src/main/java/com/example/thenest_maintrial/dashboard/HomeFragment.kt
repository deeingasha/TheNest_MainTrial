package com.example.thenest_maintrial.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.databinding.DashboardFragBinding
import com.example.thenest_maintrial.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    //    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var binding2: DashboardFragBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false).apply {
//            viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            RentDueView.setOnClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToRentPaymentFragment()
                findNavController().navigate(action)
            }
            PaymentsPendingView.setOnClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToExpensesFragment()
                findNavController().navigate(action)
            }
        }

//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        // TODO: Use the ViewModel
    }
}





