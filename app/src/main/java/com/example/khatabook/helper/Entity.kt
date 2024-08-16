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
    var customerMobile:String,
    @ColumnInfo
    var customerFlat:String="",
    @ColumnInfo
    var customerArea:String="",
    @ColumnInfo
    var customerPinCode:String="",
    @ColumnInfo
    var customerCity:String="",
    @ColumnInfo
    var customerState:String=""
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
    var productAmount:Int,
    @ColumnInfo
    var status:Int
)

data class TransactionEntity(
    var charFirstName:String,
    var userName:String,
    var userProductName:String,
    var userProductPrice:Int
)