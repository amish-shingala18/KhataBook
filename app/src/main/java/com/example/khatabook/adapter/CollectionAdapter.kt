package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
import com.example.khatabook.databinding.DailyCollectionSampleBinding
import com.example.khatabook.helper.TransactionEntity

class   CollectionAdapter(private var list:MutableList<TransactionEntity>) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionBinding=DailyCollectionSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_collection_sample,parent,false)
        return CollectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.collectionBinding.txtCollectionUserName.text=list[position].customerName
        holder.collectionBinding.txtCollectionProName.text=list[position].entryProductName
        holder.collectionBinding.txtCollectionProPrice.text="₹ ${list[position].entryProductPrice}"
        holder.collectionBinding.txtCollectionProQuantity.text= list[position].entryProductQuantity
        holder.collectionBinding.txtCollectionProAmount.text="₹ ${list[position].entryProductAmount}"
        if (list[position].entryProductStatus==1){
            holder.collectionBinding.txtCollectionProAmount.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
        else{
            holder.collectionBinding.txtCollectionProAmount.setTextColor(holder.itemView.context.getColor(R.color.red))
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataChanged(l1:MutableList<TransactionEntity>){
        list=l1
        notifyDataSetChanged()
    }
}