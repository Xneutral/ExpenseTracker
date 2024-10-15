package com.rashid.homeexpensemanager.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.viewmodel.TransactionViewModel

@Composable
fun TransactionApp(viewModel: TransactionViewModel) {

    val navController = rememberNavController()
    val transactions = viewModel.transactions.value
    val context = LocalContext.current

    var currentTransaction = viewModel.currentTransaction.value

    viewModel.getAllTransaction(context)

    NavHost(navController = navController, startDestination = "transaction_list") {
        // First screen: List of transactions and total expense
        composable("transaction_list") {
            TransactionListScreen(
                transactions = transactions,
                onAddTransactionClick = {
                    currentTransaction = null
                    navController.navigate("add_transaction")
                },
                onEditTransactionClick = { editTransaction ->
                    viewModel.getTransactionById(context, editTransaction.id)
                    navController.navigate("add_transaction")
                },
                onDeleteTransactionClick = { deleteTransaction ->
                    //delete here
                    viewModel.deleteTransactionById(context, deleteTransaction.id)
                }
            )
        }

        // Second screen: Add a transaction or Edit
        composable(
            "add_transaction"
        ) {
            AddTransactionScreen(
                onAddTransaction = { transaction ->
                    viewModel.addTransaction(context, transaction)
                    navController.navigateUp() // Navigate back to the list screen
                },
                onCancel = {
                    navController.navigateUp()
                },
                currentTransaction
            )
        }
    }
}