package com.example.thenest_maintrial.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.databinding.FragmentDashboardFABBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboard_FAB_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard_FAB_Fragment : Fragment() {

    //use view binding
    private lateinit var binding: FragmentDashboardFABBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardFABBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fabCaBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            addPropertyBtn.setOnClickListener {
                val action =
                    Dashboard_FAB_FragmentDirections.actionDashboardFABFragmentToAddPropertyFragment()
                findNavController().navigate(action)
            }
            addUnitBtn.setOnClickListener {
                //TODO() navigate to add unit fragment
            }
            addTenantBtn.setOnClickListener {
                val action=
                    Dashboard_FAB_FragmentDirections.actionDashboardFABFragmentToSecurityQuestionFragment()
                findNavController().navigate(action)
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Dashboard_FAB_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Dashboard_FAB_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}