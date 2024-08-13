package com.example.khatabook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.activity.AddEntryActivity
import com.example.khatabook.activity.AddUserActivity
import com.example.khatabook.adapter.TabAdapter
import com.example.khatabook.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tabLayout()
        initClick()
        bottomSheet()
    }
    private fun tabLayout(){
        val tabAdapter = TabAdapter(this)
        binding.vpKhataBook.adapter=tabAdapter
        TabLayoutMediator(binding.tabLayout,binding.vpKhataBook){ tab,position ->
            when(position){
                0-> tab.text="Home"
                1-> tab.text="All Users"
            }
        }.attach()
    }
    private fun initClick(){
        binding.btnAddUser.setOnClickListener {
            val addUserIntent = Intent(this@MainActivity, AddUserActivity::class.java)
            startActivity(addUserIntent)
        }
        binding.btnAddEntry.setOnClickListener {
            val addEntryIntent = Intent(this@MainActivity, AddEntryActivity::class.java)
            startActivity(addEntryIntent)
        }
    }
    private fun bottomSheet(){
        binding.lnrCompany.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialog.setCancelable(true)
            dialog.setContentView(bottomView)
            val edtCompanyName = bottomView.findViewById<EditText>(R.id.edtCompanyName)
            val txtCompanyLayout = bottomView.findViewById<TextInputLayout>(R.id.txtCompanyLayout)
            val btnSave = bottomView.findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener {
                val companyName = edtCompanyName.text.toString()
                if (companyName.isEmpty()) {
                    txtCompanyLayout.error = "Enter Company Name"
                } else {
                    binding.txtCompany.text = companyName
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
}
