package com.example.khatabook.helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {
    @Insert
    fun customerInsert(customerEntity: CustomerEntity)
    @Update
    fun customerUpdate(customerEntity: CustomerEntity)
    @Delete
    fun customerDelete(customerEntity: CustomerEntity)
    @Query("SELECT * FROM customer")
    fun customerRead() : MutableList<CustomerEntity>

    @Insert
    fun entryInsert(entryEntity: EntryEntity)
    @Update
    fun entryUpdate(entryEntity: EntryEntity)
    @Delete
    fun entryDelete(entryEntity: EntryEntity)
    @Query("SELECT * FROM entry")
    fun entryRead() : MutableList<EntryEntity>

    @Query("SELECT * FROM entry INNER JOIN customer ON entry.entryCustomerId == customer.customerId WHERE entry.entryProductDate== :currentDate")
    fun allRead(currentDate:String): MutableList<TransactionEntity>

    @Query("SELECT * FROM entry INNER JOIN customer ON entry.entryCustomerId == customer.customerId WHERE entry.entryCollectionDate== :collectionDate")
    fun collectionRead(collectionDate:String): MutableList<TransactionEntity>
    @Query("SELECT * FROM entry INNER JOIN customer ON entry.entryCustomerId == customer.customerId WHERE entry.entryCustomerId== :intentId")
    fun transactionRead(intentId: Int): MutableList<TransactionEntity>

    @Query("SELECT SUM(entryProductAmount) FROM entry WHERE entryProductStatus==1 AND entry.entryProductDate==:currentDate")
    fun debitRead(currentDate: String):String
    @Query("SELECT SUM(entryProductAmount) FROM entry WHERE entryProductStatus==2 AND entry.entryProductDate==:currentDate")
    fun creditRead(currentDate: String):String
}
