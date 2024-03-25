package com.example.thenest_maintrial.tenants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class TenantsFragment : Fragment() {

    private var columnCount = 1

    private val tenantsViewModel: TenantsViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var tenantsRv: RecyclerView
    private lateinit var emptyAnimation: LottieAnimationView
    private lateinit var emptyText: TextView
    private lateinit var backButon: ImageButton
    private lateinit var addTenantBtn: FloatingActionButton


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
        val view = inflater.inflate(R.layout.fragment_tenants_list, container, false)
        loadingDialog = LoadingDialog(requireActivity())

        //initialize the views
        tenantsRv = view.findViewById(R.id.tenantsListRv_view)
        emptyAnimation = view.findViewById(R.id.empty_animationT)
        emptyText = view.findViewById(R.id.emptyTenant_TV)
        backButon = view.findViewById(R.id.tenant_ca_btn)
        addTenantBtn = view.findViewById(R.id.fab_add_Tenant)


        // Set the adapter
        if (tenantsRv is RecyclerView) {
            with(tenantsRv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = tenantsViewModel.tenantRvAdapter
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe LlTenants live data
        tenantsViewModel.llTenants.observe(viewLifecycleOwner, Observer {resource->
            when(resource.status){
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    println("Resource ex message: ${resource.message}")
                    println("Resource ex data: ${resource.data}")
                    if(resource.message.toString() != "No tenants yet"){
                        tenantsRv.visibility= View.VISIBLE
                        emptyAnimation.visibility = View.GONE
                        emptyText.visibility = View.GONE
                    }else{
                        emptyAnimation.visibility = View.VISIBLE
                        emptyText.visibility = View.VISIBLE
                        tenantsRv.visibility = View.GONE
                    }

                }
                Status.LOADING -> {
                    loadingDialog.show()
                }
                Status.ERROR -> {
                    loadingDialog.dismiss()
                    showToast(resource.message.toString())
                    println("Error: ${resource.message}")
                }
            }
        })
        tenantsViewModel.getLLTenants()

        backButon.setOnClickListener {
            findNavController().navigateUp()
        }
        addTenantBtn.setOnClickListener {
            showToast("Add Tenant")
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TenantsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}