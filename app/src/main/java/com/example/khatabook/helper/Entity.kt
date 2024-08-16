package com.example.khatabook.helper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    var customerId:Int=0,
    @ColumnInfo
    var customerName:String,
    @ColumnInfo
    var customerMobile:String
)

@Entity(tableName = "entry")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    var entryId:Int=0,
    @ColumnInfo
    var customerName:String,
    @ColumnInfo
    var productName:String,
    @ColumnInfo
    var productQuantity:Int,
    @ColumnInfo
    var productPrice:Int,
    @ColumnInfo
    var productAmount:Int
)