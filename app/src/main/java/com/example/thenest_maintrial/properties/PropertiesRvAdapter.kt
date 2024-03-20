package com.example.thenest_maintrial.properties

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.LL_Property
import com.example.thenest_maintrial.databinding.FragmentPropertiesItemBinding
import com.example.thenest_maintrial.properties.placeholder.PlaceholderContent.PlaceholderItem

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
//        holder.propertyItemID = item.propertyID
//        holder.propertyItemLocation = item.location
//        holder.propUnitsNo = item.total_units
//        holder.propVacantNo = item.vacant_units
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
//        var propertyItemName = binding.propertyItemName.text.toString()
//        var propertyItemID = binding.propertyItemID.text.toString()
//        var propertyItemLocation = binding.propertyItemLocation.text.toString()
//        var propUnitsNo = binding.propUnitsNo.text.toString().toInt()
//        var propVacantNo = binding.propVacantNo.text.toString().toInt()


        override fun toString(): String {
            return super.toString() + " '" + binding.propertyItemName.text + "'"
        }
    }

}