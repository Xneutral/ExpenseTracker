package com.rashid.homeexpensemanager.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.data.room.TransactionDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel  : ViewModel() {

    // Flow of transactions
    var transactions = mutableStateOf<List<Transaction>>(emptyList())
    private set

    // Add a new transaction
    fun addTransaction(context : Context, transaction: Transaction) {
        viewModelScope.launch {
            val db = TransactionDb.getDatabase(context)
            db.transactionDao().insertTransaction(transaction)
        }
    }

    fun getAllTransaction(context: Context){
        viewModelScope.launch {
            val db = TransactionDb.getDatabase(context)
            db.transactionDao().getAllTransactions().collect{
                transactions.value = it
            }
        }
    }


}