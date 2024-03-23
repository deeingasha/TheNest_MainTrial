package com.example.thenest_maintrial.tenants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.TenantDetails
import com.example.thenest_maintrial.databinding.FragmentTenantsItemBinding
import com.example.thenest_maintrial.tenants.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TenantRvAdapter(
    private val values: List<TenantDetails>
) : RecyclerView.Adapter<TenantRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTenantsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
//        holder.idView.text = item.id
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentTenantsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: TenantDetails) {
                binding.tenantFName.text = item.fName
                binding.tenantLName.text = item.lName
                binding.tenantID.text = item.idNumber
                binding.tenantUnitNo .text = item.unitCode
    //TODO() startdate and checkbox

            }
//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content

//        override fun toString(): String {
////            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}