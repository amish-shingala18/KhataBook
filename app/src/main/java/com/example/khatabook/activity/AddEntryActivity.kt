package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.khatabook.R
import com.example.khatabook.adapter.SpinnerAdapter
import com.example.khatabook.databinding.ActivityAddEntryBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.EntryEntity
import com.example.khatabook.helper.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("NAME_SHADOWING")
class AddEntryActivity : AppCompatActivity() {
    private var entryCurrentDate=""
    private var formattedDate: String=""
    private var txtProductStatus:Int =1
    private var txtTotalAmount: Int=0
    private lateinit var binding: ActivityAddEntryBinding
    private var userList = mutableListOf<CustomerEntity>()
    private lateinit var spinnerAdapter : SpinnerAdapter
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRv()
        initClick()
        keyboardDetector()
    }
    private fun initRv() {
        userList = initDb(this).dao().customerRead()
        spinnerAdapter = SpinnerAdapter(userList)
        binding.customerSpinner.adapter = spinnerAdapter
    }
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initClick(){
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        entryCurrentDate= sdf.format(System.currentTimeMillis())
        binding.txtDatePicker.text=entryCurrentDate
        paymentStatus()
        binding.txtSelectDate.setOnClickListener {
            paymentDatePicker()
        }
        binding.cvDatePicker.setOnClickListener{
            entryDatePicker()
        }
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
                val customerNameEntity = userList[binding.customerSpinner.selectedItemPosition]
                val entryEntity = EntryEntity( entryCustomerId = customerNameEntity.customerId,
                    entryProductName = productName,
                    entryProductQuantity = productQuantity.toInt(),
                    entryProductPrice = productPrice.toInt(),
                    entryProductAmount = txtTotalAmount, entryProductStatus = txtProductStatus, entryProductDate = entryCurrentDate,
                    entryCollectionDate = formattedDate)
                val db = initDb(this)
                db.dao().entryInsert(entryEntity)
                Toast.makeText(this, "Entry Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        binding.edtProductPrice.addTextChangedListener { text->
            val productQuantity = binding.edtProductQuantity.text.toString()
            if (productQuantity.isNotEmpty() && text?.isNotEmpty() == true) {
                txtTotalAmount = productQuantity.toInt() * text.toString().toInt()
                binding.txtTotalAmount.text = "₹$txtTotalAmount"
            }
        }
        binding.edtProductQuantity.addTextChangedListener { text->
            val productPrice = binding.edtProductPrice.text.toString()
            if (productPrice.isNotEmpty() && text?.isNotEmpty() == true) {
                txtTotalAmount = productPrice.toInt() * text.toString().toInt()
                binding.txtTotalAmount.text = "₹$txtTotalAmount"
            }
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun keyboardDetector(){
        binding.main.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.main.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.main.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.10) {
                val view = binding.lnrTotalAmt.layoutParams as ConstraintLayout.LayoutParams
                view.bottomMargin = keypadHeight
                binding.lnrTotalAmt.layoutParams = view
            } else {
                val view = binding.lnrTotalAmt.layoutParams as ConstraintLayout.LayoutParams
                view.bottomMargin = 0
                binding.lnrTotalAmt.layoutParams = view
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun paymentDatePicker(){
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            formattedDate = dateFormat.format(selectedDate.time)
            binding.txtSelectDate.text="Selected Date : $formattedDate"
        },calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = calendar.getTimeInMillis()
        datePickerDialog.show()
        binding.txtSelectDate.setCompoundDrawables(null,null,null,null)
    }
    private fun paymentStatus(){

        binding.rgPayment.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId==binding.rbDebit.id){
                binding.txtSelectDate.visibility=View.GONE
                txtProductStatus=1
                formattedDate = ""
            }
            else{
                binding.txtSelectDate.visibility=View.VISIBLE
                txtProductStatus=2
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun entryDatePicker(){
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            entryCurrentDate= dateFormat.format(selectedDate.time)
            binding.txtDatePicker.text= entryCurrentDate
        },calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = calendar.getTimeInMillis()
        datePickerDialog.show()
    }
}