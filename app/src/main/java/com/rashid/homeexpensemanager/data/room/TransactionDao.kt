package com.rashid.homeexpensemanager.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rashid.homeexpensemanager.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions_table")
    fun getAllTransactions(): Flow<List<Transaction>>
}
