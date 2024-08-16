package com.example.khatabook.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.SpinnerAdapter
import com.example.khatabook.databinding.ActivityAddEntryBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb

class AddEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEntryBinding
    private var userList = mutableListOf<CustomerEntity>()
    private lateinit var spinnerAdapter : SpinnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding=ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRv()
        initClick()
    }

    private fun initRv() {
        userList = initDb(this).dao().customerRead()
        spinnerAdapter = SpinnerAdapter(userList)
        binding.customerSpinner.adapter = spinnerAdapter
    }
    private fun initClick(){
        binding.btnSubmit.setOnClickListener {
            val productQuantity=binding.edtProductQuantity.text.toString()
            val productPrice=binding.edtProductPrice.text.toString()
            val productName=binding.edtProductName.text.toString()
            if (productName.isEmpty()){
                binding.txtProductNameLayout.setEnabled(true)
                binding.txtProductNameLayout.setError("Enter Product Name")
            }
            else if(productQuantity.isEmpty()){
                binding.txtProductQuantityLayout.setEnabled(true)
                binding.txtProductQuantityLayout.setError("Enter Product Quantity")
            }
            else if(productPrice.isEmpty()){
                binding.txtProductPriceLayout.setEnabled(true)
                binding.txtProductPriceLayout.setError("Enter Product Price")
            }
            else if(binding.customerSpinner.selectedItemPosition==0){
                Toast.makeText(this, "Please Select Customer", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.txtProductNameLayout.setEnabled(false)
                binding.txtProductQuantityLayout.setEnabled(false)
                binding.txtProductPriceLayout.setEnabled(false)
                Toast.makeText(this, "Entry Added Successfully", Toast.LENGTH_SHORT).show()
                val txtTotalAmount=productQuantity.toInt()*productPrice.toInt()
                binding.txtTotalAmount.text= txtTotalAmount.toString()
            }

        }
    }
}
