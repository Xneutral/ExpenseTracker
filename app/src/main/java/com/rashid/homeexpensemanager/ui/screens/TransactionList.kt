package com.rashid.homeexpensemanager.ui.screens

import androidx.compose.animation.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rashid.homeexpensemanager.R
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    transactions: List<Transaction>, onAddTransactionClick: () -> Unit,
    onEditTransactionClick: (transaction: Transaction) -> Unit,
    onDeleteTransactionClick: (transaction: Transaction) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Transactions") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransactionClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val totalExpenses = transactions.filterNot { it.isIncome }.sumOf { it.amount }
            val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
            val balance = totalIncome - totalExpenses


            BalanceCard(balance = balance, totalIncome = totalIncome, totalExpense = totalExpenses)
            // Show transaction list
            TransactionList(transactions, { editTransaction ->
                onEditTransactionClick(editTransaction)
            },
                { deleteTransaction ->
                    onDeleteTransactionClick(deleteTransaction)
                })
        }
    }
}

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    onEdit: (transaction: Transaction) -> Unit,
    onDelete: (transaction: Transaction) -> Unit,
) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction, { transactionToEdit ->
                onEdit(transactionToEdit)
            }, { transactionToDelete ->
                onDelete(transactionToDelete)
            })
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onEdit: (transaction: Transaction) -> Unit,
    onDelete: (transaction: Transaction) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                Text(text = transaction.description, fontSize = 16.sp)

                Text(
                    text = if (transaction.isIncome) "+PKR${transaction.amount}" else "-PKR${transaction.amount}",
                    fontSize = 16.sp,
                    color = if (transaction.isIncome) Color(0xFF11823B) else Color.Red
                )
            }

            Image(
                painter = painterResource(id = R.drawable.edit_ic),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onEdit(transaction)
                }
                    .height(38.dp)
                    .width(38.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.delete_ic),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onDelete(transaction)
                }
                    .height(38.dp)
                    .width(38.dp)
            )
        }
    }
}

@Composable
fun BalanceCard(balance: Double, totalIncome: Double, totalExpense: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Balance Information
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "PKR${String.format("%.2f", balance)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = if (balance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(1.dp),
                color = Color.LightGray
            )

            // Income and Expense Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "+PKR${String.format("%.2f", totalIncome)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Expense",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "-PKR${String.format("%.2f", totalExpense)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
