package com.example.thenest_maintrial.reports

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.LoadingDialog
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.databinding.FragmentPropertiesListBinding
import com.example.thenest_maintrial.properties.PropertiesFragment
import com.example.thenest_maintrial.properties.PropertiesViewModel
import com.example.thenest_maintrial.utils.Status
import com.example.thenest_maintrial.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [PropertiesReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PropertiesReportFragment : Fragment() {

    private var columnCount = 1

    private lateinit var binding: FragmentPropertiesListBinding
    private val propertiesViewModel: PropertiesViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var propertiesRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(PropertiesFragment.ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPropertiesListBinding.inflate(inflater, container, false)

        loadingDialog = LoadingDialog(requireContext())
        propertiesRv = binding.propertiesListRvView

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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            propSearchView.visibility = View.VISIBLE
            propFilterSortImageView.visibility = View.VISIBLE
            propertyCaBtn.visibility = View.GONE

            propSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            propFilterSortImageView.setOnClickListener {
                showFilterSortMenu()
            }
        }

        propertiesViewModel.createLandlordPropertiesReport(
            filter = null,
            sort = null,
            search = null,
            pdf = true
        ).observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    println("resource Success: ${resource.data}")
                    println("resource message: ${resource.message}")
                    showToast("report generated successfully")
                    val pdflink = resource.data
                    // Download the PDF file
                    val request = DownloadManager.Request(Uri.parse(pdflink))
                        .setTitle("Property Report")
                        .setDescription("Downloading")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "PropertyReport.pdf"
                        )
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)

                    val downloadManager =
                        context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                }

                Status.LOADING -> {
                    loadingDialog.show()
                }

                Status.ERROR -> {
                    loadingDialog.dismiss()
                    println("resource message: ${resource.message}")

                }
            }
        })


    }

    private fun showFilterSortMenu() {
        val popupMenu = android.widget.PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.property_filter_options, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.asc -> {
                    // Handle filter menu
                    val sort = "1"
                    true
                }

                R.id.desc -> {
                    // Handle sort menu
                    val sort = "-1"
                    true
                }

                R.id.vacant -> {
                    val filter = "vacant_units:{\$gt:0}"
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PropertiesReportFragment().apply {

            }
    }
}
