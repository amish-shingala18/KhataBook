package com.example.khatabook.activity

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
        private fun initClick(){
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
        else {
            val customerEntity =
                CustomerEntity(customerName = customerName, customerMobile = customerMobile,
                    customerFlat = customerFlat,customerArea = customerArea,
                    customerPinCode = customerPinCode,customerCity = customerCity,
                    customerState = customerState)
            initDb(this)
            db!!.dao().customerInsert(customerEntity)
            finish()
        }
    }
}