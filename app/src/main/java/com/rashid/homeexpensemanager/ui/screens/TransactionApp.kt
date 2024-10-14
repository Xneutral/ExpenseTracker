package com.rashid.homeexpensemanager.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.viewmodel.TransactionViewModel

@Composable
fun TransactionApp(viewModel: TransactionViewModel) {
    val navController = rememberNavController()
    var transactions  = viewModel.transactions.value
    viewModel.getAllTransaction(LocalContext.current)
    NavHost(navController = navController, startDestination = "transaction_list") {
        // First screen: List of transactions and total expense
        composable("transaction_list") {
            TransactionListScreen(
                transactions = transactions,
                onAddTransactionClick = { navController.navigate("add_transaction") }
            )
        }

        // Second screen: Add a transaction
        composable("add_transaction") {
            AddTransactionScreen(
                onAddTransaction = {
//                    transactions = transactions + transaction
                    navController.navigateUp() // Navigate back to the list screen
                },
                onCancel = { navController.navigateUp()
                },
                viewModel
            )
        }
    }
}