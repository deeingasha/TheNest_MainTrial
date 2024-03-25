package com.example.thenest_maintrial.tenants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.LL_Tenant
import com.example.thenest_maintrial.databinding.FragmentTenantsItemBinding
import com.example.thenest_maintrial.tenants.placeholder.PlaceholderContent.PlaceholderItem
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TenantRvAdapter(
    private var tenants: List<LL_Tenant>
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
        val item = tenants[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = tenants.size

    fun updateData(tenants: List<LL_Tenant>) {
        this.tenants = tenants
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FragmentTenantsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LL_Tenant) {

            val currentFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            currentFormat.timeZone = TimeZone.getTimeZone("UTC")
            val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = currentFormat.parse(item.start_date)
            val formattedDate = desiredFormat.format(date)


            binding.tenantFName.text = item.userDetails.fName
            binding.tenantLName.text = item.userDetails.lName
            binding.tenantID.text = item.idNumber
            binding.tenantUnitNo.text = item.unitCode
            binding.startDate.text = formattedDate
            binding.isActiveCB.isChecked = item.is_active
            //TODO() startdate and checkbox

        }
//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content

//        override fun toString(): String {
////            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}