package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
import com.example.khatabook.activity.TransactionDetailActivity
import com.example.khatabook.databinding.HomeUsersSampleBinding
import com.example.khatabook.helper.TransactionEntity

class HomeAdapter(private var list: MutableList<TransactionEntity>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HomeUsersSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_users_sample, parent, false)
        return HomeViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.txtHomeUserName.text=list[position].customerName
        holder.binding.txtHomeProduct.text=list[position].entryProductName
        holder.binding.txtHomeAmount.text= "₹ ${list[position].entryProductAmount}"
        holder.binding.txtHomeFirstTxt.text=list[position].customerName.first().toString()
        Log.e("TAG", "onBindViewHolder: ${list[position].entryProductAmount}")
        if (list[position].entryProductStatus==1){
            holder.binding.txtHomeAmount.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
        else{
            holder.binding.txtHomeAmount.setTextColor(holder.itemView.context.getColor(R.color.red))
        }
        holder.binding.cvHomeUsers.setOnClickListener {
            val intent= Intent(holder.itemView.context, TransactionDetailActivity::class.java)
            intent.putExtra("transactionEntryCustomerId",list[position].entryCustomerId)
            intent.putExtra("transactionCustomerName",list[position].customerName)
            intent.putExtra("transactionId",list[position].entryId)
            intent.putExtra("transactionProductName",list[position].entryProductName)
            intent.putExtra("transactionProductQuantity",list[position].entryProductQuantity)
            intent.putExtra("transactionProductPrice",list[position].entryProductPrice)
            intent.putExtra("transactionProductAmount",list[position].entryProductAmount)
            intent.putExtra("transactionProductStatus",list[position].entryProductStatus)
            intent.putExtra("transactionProductDate",list[position].entryProductDate)
            intent.putExtra("transactionCollectionDate",list[position].entryCollectionDate)
            intent.putExtra("transactionCustomerMobile",list[position].customerMobile)
            startActivity(holder.itemView.context,intent,null)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataChanged(l1:MutableList<TransactionEntity>){
        list=l1
        notifyDataSetChanged()
    }
}
