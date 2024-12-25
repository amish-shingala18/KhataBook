package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.khatabook.MainActivity
import com.example.khatabook.adapter.SpinnerAdapter
import com.example.khatabook.databinding.ActivityAddEntryBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.EntryEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEntryActivity : AppCompatActivity() {
    private var editCustomerName: String=""
    private var userList = mutableListOf<CustomerEntity>()
    private var entryUpdateId: Int = -1
    private var entryCurrentDate = ""
    private var formattedDate = ""
    private var txtProductStatus: Int = 1
    private lateinit var binding: ActivityAddEntryBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private val calendar = Calendar.getInstance()
    private var txtTotalAmount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.khatabook.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Get Intent
        getEditData()

        //Set Spinner of User
        initSpinner()

        //Insert and Update
        initClick()
    }
    private fun initSpinner() {
        userList = initDb(this).dao().customerRead()
        spinnerAdapter = SpinnerAdapter(userList)
        binding.customerSpinner.adapter = spinnerAdapter

        val selectedItemPosition = userList.indexOfFirst { it.customerName == editCustomerName }
        binding.customerSpinner.setSelection(selectedItemPosition)

    }
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initClick() {
        //Update Detect
        if(entryUpdateId!=-1) {
            binding.textEntry.text = "Update Entry"
            binding.btnSubmit.text = "Update"
        }
        //Current Date
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        entryCurrentDate = sdf.format(System.currentTimeMillis())
        binding.txtDatePicker.text = entryCurrentDate
        //Radio button Payment Status
        paymentStatus()
        binding.txtCollectionDate.setOnClickListener {
            paymentDatePicker()
        }
        binding.cvDatePicker.setOnClickListener {
            entryDatePicker()
        }
        binding.btnSubmit.setOnClickListener {
            addOrUpdateEntry()
        }
        binding.edtProductPrice.addTextChangedListener { text ->
            val productQuantity = binding.edtProductQuantity.text.toString()
            if (productQuantity.isNotEmpty() && text?.isNotEmpty() == true) {
                txtTotalAmount = (productQuantity.toInt() * text.toString().toInt()).toString()
                binding.txtTotalAmount.text = "₹$txtTotalAmount"
            } else {
                binding.txtTotalAmount.text = "₹0"
            }
        }
        binding.edtProductQuantity.addTextChangedListener { text ->
            val productPrice = binding.edtProductPrice.text.toString()
            if (productPrice.isNotEmpty() && text?.isNotEmpty() == true) {
                txtTotalAmount = (productPrice.toInt() * text.toString().toInt()).toString()
                binding.txtTotalAmount.text = "₹$txtTotalAmount"
            } else {
                binding.txtTotalAmount.text = "₹0"
            }
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun addOrUpdateEntry() {
        val productQuantity = binding.edtProductQuantity.text.toString()
        val productPrice = binding.edtProductPrice.text.toString()
        val productName = binding.edtProductName.text.toString()
        if (productName.isEmpty()) {
            binding.txtProductNameLayout.isEnabled = true
            binding.txtProductNameLayout.error = "Enter Product Name"
        } else if (productQuantity.isEmpty()) {
            binding.txtProductQuantityLayout.isEnabled = true
            binding.txtProductQuantityLayout.error = "Enter Product Quantity"
        } else if (productPrice.isEmpty()) {
            binding.txtProductPriceLayout.isEnabled = true
            binding.txtProductPriceLayout.error = "Enter Product Price"
        } else if(binding.customerSpinner.selectedItemPosition==0){
            Toast.makeText(this, "Select Customer", Toast.LENGTH_SHORT).show()
        }
        else {
            binding.txtProductNameLayout.isEnabled = false
            binding.txtProductQuantityLayout.isEnabled = false
            binding.txtProductPriceLayout.isEnabled = false
            val customerNameEntity = userList[binding.customerSpinner.selectedItemPosition]
            val entryEntity = EntryEntity(
                entryCustomerId = customerNameEntity.customerId,
                entryProductName = productName,
                entryProductQuantity = productQuantity,
                entryProductPrice = productPrice,
                entryProductAmount = txtTotalAmount,
                entryProductStatus = txtProductStatus,
                entryProductDate = entryCurrentDate,
                entryCollectionDate = formattedDate
            )
            Log.e("TAG", "addOrUpdateEntry: $txtProductStatus")
            initDb(this)
            if (entryUpdateId == -1) {
                db!!.dao().entryInsert(entryEntity)
                Toast.makeText(this, "Entry Added Successfully", Toast.LENGTH_SHORT).show()
            } else {
                entryEntity.entryId=entryUpdateId
                db!!.dao().entryUpdate(entryEntity)
                Toast.makeText(this, "Entry Updated Successfully", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "addOrUpdateEntry: $entryEntity")
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun paymentDatePicker() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formattedDate = dateFormat.format(selectedDate.time)
            binding.txtCollectionDate.text = "Selected Date: $formattedDate"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, "Cancel"
        ) { _, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
            binding.rbDebit.isChecked=true
            txtProductStatus=1
            formattedDate=""
            }
        }
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
        binding.txtCollectionDate.setCompoundDrawables(null, null, null, null)
    }
    @SuppressLint("SetTextI18n")
    private fun paymentStatus() {
        binding.rgPayment.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbDebit.id -> {
                    binding.txtCollectionDate.visibility = View.GONE
                    txtProductStatus = 1
                    formattedDate = ""
                }
                binding.rbCredit.id -> {
                    binding.txtCollectionDate.visibility = View.VISIBLE
                    txtProductStatus = 2
                    paymentDatePicker()
                }
            }
            Log.e("TAG", "txtProductStatus: $txtProductStatus")
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun entryDatePicker() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.
            getDefault())
            entryCurrentDate = dateFormat.format(selectedDate.time)
            binding.txtDatePicker.text = entryCurrentDate
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }
    @Suppress("SENSELESS_COMPARISON")
    @SuppressLint("SetTextI18n")
    private fun getEditData() {
        editCustomerName = intent.getStringExtra("editCustomerName") ?:""
        val productName = intent.getStringExtra("editProductName")
        val productQuantity = intent.getStringExtra("editProductQuantity")
        val productPrice = intent.getStringExtra("editProductPrice")
        txtTotalAmount = intent.getStringExtra("editProductAmount")?:""
        val productDate = intent.getStringExtra("editProductDate")
        val productCollectionDate = intent.getStringExtra("editCollectionDate")
        txtProductStatus = intent.getIntExtra("editProductStatus", 1)
        entryUpdateId = intent.getIntExtra("editUpdateId", -1)

//        Log.e("Customer", "getEditData: ${editCustomerName}" )

        binding.edtProductName.setText(productName)
        binding.edtProductQuantity.setText(productQuantity)
        binding.edtProductPrice.setText(productPrice)
        binding.txtDatePicker.text = productDate
        binding.txtCollectionDate.text = productCollectionDate ?: ""
        formattedDate = productCollectionDate ?: ""
        if(txtTotalAmount==null){
            binding.txtTotalAmount.text="₹0"
        }
        else {
            binding.txtTotalAmount.text = "₹$txtTotalAmount"
        }
        paymentStatus()
        if (txtProductStatus==1) {
            binding.rbDebit.isChecked = true
            binding.txtCollectionDate.visibility = View.GONE
        } else {
            binding.rbCredit.isChecked = true
            binding.txtCollectionDate.visibility = View.VISIBLE
        }
    }
}