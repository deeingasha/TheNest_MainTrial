package com.example.thenest_maintrial.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.SharedPreferenceManager
import com.example.thenest_maintrial.data.remote.model.response.TenantDashboardResponse
import com.example.thenest_maintrial.databinding.FragmentTenantHomeBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TenantHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TenantHomeFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentTenantHomeBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentTenantHomeBinding.inflate(inflater, container, false).apply {
//            viewModel
        }
        loadingDialog = LoadingDialog(requireContext())
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        return binding.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeTenantDashboardDetails()
        viewModel.getTenantDashboardDetails()

        binding.apply {

        }
    }
    private fun observeTenantDashboardDetails() {
        viewModel.tenantDashboardDetails.observe(viewLifecycleOwner) {
            binding.apply {
                it.let{resource->
                    when (resource.status) {
                        Status.LOADING -> {
                            loadingDialog.show()
                        }

                Status.SUCCESS -> {

                    loadingDialog.dismiss()
                    showToast("Data loaded successfully")
                    println(resource.data)
                    val tdashDetails = resource.data as TenantDashboardResponse

                    val fname = tdashDetails.data?.userDetails?.fName
                    val lname = tdashDetails.data?.userDetails?.lName
                    val idNo = tdashDetails.data?.idNumber
                    val is_active = tdashDetails.data?.is_active
                    val startdate = tdashDetails.data?.start_date
                    val enddate = tdashDetails.data?.end_date
                    val unitcode = tdashDetails.data?.unitCode

                    val currentFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    currentFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = currentFormat.parse(startdate)
                    val formattedDate = desiredFormat.format(date)

                    tenantUnitNo.text = unitcode
                    tenantFName.text = fname
                    tenantLName.text = lname
                    tenantID.text = idNo
                    startDate.text = formattedDate
                    if (is_active != null) {
                        isActiveCB.isChecked = is_active
                    }

                    val name= "$fname $lname"

                    sharedPreferenceManager.storeUsername(name)


                    }

                Status.ERROR -> {
                    loadingDialog.dismiss()
                    showToast(it.message ?: "An error occurred")
                }
            }
            }
        }
    }}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TenantHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TenantHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}