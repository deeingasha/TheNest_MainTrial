package com.example.thenest_maintrial.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.utils.SharedViewModel
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class UnitsFragment : Fragment() {

    private var columnCount = 1

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val propertiesViewModel: PropertiesViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var unitsRv: RecyclerView
    private lateinit var emptyAnimation: LottieAnimationView
    private lateinit var emptyText: TextView
    private lateinit var backButon: ImageButton
    private lateinit var addUnitButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_unit_list, container, false)
        loadingDialog = LoadingDialog(requireContext())


        unitsRv = view.findViewById(R.id.UnitsListRv_view)
        emptyAnimation = view.findViewById(R.id.empty_animationP)
        emptyText = view.findViewById(R.id.emptyProp_TV)
        backButon = view.findViewById(R.id.units_ca_btn)
        addUnitButton = view.findViewById(R.id.fab_add_Unit)

        // Set the adapter
        if (unitsRv is RecyclerView) {
            with(unitsRv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = propertiesViewModel.unitAdapter
            }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("units fragment")


        sharedViewModel.llProperties.observe(viewLifecycleOwner, Observer{ resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        println("hello")
                        println("Resource: ${resource.data}")


//                    val propDets = resource.data  as LL_PropertiesResponse
//
//                    val llunits = propDets.data?.flatMap { it.units } ?: emptyList()
//                    println("ll units: $llunits")
//                    unitAdapter.updateData(llunits)
                    }
                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> {
                        loadingDialog.show()
                    }
                }})



            propertiesViewModel.getLLProperties()
        backButon.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun observeProperties(){

}


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            UnitsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}