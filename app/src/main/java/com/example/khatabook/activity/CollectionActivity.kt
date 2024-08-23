package com.example.khatabook.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.CollectionAdapter
import com.example.khatabook.databinding.ActivityCollectionBinding
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Locale

class CollectionActivity : AppCompatActivity() {
    private var collectionCurrentDate: String=""
    private var collectionDateFormatted: String=""
    private lateinit var binding : ActivityCollectionBinding
    private var collectionList= mutableListOf<TransactionEntity>()
    private val collectionCalendar = Calendar.getInstance()
    private lateinit var collectionAdapter: CollectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRv()
        initClick()
    }
    @SuppressLint("SimpleDateFormat")
    private fun initClick(){
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        collectionCurrentDate = sdf.format(System.currentTimeMillis())
        binding.txtCollectionSelectDate.text=collectionCurrentDate
        binding.cvSetCollectionDate.setOnClickListener{
            datePicker()
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun initRv(){
        collectionAdapter= CollectionAdapter(collectionList)
        binding.rvCollectionCustomer.adapter=collectionAdapter
    }
    private fun datePicker(){
        val datePicker = DatePickerDialog(this@CollectionActivity,{ _, year, month, dayOfMonth ->
            val dateSelected = Calendar.getInstance()
            dateSelected.set(year,month,dayOfMonth)
            val formatDate= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            collectionDateFormatted = formatDate.format(dateSelected.time)
            binding.txtCollectionSelectDate.text= collectionDateFormatted
            collectionList = db!!.dao().collectionRead(collectionDateFormatted)
            collectionAdapter.dataChanged(collectionList)
            data()

        },collectionCalendar.get(Calendar.YEAR),
            collectionCalendar.get(Calendar.MONTH),
            collectionCalendar.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    private fun data() {
        if(collectionList.isEmpty()){
            binding.imgNoCollection.visibility=View.VISIBLE
            binding.txtNoCollection.visibility=View.VISIBLE
            binding.rvCollectionCustomer.visibility= View.GONE
            binding.main.setBackgroundColor(ContextCompat.getColor(binding.main.context,R.color.bgEmpty))
        }
        else{
            binding.imgNoCollection.visibility=View.GONE
            binding.txtNoCollection.visibility=View.GONE
            binding.rvCollectionCustomer.visibility= View.VISIBLE
            binding.main.setBackgroundColor(ContextCompat.getColor(binding.main.context,R.color.white))
        }
    }
    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        initDb(this)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        collectionCurrentDate = sdf.format(System.currentTimeMillis())
        collectionList = db!!.dao().collectionRead(collectionDateFormatted)
        collectionAdapter.dataChanged(collectionList)
        data()
        super.onResume()
    }
}