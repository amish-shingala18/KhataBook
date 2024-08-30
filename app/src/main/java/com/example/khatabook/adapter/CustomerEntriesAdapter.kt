package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
import com.example.khatabook.databinding.CustomerEntriesSampleBinding
import com.example.khatabook.helper.TransactionEntity

class CustomerEntriesAdapter(private var list: MutableList<TransactionEntity>) :
    RecyclerView.Adapter<CustomerEntriesAdapter.CustomerEntriesViewHolder>() {
    class CustomerEntriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CustomerEntriesSampleBinding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerEntriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customer_entries_sample,parent,false)
        return CustomerEntriesViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomerEntriesViewHolder, position: Int) {
        holder.binding.customerEntryProName.text=list[position].entryProductName
        holder.binding.customerEntryProQuantity.text=list[position].entryProductQuantity
        holder.binding.customerEntryProPrice.text="₹${list[position].entryProductPrice}"
        holder.binding.customerEntryProDate.text=list[position].entryProductDate
        holder.binding.customerEntryProCollDate.text=list[position].entryCollectionDate
        holder.binding.customerEntryProAmount.text="₹${list[position].entryProductAmount}"
        if(list[position].entryProductStatus==1){
            holder.binding.textCollection.visibility=View.GONE
            holder.binding.customerEntryProCollDate.visibility=View.GONE
            holder.binding.customerEntryProAmount.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
        else{
            holder.binding.customerEntryProAmount.setTextColor(holder.itemView.context.getColor(R.color.red))
        }
//        holder.binding.cvCustomerEntries.setOnClickListener {
//            val customerEntryIntent= Intent(holder.itemView.context, TransactionDetailActivity::class.java)
//            customerEntryIntent.putExtra("customerEntryCustomerId",list[position].entryCustomerId)
//            customerEntryIntent.putExtra("customerEntryCustomerName",list[position].customerName)
//            customerEntryIntent.putExtra("customerEntryId",list[position].entryId)
//            customerEntryIntent.putExtra("customerEntryProductName",list[position].entryProductName)
//            customerEntryIntent.putExtra("customerEntryProductQuantity",list[position].entryProductQuantity)
//            customerEntryIntent.putExtra("customerEntryProductPrice",list[position].entryProductPrice)
//            customerEntryIntent.putExtra("customerEntryProductAmount",list[position].entryProductAmount)
//            customerEntryIntent.putExtra("customerEntryProductStatus",list[position].entryProductStatus)
//            customerEntryIntent.putExtra("customerEntryProductDate",list[position].entryProductDate)
//            customerEntryIntent.putExtra("customerEntryCollectionDate",list[position].entryCollectionDate)
//            customerEntryIntent.putExtra("customerEntryCustomerMobile",list[position].customerMobile)
//            holder.itemView.context.startActivity(customerEntryIntent)
//        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<TransactionEntity>) {
        list=l1
        notifyDataSetChanged()
    }
}