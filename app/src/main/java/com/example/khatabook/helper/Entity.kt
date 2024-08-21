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
    foreignKeys = [ForeignKey(entity = CustomerEntity::class,
    parentColumns = ["customerId"],
    childColumns = ["entryCustomerId"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)]
)
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    var entryId:Int=0,
    @ColumnInfo(index = true)
    var entryCustomerId:Int,
    @ColumnInfo
    var entryProductName:String,
    @ColumnInfo
    var entryProductQuantity:Int,
    @ColumnInfo
    var entryProductPrice:Int,
    @ColumnInfo
    var entryProductAmount:Int,
    @ColumnInfo
    var entryProductStatus:Int,
    @ColumnInfo
    var entryProductDate:String,
    @ColumnInfo
    var entryCollectionDate:String=""
)

data class  TransactionEntity(
    @ColumnInfo
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
    var customerState:String="",

    @ColumnInfo
    var entryId:Int=0,
    @ColumnInfo
    var entryCustomerId:Int,
    @ColumnInfo
    var entryProductName:String,
    @ColumnInfo
    var entryProductQuantity:Int,
    @ColumnInfo
    var entryProductPrice:Int,
    @ColumnInfo
    var entryProductAmount:Int,
    @ColumnInfo
    var entryProductStatus:Int,
    @ColumnInfo
    var entryProductDate:String,
    @ColumnInfo
    var entryCollectionDate:String=""
)