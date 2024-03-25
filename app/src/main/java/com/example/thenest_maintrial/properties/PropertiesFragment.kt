package com.example.thenest_maintrial.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PropertiesFragment : Fragment() {

    private var columnCount = 1

  //get instance of the view model
    private val propertiesViewModel: PropertiesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    //reference the RecyclerView and the animation view
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var propertiesRv: RecyclerView
    private lateinit var emptyAnimation: LottieAnimationView
    private lateinit var backButon: ImageButton
    private lateinit var titleCard:CardView
    private  lateinit var addPropertyBtn: FloatingActionButton
    private lateinit var emptyProp: TextView
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
        val view = inflater.inflate(R.layout.fragment_properties_list, container, false)
        loadingDialog = LoadingDialog(requireContext())

        //initialize the views
        propertiesRv = view.findViewById(R.id.propertiesListRv_view)
        emptyAnimation = view.findViewById(R.id.empty_animationP)
        backButon = view.findViewById(R.id.property_ca_btn)
        titleCard = view.findViewById(R.id.propertiesTitleCardView)
        addPropertyBtn = view.findViewById(R.id.fab_add_Property)
        emptyProp= view.findViewById(R.id.emptyProp_TV)


        // Set the adapter
        if (propertiesRv is RecyclerView) {
            with(propertiesRv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = propertiesViewModel.propertiesRvAdapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe the llProperties live data
        propertiesViewModel.llProperties.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    println("resource Success: ${resource.data}")
                    println("resource message: ${resource.message}")
                    if (resource.message.toString() != "No properties yet") {

                        propertiesRv.visibility = View.VISIBLE
                        emptyAnimation.visibility = View.GONE
                        emptyProp.visibility = View.GONE

                        sharedViewModel.llProperties.value = resource

                    } else {
                        propertiesRv.visibility = View.GONE
                        emptyAnimation.visibility = View.VISIBLE
                        emptyProp.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    loadingDialog.dismiss()
                    showToast(resource.message.toString())
                    println("Error message from server: ${resource.message.toString()}")

                }

                Status.LOADING -> {
                    loadingDialog.show()
                }
            }

        })
        //call the getLLProperties function to get the properties from the server
        propertiesViewModel.getLLProperties()

//        //register an observer to listen for changes in the data
//        propertiesViewModel.propertiesRvAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                //Scroll to the new position when a new item is added
//                propertiesRv.post{
//                    propertiesRv.layoutManager?.scrollToPosition(propertiesViewModel.propertiesRvAdapter.itemCount - 1)
//                }            }
//        })

//        observe the newPropertyPosition Livedata and scroll when it changes
        propertiesViewModel.newPropertyPosition.observe(viewLifecycleOwner){position->
            propertiesRv.layoutManager?.scrollToPosition(position)
        }

        backButon.setOnClickListener {
            val action = PropertiesFragmentDirections.actionPropertiesFragmentToDashboardFragment()
            findNavController().navigate(action)

        }
        addPropertyBtn.setOnClickListener {
            val action = PropertiesFragmentDirections.actionPropertiesFragmentToAddPropertyFragment()
            findNavController().navigate(action)
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PropertiesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}