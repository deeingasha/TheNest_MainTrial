package com.example.thenest_maintrial.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thenest_maintrial.data.remote.model.response.MaintenanceData
import com.example.thenest_maintrial.databinding.FragmentMaintenanceItemBinding

class MaintenanceRvAdapter(
    private var maintenanceList: List<MaintenanceData>
): RecyclerView.Adapter<MaintenanceRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMaintenanceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = maintenanceList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = maintenanceList.size

    fun updateData(maintenanceList: List<MaintenanceData>) {
        this.maintenanceList = maintenanceList
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: FragmentMaintenanceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MaintenanceData) {
            binding.maintDesc.text = item.description
            binding.maintunit.text = item.unitCode
            binding.tenantID.text = item.tenantID
            binding.maintFixed.isChecked = item.isFixed

        }
    }
}