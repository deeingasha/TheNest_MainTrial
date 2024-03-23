package com.example.thenest_maintrial.properties

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.Prop_UnitDetails
import com.example.thenest_maintrial.databinding.FragmentUnitItemBinding
import com.example.thenest_maintrial.properties.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class UnitRvAdapter(
    private var units: List<Prop_UnitDetails>
) : RecyclerView.Adapter<UnitRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentUnitItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = units[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = units.size

    fun updateData(units : List<Prop_UnitDetails>) {
        this.units = units
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FragmentUnitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Prop_UnitDetails) {
                binding.unitItemCode.text = item.unitCode
                binding.unitType.text = item.unit_type
                binding.rentAmount.text = item.rent.toString()
                binding.unitIsOccupied.isChecked= item.isOccupied

            }

        override fun toString(): String {
            return super.toString() + " '" + binding.unitItemCode.text + "'"
        }
    }

}