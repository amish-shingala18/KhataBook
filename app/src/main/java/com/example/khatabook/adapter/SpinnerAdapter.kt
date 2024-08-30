package com.example.khatabook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.khatabook.R
import com.example.khatabook.databinding.DropDownSampleBinding
import com.example.khatabook.helper.CustomerEntity

class SpinnerAdapter(private var list: MutableList<CustomerEntity>) : BaseAdapter() {
    init{
        list.add(
            0,
            CustomerEntity(customerName = "--- Select Customer ---", customerMobile = "")
        )
    }
    override fun getCount(): Int {
        return list.count()
    }
    override fun getItem(position: Int): Any {
        return 0
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =LayoutInflater.from(parent!!.context).inflate(R.layout.drop_down_sample,parent,false)
        val binding = DropDownSampleBinding.bind(view)
        binding.txtUsername.text = list[position].customerName
        return binding.root
    }
}
