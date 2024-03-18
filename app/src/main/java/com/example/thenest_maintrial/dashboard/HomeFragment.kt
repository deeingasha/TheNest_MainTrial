package com.example.thenest_maintrial.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.SharedPreferenceManager
import com.example.thenest_maintrial.data.remote.model.response.LlDashboardResponse
import com.example.thenest_maintrial.databinding.FragmentHomeBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false).apply {
//            viewModel
        }
        loadingDialog = LoadingDialog(requireContext())
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDashboardDetails()
       viewModel.getLlDashboardDetails()

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
    }

    private fun observeDashboardDetails() {
        viewModel.dashboardDetails.observe(viewLifecycleOwner){
            binding.apply {
                it.let {resource ->
                    when(resource.status){
                        Status.SUCCESS -> {
                            showToast(resource.message.toString())
                            println(resource.data)
                            val dashboardDetails = resource.data as LlDashboardResponse
                            val name = dashboardDetails.data?.name
                            val numOfProperties = dashboardDetails.data?.numOfProperties
                            val totalVacantUnits = dashboardDetails.data?.totalVacantUnits
                            val numOfTenants = dashboardDetails.data?.numOfTenants

                            propertiesNum.text = numOfProperties.toString()
                            tenantsNumber.text = numOfTenants.toString()
                            emptyUnitsNumber.text = totalVacantUnits.toString()
                            usernameDbTv.text = name

                            //store username in shared preference
                            resource.data?.data?.let { username ->
                                if (name != null) {
                                    sharedPreferenceManager.storeUsername(name)
                                }
                            }
                            println("username: ${sharedPreferenceManager.getUsername()}") //todo remove print statement


                        }
                        Status.ERROR -> {
                            showToast(resource.message.toString())
                            findNavController().navigateUp()
                        }
                        Status.LOADING -> {
                            loadingDialog.show()
                        }
                    }
                }
            }
        }
    }

}





