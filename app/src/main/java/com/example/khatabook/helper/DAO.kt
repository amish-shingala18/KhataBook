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
}
