package com.example.khatabook.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.HomeAdapter
import com.example.khatabook.databinding.FragmentHomeBinding
import com.example.khatabook.helper.DbRoomHelper.Companion.db
import com.example.khatabook.helper.DbRoomHelper.Companion.initDb
import com.example.khatabook.helper.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private var dateFormatted: String=""
    private var currentDate:String=""
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private var transactionList = mutableListOf<TransactionEntity>()
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        exit()
        initRv()
        initClick()
        return binding.root
    }
    private fun exit() {
        activity?.onBackPressedDispatcher?.addCallback {
            requireActivity().finish()
        }
    }

    private fun initRv(){
        homeAdapter = HomeAdapter(transactionList)
        binding.rvTodayTrans.adapter=homeAdapter
    }
    @SuppressLint("ResourceAsColor")
    private fun data(){
        if(transactionList.isEmpty()){
            binding.imgNoData.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.VISIBLE
            binding.txtSeeAll.visibility=View.VISIBLE
            binding.rvTodayTrans.visibility=View.GONE
            binding.homeFragment.setBackgroundColor(ContextCompat.getColor(binding.homeFragment.context,R.color.bgEmpty))
        }
        else{
            binding.imgNoData.visibility=View.GONE
            binding.txtNoData.visibility=View.GONE
            binding.txtSeeAll .visibility=View.VISIBLE
            binding.rvTodayTrans.visibility=View.VISIBLE
            binding.homeFragment.setBackgroundColor(ContextCompat.getColor(binding.homeFragment.context,R.color.white))
        }
    }
    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        initDb(requireActivity())
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        currentDate = sdf.format(System.currentTimeMillis())
        transactionList = db!!.dao().allRead(currentDate)
        homeAdapter.dataChanged(transactionList)
        data()
        super.onResume()
    }
    private fun initClick(){
        binding.txtSeeAll.setOnClickListener {
            datePicker()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun datePicker(){
        val datePicker = DatePickerDialog(requireActivity(),{ _, year, month, dayOfMonth ->
            val dateSelected = Calendar.getInstance()
            dateSelected.set(year,month,dayOfMonth)
            val formatDate=SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())

            dateFormatted = formatDate.format(dateSelected.time)
            binding.txtSeeAll.text="Selected Date : $dateFormatted"
            transactionList = db!!.dao().allRead(dateFormatted)
            homeAdapter.dataChanged(transactionList)
            data()

        },calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.maxDate = calendar.getTimeInMillis()
        datePicker.show()
    }
}