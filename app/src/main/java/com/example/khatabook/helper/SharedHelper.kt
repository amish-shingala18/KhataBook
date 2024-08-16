package com.example.khatabook.helper

import android.app.Activity

class SharedHelper {
    fun setCompanyName(activity : Activity, companyName : String){
        val pref = activity.getSharedPreferences("company", Activity.MODE_PRIVATE)
        val editor= pref.edit()
        editor.putString("company",companyName)
        editor.apply()
    }
    fun getCompanyName(activity: Activity):String{
        val pref = activity.getSharedPreferences("company", Activity.MODE_PRIVATE)
        return pref.getString("company","")!!
    }
}
