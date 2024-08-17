package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
import com.example.khatabook.databinding.AllUserSampleBinding
import com.example.khatabook.helper.CustomerEntity

class AllUserAdapter(private var list: MutableList<CustomerEntity>)
    : RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder>() {
    class AllUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sampleBinding = AllUserSampleBinding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_user_sample,parent,false)
        return AllUserViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        holder.sampleBinding.txtUserName.text=list[position].customerName
        holder.sampleBinding.txtUserMobile.text=list[position].customerMobile
        holder.sampleBinding.txtChar.text=list[position].customerName.first().toString()
        holder.sampleBinding.imgCallUser.setOnClickListener {
            if(holder.sampleBinding.imgCallUser.context.checkSelfPermission
                    (android.Manifest.permission.CALL_PHONE)==
                android.content.pm.PackageManager.PERMISSION_GRANTED){
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${list[position].customerMobile}")
                startActivity(holder.sampleBinding.imgCallUser.context,callIntent,null)
            }
            else{
                Toast.makeText(holder.sampleBinding.imgCallUser.context, "Phone Call Permission is Denied", Toast.LENGTH_LONG).show()
            }
        }
        holder.sampleBinding.imgMessageUser.setOnClickListener {
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<CustomerEntity>) {
        list=l1
        notifyDataSetChanged()
    }
}
