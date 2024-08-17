package com.example.khatabook.helper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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

@Entity(tableName = "entry",
//    foreignKeys = [ForeignKey(entity = CustomerEntity::class,
//    parentColumns = [""],
//    childColumns = [""],
//    onDelete = ForeignKey.CASCADE,
//    onUpdate = ForeignKey.CASCADE)]
)
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    var entryId:Int=0,
    @ColumnInfo
    var entryCustomerName:String,
    @ColumnInfo
    var entryProductName:String,
    @ColumnInfo
    var entryProductQuantity:Int,
    @ColumnInfo
    var entryProductPrice:Int,
    @ColumnInfo
    var entryProductAmount:Int,
    @ColumnInfo
    var entryProductStatus:Int
)

data class TransactionEntity(
    var charFirstName:String,
    var userName:String,
    var userProductName:String,
    var userProductAmount:Int,
    var userProductStatus:Int
)
