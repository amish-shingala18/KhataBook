package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
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
        if (list[position].entryProductStatus==1){
            holder.binding.txtHomeAmount.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
        else{
            holder.binding.txtHomeAmount.setTextColor(holder.itemView.context.getColor(R.color.red))
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataChanged(l1:MutableList<TransactionEntity>){
        list=l1
        notifyDataSetChanged()
    }
}