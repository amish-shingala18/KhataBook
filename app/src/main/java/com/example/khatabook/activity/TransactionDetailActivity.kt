package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.databinding.ActivityTransactionDetailBinding
import com.example.khatabook.fragment.HomeFragment.Companion.transactionList
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.EntryEntity
import com.example.khatabook.helper.TransactionEntity

class TransactionDetailActivity : AppCompatActivity() {
    private var transactionCustomerMobile:String?=""
    private var transactionEntryProductId=0
    private var transactionProductStatus: Int=1
    private var transactionProductAmount: String = ""
    private var transactionProductDate: String?=""
    private var transactionProductPrice: String = ""
    private var transactionCollectionDate: String=""
    private var transactionProductQuantity: String=""
    private var transactionProductName: String?=""
    private var transactionCustomerName: String?=""
    private var transactionIntentId: Int=-1
    private lateinit var binding: ActivityTransactionDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initClick()
        getTransactionData()
        //getCustomerEntryData()
    }
    private fun initClick(){
        binding.imgTransactionBack.setOnClickListener {
            finish()
        }
        binding.btnDeleteEntry.setOnClickListener {
            deleteEntry()
        }
        binding.btnUpdateEntry.setOnClickListener {
            editEntryData()
        }
    }
    private fun deleteEntry() {
        initDb(this)
        val transactionEntity = TransactionEntity(entryId = transactionIntentId,
            customerMobile = transactionCustomerMobile!!,
            entryCustomerId=transactionEntryProductId,
            entryProductName=transactionProductName!!,
            entryProductQuantity=transactionProductQuantity,
            entryProductPrice=transactionProductPrice,
            entryProductAmount = transactionProductAmount,
            entryProductStatus = transactionProductStatus,
            entryProductDate = transactionProductDate!!,
            entryCollectionDate = transactionCollectionDate,
            customerName = transactionCustomerName!!)
        val entryEntity = EntryEntity(
            entryId = transactionIntentId,
            entryCustomerId = transactionEntryProductId,
            entryProductName = transactionProductName!!,
            entryProductQuantity = transactionProductQuantity,
            entryProductPrice = transactionProductPrice,
            entryProductAmount = transactionProductAmount,
            entryProductStatus = transactionProductStatus,
            entryProductDate = transactionProductDate!!,
            entryCollectionDate = transactionCollectionDate
        )
        db!!.dao().entryDelete(entryEntity)
        transactionList.remove(transactionEntity)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun getTransactionData(){
        transactionIntentId=intent.getIntExtra("transactionId",-1)
        transactionEntryProductId=intent.getIntExtra("transactionEntryProductId",0)
        transactionCustomerName=intent.getStringExtra("transactionCustomerName")
        transactionProductName=intent.getStringExtra("transactionProductName")
        transactionProductQuantity= intent.getStringExtra("transactionProductQuantity")!!
        transactionProductPrice= intent.getStringExtra("transactionProductPrice")!!
        transactionProductAmount= intent.getStringExtra("transactionProductAmount")!!
        transactionProductStatus=intent.getIntExtra("transactionProductStatus",0)
        transactionProductDate=intent.getStringExtra("transactionProductDate")
        transactionCollectionDate=intent.getStringExtra("transactionCollectionDate")!!
        transactionCustomerMobile=intent.getStringExtra("transactionCustomerMobile")

        binding.txtEntryDate.text="$transactionProductDate"
        binding.txtCustomerName.text=transactionCustomerName
        binding.txtProductName.text=transactionProductName
        binding.txtProductQuantity.text= transactionProductQuantity
        binding.txtProductPrice.text="₹$transactionProductPrice"
        binding.txtProductAmount.text="₹$transactionProductAmount"
        binding.txtTransCollectionDate.text= transactionCollectionDate
    }
    private fun editEntryData(){
        val editTransactionIntent= Intent(this@TransactionDetailActivity,AddEntryActivity::class.java)
        editTransactionIntent.putExtra("editUpdateId",transactionIntentId)
        editTransactionIntent.putExtra("editCustomerName",transactionCustomerName)
        editTransactionIntent.putExtra("editProductName",transactionProductName)
        editTransactionIntent.putExtra("editProductQuantity",transactionProductQuantity)
        editTransactionIntent.putExtra("editProductPrice",transactionProductPrice)
        editTransactionIntent.putExtra("editProductAmount",transactionProductAmount)
        editTransactionIntent.putExtra("editProductStatus",transactionProductStatus)
        editTransactionIntent.putExtra("editProductDate",transactionProductDate)
        editTransactionIntent.putExtra("editCollectionDate",transactionCollectionDate)
        startActivity(editTransactionIntent)
    }
    //Getting Data from CustomerEntriesAdapter
    //@SuppressLint("SetTextI18n")
//    private fun getCustomerEntryData(){
//        transactionIntentId=intent.getIntExtra("customerEntryCustomerId",-1)
//        transactionEntryProductId=intent.getIntExtra("customerEntryId",0)
//        transactionCustomerName=intent.getStringExtra("customerEntryCustomerName")
//        transactionProductName=intent.getStringExtra("customerEntryProductName")
//        transactionProductQuantity= intent.getStringExtra("customerEntryProductQuantity")!!
//        transactionProductPrice= intent.getStringExtra("customerEntryProductPrice")!!
//        transactionProductAmount= intent.getStringExtra("customerEntryProductAmount")!!
//        transactionProductStatus=intent.getIntExtra("customerEntryProductStatus",0)
//        transactionProductDate=intent.getStringExtra("customerEntryProductDate")
//        transactionCollectionDate=intent.getStringExtra("customerEntryCollectionDate")!!
//        transactionCustomerMobile=intent.getStringExtra("customerEntryCustomerMobile")
//
//        binding.txtEntryDate.text="$transactionProductDate"
//        binding.txtCustomerName.text=transactionCustomerName
//        binding.txtProductName.text=transactionProductName
//        binding.txtProductQuantity.text= transactionProductQuantity
//        binding.txtProductPrice.text="₹$transactionProductPrice"
//        binding.txtProductAmount.text="₹$transactionProductAmount"
//        binding.txtTransCollectionDate.text= transactionCollectionDate
//    }
}
