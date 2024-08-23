package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.databinding.ActivityAddUserBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb

class AddUserActivity : AppCompatActivity() {
    private var customerUpdateId: Int=-1
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var allUserList : MutableList<CustomerEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        allUserList= mutableListOf()
        super.onCreate(savedInstanceState)
        binding=ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initClick()
    }
    @SuppressLint("SetTextI18n")
    private fun initClick(){
        getEditData()
        if(customerUpdateId!=-1||allUserList.isNotEmpty()){
            binding.txtAddUser.text="Update User"
            binding.btnSubmit.text="Update"
        }
        binding.btnSubmit.setOnClickListener {
            addUser()
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun addUser(){
        val customerName=binding.edtName.text.toString()
        val customerMobile=binding.edtMobile.text.toString()
        val customerFlat=binding.edtFlat.text.toString()
        val customerArea=binding.edtArea.text.toString()
        val customerPinCode=binding.edtPinCode.text.toString()
        val customerCity=binding.edtCity.text.toString()
        val customerState=binding.edtState.text.toString()
        if (customerName.isEmpty()){
            binding.txtNameLayout.setEnabled(true)
            binding.txtNameLayout.setError("Enter Name")
        }
        else if(customerMobile.isEmpty()){
            binding.txtMobileLayout.setEnabled(true)
            binding.txtMobileLayout.setError("Enter Mobile")
        }
        else if(customerMobile.length!=10){
            binding.txtMobileLayout.setEnabled(true)
            binding.txtMobileLayout.setError("Enter Valid Number")
        }
        else {
            getEditData()
            val customerEntity =
                CustomerEntity( customerName = customerName, customerMobile = customerMobile,
                    customerFlat = customerFlat,customerArea = customerArea,
                    customerPinCode = customerPinCode,customerCity = customerCity,
                    customerState = customerState)
            initDb(this)
            if(customerUpdateId==-1) {
                db!!.dao().customerInsert(customerEntity)
            }
            else{
                customerEntity.customerId = customerUpdateId
                db!!.dao().customerUpdate(customerEntity)
            }
            finish()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getEditData(){
        val customerName=intent.getStringExtra("updateCustomerName")
        val customerMobile=intent.getStringExtra("updateCustomerMobile")
        customerUpdateId=intent.getIntExtra("updateCustomerId",-1)
        val customerFlat=intent.getStringExtra("updateFlat")
        val customerArea=intent.getStringExtra("updateArea")
        val customerPinCode=intent.getStringExtra("updatePinCode")
        val customerCity=intent.getStringExtra("updateCity")
        val customerState=intent.getStringExtra("updateState")
        binding.edtName.setText(customerName)
        binding.edtMobile.setText(customerMobile)
        binding.edtFlat.setText(customerFlat)
        binding.edtArea.setText(customerArea)
        binding.edtPinCode.setText(customerPinCode)
        binding.edtCity.setText(customerCity)
        binding.edtState.setText(customerState)
    }
}