package com.example.thenest_maintrial.properties.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thenest_maintrial.data.remote.model.response.UnitType
import com.example.thenest_maintrial.data.remote.model.response.createUnit
import com.example.thenest_maintrial.databinding.FragmentAddUnitsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addUnitsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addUnitsFragment : Fragment() {

    private lateinit var binding: FragmentAddUnitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUnitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val propertyID = pID.text.toString()
            val total_units = totalUnits.text.toString()

            val type = typeUnit.text.toString()
            val rent = rent.text.toString()
            val count = count.text.toString()

            val type1 = typeUnit1.text.toString()
            val rent1 = rent1.text.toString()
            val count1 = count1.text.toString()

            val type2 = typeUnit2.text.toString()
            val rent2 = rent2.text.toString()
            val count2 = count2.text.toString()

            val unit_types = listOf(
                UnitType(
                    type = type,
                    count = count.toInt(),
                    rent = rent.toInt()
                ),
                UnitType(
                    type = type1,
                    count = count1.toInt(),
                    rent = rent1.toInt()
                ),
                UnitType(
                    type = type2,
                    count = count2.toInt(),
                    rent = rent2.toInt()
                )
            )

           val createUnit: createUnit = createUnit(
    propertyID = propertyID,
    total_units = total_units.toInt(),
    unit_types = unit_types
)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addUnitsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addUnitsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}