package com.rashid.homeexpensemanager.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel  : ViewModel() {

    // Flow of transactions
    var transactions = mutableStateOf<List<Transaction>>(emptyList())
    private set

    private val transactionRepository = TransactionRepository()

    var currentTransaction = mutableStateOf<Transaction?>(null)
        private set

    // Add a new transaction
    fun addTransaction(context : Context, transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.addTransaction(context, transaction)
        }
    }

    fun getAllTransaction(context: Context){
        viewModelScope.launch {
            transactionRepository.getAllTransaction(context).collect{
                transactions.value = it
            }
        }
    }

    fun getTransactionById(context: Context, id: Int){
        viewModelScope.launch {
            transactionRepository.getTransactionById(context, id).collect{
                currentTransaction.value = it
            }
        }
    }

    fun deleteTransactionById(context: Context, id: Int){
        viewModelScope.launch {
            transactionRepository.deleteTransaction(context, id)
        }
    }


}