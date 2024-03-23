package com.example.thenest_maintrial.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.data.remote.PropertyDetails
import com.example.thenest_maintrial.data.remote.model.response.addPropertyResponse
import com.example.thenest_maintrial.databinding.FragmentPropertiesAddBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyFragment: Fragment(){
    private lateinit var binding: FragmentPropertiesAddBinding
    private val viewModel: PropertiesViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPropertiesAddBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            continuePropBtn.setOnClickListener {
                if (validateForm()) {
                    val name = binding.pName.text.toString()
                    val location = binding.pLocation.text.toString()
                    val pID   = binding.pID.text.toString()
                    val totalUnits = binding.tUnits.text.toString().toInt()

                    val propertyDetails =   PropertyDetails(
                        name,
                        location,
                        pID,
                        totalUnits,
                    )

                    addProperty(propertyDetails)
                }
            }
            propertyCaBtn.setOnClickListener {
                val action = AddPropertyFragmentDirections.actionAddPropertyFragmentToPropertiesFragment()
                findNavController().navigate(action)
            }
        }
    }
    private fun addProperty(propertyDetails: PropertyDetails) {
        viewModel.addProperty(propertyDetails).observe(viewLifecycleOwner) {
            binding.apply {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            showToast(resource.message.toString())
                            println("response from addprop: ${resource.data}")
                            val unitDetails =resource.data as addPropertyResponse

                            val pName = unitDetails.data?.name
                            val pLocation = unitDetails.data?.location
                            val pID = unitDetails.data?.propertyID
                            val tUnits = unitDetails.data?.total_units
                            val vUnits = unitDetails.data?.vacant_units


                            val action = AddPropertyFragmentDirections.actionAddPropertyFragmentToPropertySingleViewFragment(
                                pID,
                            )

                            findNavController().navigate(action)


                        }
                        Status.LOADING -> {
                            loadingDialog.show()
                        }
                        Status.ERROR -> {
                            loadingDialog.dismiss()
                            showToast(resource.message.toString())
                            println("response from server: $resource")
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val name = binding.pName.text.toString()
        val location = binding.pLocation.text.toString()
        val pID   = binding.pID.text.toString()
        val totalUnits = binding.tUnits.text.toString()

        binding.apply {
            hideErrorMessage()
            when{
                name.isEmpty() -> {
                    pNameInputLayout.isErrorEnabled = true
                    pNameInputLayout.error = "Property name is required"
                    return false
                }
                name.length < 3 -> {
                    pNameInputLayout.isErrorEnabled = true
                    pNameInputLayout.error = "Property name must be at least 3 characters long"
                    return false
                }
                location.isEmpty() -> {
                    pLocationInputLayout.isErrorEnabled = true
                    pLocationInputLayout.error = "Location is required"
                    return false
                }
                pID.isEmpty() -> {
                    pIDInputLayout.isErrorEnabled = true
                    pIDInputLayout.error = "PropertyID is required"
                    return false
                }
                pID.length < 2 -> {
                    pIDInputLayout.isErrorEnabled = true
                    pIDInputLayout.error = "PropertyID must be at least 3 characters long"
                    return false
                }
                totalUnits.isEmpty() -> {
                    tUnitsInputLayout.isErrorEnabled = true
                    tUnitsInputLayout.error = "Total number of units is required"
                    return false
                }
                totalUnits.toIntOrNull()== null -> {
                    tUnitsInputLayout.isErrorEnabled = true
                    tUnitsInputLayout.error = "Invalid number of units"
                    return false
                }
                totalUnits.toInt() < 1 -> {
                    tUnitsInputLayout.isErrorEnabled = true
                    tUnitsInputLayout.error = "Total number of units must be at least 1"
                    return false
                }

                else -> return true
            }
        }
    }

    private fun hideErrorMessage() {
        binding.apply {
            pNameInputLayout.isErrorEnabled = false
            pLocationInputLayout.isErrorEnabled = false
            pIDInputLayout.isErrorEnabled = false
            tUnitsInputLayout.isErrorEnabled = false
        }
    }
}