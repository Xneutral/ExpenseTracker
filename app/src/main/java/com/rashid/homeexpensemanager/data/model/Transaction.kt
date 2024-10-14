package com.rashid.homeexpensemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions_table")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val description: String,
    val amount: Double,
    val isIncome: Boolean // true for income and false for expense
)


