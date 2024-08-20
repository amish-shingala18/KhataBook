package com.example.khatabook.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.khatabook.R
import com.example.khatabook.adapter.AllUserAdapter
import com.example.khatabook.databinding.FragmentAllUserBinding
import com.example.khatabook.helper.CustomerEntity
import com.example.khatabook.helper.DbRoomHelper

class AllUserFragment : Fragment() {
    private lateinit var permissionLauncher : ActivityResultLauncher<Array<String>>
    private lateinit var binding: FragmentAllUserBinding
    private lateinit var allUserAdapter: AllUserAdapter
    private var allUserList = mutableListOf<CustomerEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding=FragmentAllUserBinding.inflate(layoutInflater)
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if (it[Manifest.permission.CALL_PHONE]==true && it[Manifest.permission.SEND_SMS]==true) {

            }
            else{

            }
        }

        initRv()
        return binding.root
    }
    private fun initRv(){
        allUserAdapter=AllUserAdapter(allUserList, permissionLauncher)
        binding.rvCustomers.adapter=allUserAdapter
    }
    override fun onResume() {
        val db = DbRoomHelper.initDb(requireContext())
        allUserList = db.dao().customerRead()
        allUserAdapter.dataSetChanged(allUserList)
        userEmptyData()
        super.onResume()
    }
    @SuppressLint("ResourceAsColor")
    private fun userEmptyData(){
        if (allUserList.isEmpty()){
            binding.imgNoCustomer.visibility=View.VISIBLE
            binding.txtNoCustomer.visibility=View.VISIBLE
            binding.rvCustomers.visibility=View.GONE
            binding.allUserFragment.setBackgroundColor(ContextCompat.getColor(binding.allUserFragment.context,R.color.bgEmpty))
        }
        else{
            binding.imgNoCustomer.visibility=View.GONE
            binding.txtNoCustomer.visibility=View.GONE
            binding.rvCustomers.visibility=View.VISIBLE
            binding.allUserFragment.setBackgroundColor(ContextCompat.getColor(binding.allUserFragment.context,R.color.white))
        }
    }


}