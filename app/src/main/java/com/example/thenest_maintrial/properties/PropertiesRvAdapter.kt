package com.example.thenest_maintrial.properties

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.LL_Property
import com.example.thenest_maintrial.databinding.FragmentPropertiesItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PropertiesRvAdapter(
    private var properties: List<LL_Property>
) : RecyclerView.Adapter<PropertiesRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPropertiesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = properties[position]
        holder.bind(item)
//        propertyItemName = item.name

    }

    override fun getItemCount(): Int = properties.size

    fun updateData(properties : List<LL_Property>) {
        this.properties = properties
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FragmentPropertiesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: LL_Property) {

                    binding.propertyItemName.text = item.name
                    binding.propertyItemID.text = item.propertyID
                    binding.propertyItemLocation.text = item.location
                    binding.propUnitsNo.text = item.total_units.toString()
                    binding.propVacantNo.text = item.vacant_units.toString()

            }
        init {
            binding.propertyItemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val property = properties[position]
                    val action = PropertiesFragmentDirections.actionPropertiesFragmentToPropertySingleViewFragment(property.propertyID)
                    binding.propertyItemView.findNavController().navigate(action)
                }
            }
        }
//        var propertyItemName = binding.propertyItemName.text.toString()



        override fun toString(): String {
            return super.toString() + " '" + binding.propertyItemName.text + "'"
        }
    }

}