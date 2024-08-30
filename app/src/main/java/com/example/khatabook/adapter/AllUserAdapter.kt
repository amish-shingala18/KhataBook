package com.example.khatabook.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.khatabook.R
import com.example.khatabook.activity.UserDetailActivity
import com.example.khatabook.databinding.AllUserSampleBinding
import com.example.khatabook.helper.CustomerEntity

class AllUserAdapter(
    private var list: MutableList<CustomerEntity>,
    private val permissionLauncher: ActivityResultLauncher<Array<String>>
)
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
                    (Manifest.permission.CALL_PHONE)== android.content.pm.PackageManager.PERMISSION_GRANTED){
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${list[position].customerMobile}")
                startActivity(holder.sampleBinding.imgCallUser.context,callIntent,null)
            }
            else{
                permissionLauncher.launch(arrayOf(Manifest.permission.CALL_PHONE))

                Toast.makeText(holder.sampleBinding.imgCallUser.context, "Phone Call Permission is Denied", Toast.LENGTH_LONG).show()
            }
        }
        holder.sampleBinding.imgMessageUser.setOnClickListener {
            val phoneNumber = list[position].customerMobile
            val whatsAppMessage = "Hello ${list[position].customerName},\n\nWe have an exclusive offer for you! Get " +
                    "10% off on your next purchase. " +
                    "Hurry, offer valid for a limited time!"
            val url = "https://api.whatsapp.com/send?phone= +91$phoneNumber&text=${Uri.encode(whatsAppMessage)}"
            val whatsappIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(holder.sampleBinding.imgMessageUser.context, whatsappIntent, null)
        }
        holder.sampleBinding.cvCustomer.setOnClickListener {
            val intent = Intent(holder.sampleBinding.cvCustomer.context, UserDetailActivity::class.java)
            intent.putExtra("customerName",list[position].customerName)
            intent.putExtra("customerMobile",list[position].customerMobile)
            intent.putExtra("customerFlat",list[position].customerFlat)
            intent.putExtra("customerArea",list[position].customerArea)
            intent.putExtra("customerPinCode",list[position].customerPinCode)
            intent.putExtra("customerCity",list[position].customerCity)
            intent.putExtra("customerState",list[position].customerState)
            intent.putExtra("customerId",list[position].customerId)
            startActivity(holder.itemView.context,intent,null)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<CustomerEntity>) {
        list=l1
        notifyDataSetChanged()
    }
}
