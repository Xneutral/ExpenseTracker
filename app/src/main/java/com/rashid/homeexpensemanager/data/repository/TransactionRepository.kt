package com.rashid.homeexpensemanager.data.repository

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.data.room.TransactionDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TransactionRepository {


    suspend fun addTransaction(context: Context, transaction: Transaction) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                val db = TransactionDb.getDatabase(context)
                db.transactionDao().insertTransaction(transaction)
            }
    }

    fun getAllTransaction(context: Context): Flow<List<Transaction>> {
        val db = TransactionDb.getDatabase(context)
        return db.transactionDao().getAllTransactions()
    }


    fun getTransactionById(context: Context, id: Int) : Flow<Transaction>{
        val db = TransactionDb.getDatabase(context)
        return db.transactionDao().getTransactionById(id)
    }


    suspend fun deleteTransaction(context: Context, id: Int){
        CoroutineScope(Dispatchers.IO)
            .launch {
                val db = TransactionDb.getDatabase(context)
                db.transactionDao().deleteTransactionById(id)
            }
    }


}