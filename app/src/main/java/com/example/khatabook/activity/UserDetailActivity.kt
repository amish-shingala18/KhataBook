package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.CustomerEntriesAdapter
import com.example.khatabook.databinding.ActivityUserDetailBinding
import com.example.khatabook.fragment.AllUserFragment.Companion.allUserList
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.TransactionEntity

class UserDetailActivity : AppCompatActivity() {
    private var updateAddress: String=""
    private var updateMobile: String=""
    private var updateName: String=""
    private lateinit var binding:ActivityUserDetailBinding
    private var customerEntryList = mutableListOf<TransactionEntity>()
    private lateinit var customerEntryAdapter :CustomerEntriesAdapter
    private var intentId: Int? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emptyData()
        getCustomerData()
        initCLick()
        initRv()
    }
    private fun initRv(){
        customerEntryAdapter= CustomerEntriesAdapter(customerEntryList)
        binding.rvcCustomerEntries.adapter=customerEntryAdapter
    }
    @SuppressLint("SetTextI18n")
    private fun getCustomerData(){
        intentId=intent.getIntExtra("customerId",-1)
        val customerName=intent.getStringExtra("customerName")
        val customerMobile=intent.getStringExtra("customerMobile")
        val customerFlat=intent.getStringExtra("customerFlat")
        val customerArea=intent.getStringExtra("customerArea")
        val customerPinCode=intent.getStringExtra("customerPinCode")
        val customerCity=intent.getStringExtra("customerCity")
        val customerState=intent.getStringExtra("customerState")
        binding.txtUpdateName.text = customerName
        binding.txtUpdateMobile.text = customerMobile
        binding.txtUpdateAddress.text = "$customerFlat $customerArea $customerCity $customerState $customerPinCode"
    }
    private fun initCLick(){
        binding.imgBackClick.setOnClickListener {
            finish()
        }
        binding.btnEditDetails.setOnClickListener {
            editData()
        }
        binding.btnDeleteCustomer.setOnClickListener {
            deleteCustomer()
        }
    }
    private fun editData(){
        updateName=binding.txtUpdateName.text.toString()
        updateMobile=binding.txtUpdateMobile.text.toString()
        updateAddress=binding.txtUpdateAddress.text.toString()
        val editIntent= Intent(this@UserDetailActivity,AddUserActivity::class.java)
        editIntent.putExtra("updateCustomerId",intentId)
        editIntent.putExtra("updateCustomerName",updateName)
        editIntent.putExtra("updateCustomerMobile",updateMobile)
        //editIntent.putExtra("updateCustomerAddress",updateAddress)
        startActivity(editIntent)
    }
    private fun deleteCustomer(){
        initDb(this)
        val customerEntity = CustomerEntity(customerId = intentId!!, customerName = updateName, customerMobile = updateMobile)
        db!!.dao().customerDelete(customerEntity)
        allUserList.remove(customerEntity)
        finish()
    }
    private fun emptyData(){
        if(customerEntryList.isEmpty()){
            binding.imgNoEntry.visibility=View.VISIBLE
            binding.txtNoEntry.visibility=View.VISIBLE
            binding.rvcCustomerEntries.visibility= View.GONE
        }
        else{
            binding.imgNoEntry.visibility=View.GONE
            binding.txtNoEntry.visibility=View.GONE
            binding.rvcCustomerEntries.visibility= View.VISIBLE
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        initDb(this)
        customerEntryList = db!!.dao().transactionRead(intentId!!)
        customerEntryAdapter.dataSetChanged(customerEntryList)
        emptyData()
        super.onResume()
    }
}