package com.example.khatabook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.khatabook.adapter.HomeAdapter
import com.example.khatabook.databinding.FragmentHomeBinding
import com.example.khatabook.helper.TransactionEntity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private var transactionList = mutableListOf<TransactionEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        initRv()
        data()
        return binding.root
    }
    private fun initRv(){
        adapter = HomeAdapter()
        binding.rvTodayTrans.adapter=adapter
    }
    private fun data(){
        if(transactionList.isEmpty()){
            binding.imgNoData.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.VISIBLE
            binding.txtSeeAll.visibility=View.GONE
            binding.txtTodayTransaction.visibility=View.GONE
            binding.rvTodayTrans.visibility=View.GONE
        }
        else{
            binding.imgNoData.visibility=View.GONE
            binding.txtNoData.visibility=View.GONE
            binding.txtSeeAll .visibility=View.VISIBLE
            binding.
            txtTodayTransaction.visibility=View.VISIBLE
            binding.rvTodayTrans.visibility=View.VISIBLE
        }
    }
}