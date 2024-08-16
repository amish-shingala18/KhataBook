package com.example.khatabook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.khatabook.adapter.AllUserAdapter
import com.example.khatabook.databinding.FragmentAllUserBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper

class AllUserFragment : Fragment() {
    private lateinit var binding: FragmentAllUserBinding
    private lateinit var allUserAdapter: AllUserAdapter
    private var allUserList = mutableListOf<CustomerEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding=FragmentAllUserBinding.inflate(layoutInflater)
        initRv()
        return binding.root
    }
    private fun initRv(){
        allUserAdapter=AllUserAdapter(allUserList)
        binding.rvCustomers.adapter=allUserAdapter
    }
    override fun onResume() {
        val db = DbRoomHelper.initDb(requireContext())
        allUserList = db.dao().customerRead()
        allUserAdapter.dataSetChanged(allUserList)
        super.onResume()
    }
}