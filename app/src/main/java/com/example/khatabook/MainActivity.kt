package com.example.khatabook

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.khatabook.activity.AddEntryActivity
import com.example.khatabook.activity.AddUserActivity
import com.example.khatabook.activity.CollectionActivity
import com.example.khatabook.adapter.TabAdapter
import com.example.khatabook.databinding.ActivityMainBinding
import com.example.khatabook.helper.SharedHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var sharedHelper = SharedHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startComName()
        tabLayout()
        initClick()
        bottomSheet()

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if(it[Manifest.permission.CALL_PHONE] == true && it[Manifest.permission.SEND_SMS] == true)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        val status = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)

        if(status==PackageManager.PERMISSION_GRANTED) {}
        else if(ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.CALL_PHONE)){}
        else
        {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS))
        }

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
        binding.imgCollection.setOnClickListener {
            val collectionIntent = Intent(this@MainActivity, CollectionActivity::class.java)
            startActivity(collectionIntent)
        }
    }
    @SuppressLint("InflateParams")
    private fun bottomSheet(){
        binding.lnrCompany.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialog.setCancelable(true)
            dialog.setContentView(bottomView)
            val edtCompanyName = bottomView.findViewById<EditText>(R.id.edtCompanyName)
            val txtCompanyLayout = bottomView.findViewById<TextInputLayout>(R.id.txtCompanyLayout)
            val btnSave = bottomView.findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener{
                val companyName = edtCompanyName.text.toString()
                if (companyName.isEmpty()) {
                    txtCompanyLayout.error = "Enter Company Name"
                } else {
                    addComName(companyName)
                    startComName()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
    private fun addComName(name:String){
        sharedHelper.setCompanyName(this,name)
    }
    @SuppressLint("SetTextI18n")
    private fun startComName(){
        val companyName = sharedHelper.getCompanyName(this)
        binding.txtCompany.text = companyName
    }
}
