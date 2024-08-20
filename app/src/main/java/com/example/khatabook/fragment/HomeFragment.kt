package com.example.khatabook.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.HomeAdapter
import com.example.khatabook.databinding.FragmentHomeBinding
import com.example.khatabook.helper.EntryEntity
import com.example.khatabook.helper.TransactionEntity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private var transactionList = mutableListOf<TransactionEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        initRv()
        exit()
        return binding.root
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
            binding.txtSeeAll.visibility=View.GONE
            binding.txtTodayTransaction.visibility=View.GONE
            binding.rvTodayTrans.visibility=View.GONE
            binding.homeFragment.setBackgroundColor(ContextCompat.getColor(binding.homeFragment.context,R.color.bgEmpty))
        }
        else{
            binding.imgNoData.visibility=View.GONE
            binding.txtNoData.visibility=View.GONE
            binding.txtSeeAll .visibility=View.VISIBLE
            binding.txtTodayTransaction.visibility=View.VISIBLE
            binding.rvTodayTrans.visibility=View.VISIBLE
            binding.homeFragment.setBackgroundColor(ContextCompat.getColor(binding.homeFragment.context,R.color.white))
        }
    }
    private fun exit(){
        activity?.onBackPressedDispatcher?.addCallback {
            requireActivity().finish()
        }
    }
    override fun onResume() {
        data()
        super.onResume()
    }
}