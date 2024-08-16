package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.SpinnerAdapter
import com.example.khatabook.databinding.ActivityAddEntryBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEntryBinding
    private var userList = mutableListOf<CustomerEntity>()
    private lateinit var spinnerAdapter : SpinnerAdapter
    private val calendar = Calendar.getInstance()
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
        keyboardDetector()
    }
    private fun initRv() {
        userList = initDb(this).dao().customerRead()
        spinnerAdapter = SpinnerAdapter(userList)
        binding.customerSpinner.adapter = spinnerAdapter
        if (binding.customerSpinner.selectedItemPosition==0){
        }
    }
    private fun initClick(){
        paymentStatus()
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
//                binding.txtProductNameLayout.setEnabled(false)
//                binding.txtProductQuantityLayout.setEnabled(false)
//                binding.txtProductPriceLayout.setEnabled(false)
                Toast.makeText(this, "Entry Added Successfully", Toast.LENGTH_SHORT).show()
                val txtTotalAmount=productQuantity.toInt()*productPrice.toInt()
                binding.txtTotalAmount.text= txtTotalAmount.toString()
            }
        }
        binding.txtSelectDate.setOnClickListener {
            datePicker()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun datePicker(){
        val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            binding.txtSelectDate.text="Selected Date : $formattedDate"
        },calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
        binding.txtSelectDate.setCompoundDrawables(null,null,null,null)
    }
    private fun paymentStatus(){
        binding.rgPayment.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId==binding.rbDebit.id){
                binding.txtSelectDate.visibility=View.GONE
            }
            else{
                binding.txtSelectDate.visibility=View.VISIBLE
            }
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
}