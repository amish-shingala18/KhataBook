package com.example.khatabook.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.databinding.ActivityAddUserBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var allUserList : MutableList<CustomerEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        allUserList= mutableListOf()
        super.onCreate(savedInstanceState)
        binding=ActivityAddUserBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
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
    }
    private fun addUser(){

        val customerName=binding.edtName.text.toString()
        val customerMobile=binding.edtMobile.text.toString()
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
                CustomerEntity(customerName = customerName, customerMobile = customerMobile)
            val db = DbRoomHelper.initDb(this)
            db.dao().customerInsert(customerEntity)
            finish()
        }
    }
}