package com.example.thenest_maintrial.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thenest_maintrial.R
import com.example.thenest_maintrial.SharedPreferenceManager
import com.example.thenest_maintrial.databinding.DashboardFragBinding

class DashboardFragment: Fragment() {

    private lateinit var binding: DashboardFragBinding
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    //private lateinit var dashboardItemAdapter: DashboardItemAdapter todo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DashboardFragBinding.inflate(inflater,container,false)
        //initialise sharedPreferenceManager
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        val role = sharedPreferenceManager.getRole()
        val fragment: Fragment = if (role == "tenant") {
            TenantHomeFragment()
        } else {
            HomeFragment()
        }
        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val maintenanceFragment = MaintenanceFragment()
        val reportsFragment = ReportsFragment()

        setCurrentFragment(fragment)

        binding.bottomNavView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.nav_home->setCurrentFragment(homeFragment)
                R.id.nav_calendar->setCurrentFragment(calendarFragment)
                R.id.nav_maintenance->setCurrentFragment(maintenanceFragment)
                R.id.nav_report->setCurrentFragment(reportsFragment)
            }
            true
        }


        binding.fab.setOnClickListener{
            val action = DashboardFragmentDirections.actionDashboardFragmentToDashboardFABFragment()

            findNavController().navigate(action)
        }
        return binding.root
    }


    private fun setCurrentFragment(fragment: Fragment)=
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.dashFragment, fragment)
            commit()
        }
}