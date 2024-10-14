package com.rashid.homeexpensemanager.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rashid.homeexpensemanager.data.model.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionDb : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao


    companion object {
        @Volatile
        private var INSTANCE: TransactionDb? = null

        fun getDatabase(context: Context): TransactionDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDb::class.java,
                    "transaction_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}