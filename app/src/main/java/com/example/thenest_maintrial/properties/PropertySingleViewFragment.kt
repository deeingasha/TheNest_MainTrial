package com.example.thenest_maintrial.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.data.remote.model.response.singlePropertyResponse
import com.example.thenest_maintrial.databinding.FragmentPropertySingleViewBinding
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertySingleViewFragment : Fragment() {

//    @Inject
//    lateinit var apiService: ApiService

    private lateinit var binding: FragmentPropertySingleViewBinding
    private val args: PropertySingleViewFragmentArgs by navArgs()
    private val viewModel: PropertiesViewModel by viewModels()//{
//        PropertiesViewModelFactory(PropertiesRepository())
//    }
    private lateinit var loadingDialog: LoadingDialog


    companion object {
        fun newInstance() = PropertySingleViewFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPropertySingleViewBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //instantiate the adapter and set it to the recyclerview
        val adapter = UnitRvAdapter(emptyList())
        binding.pDetailsUnitsRV.adapter = adapter

        binding.apply {

            val propertyId = args.pID


            // Call the getSingleProperty function
            if (propertyId != null) {
                viewModel.getSingleProperty(propertyId).observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            showToast(resource.message.toString())
                            val propertyDetails = resource.data as singlePropertyResponse
                            println(propertyDetails)

                            val pName= propertyDetails.data?.name
                            val pLocation = propertyDetails.data?.location
                            val pID = propertyDetails.data?.propertyID
                            val tUnits = propertyDetails.data?.total_units
                            val vUnits = propertyDetails.data?.vacant_units
                            val propertyUnits = propertyDetails.data?.units

                            if (!propertyUnits.isNullOrEmpty()) {
                                adapter.updateData(propertyUnits?: emptyList())
                                println("propertunmi $propertyUnits")
                            }else{
                                pDetailsUnitsRV.visibility = View.GONE
                                emptyAnimationP.visibility = View.VISIBLE
                                emptyPropTV2.visibility = View.VISIBLE
                                autoPopUnitsBtn.visibility = View.VISIBLE

                                autoPopUnitsBtn.setOnClickListener {
                                    val action = PropertySingleViewFragmentDirections.actionPropertySingleViewFragmentToAddUnitsFragment()
                                    findNavController().navigate(action)
                                }
                            }


                            propertyItemName.text = pName
                            propertyItemID.text=pID
                            propertyItemLocation.text=pLocation
                            propUnitsNo.text = tUnits.toString()
                            propVacantNo.text = vUnits.toString()


                        }

                        Status.ERROR -> {
                            loadingDialog.dismiss()
                           showToast(resource.message.toString())
                            println(resource.message.toString())
                        }

                        Status.LOADING -> {
                            loadingDialog.show()
                        }
                    }
                }
            }

            propertyCaBtn.setOnClickListener {
                val action = PropertySingleViewFragmentDirections.actionPropertySingleViewFragmentToPropertiesFragment()
                findNavController().navigate(action)
            }
        }



    }
}
