package com.rashid.homeexpensemanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rashid.homeexpensemanager.data.model.Transaction
import com.rashid.homeexpensemanager.viewmodel.TransactionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onAddTransaction: (addTransaction: Transaction) -> Unit,
    onCancel: () -> Unit,
    currentTransaction: Transaction?
) {
    var description by remember { mutableStateOf(currentTransaction?.description ?: "") }
    var amount by remember {
        mutableStateOf(
            if (currentTransaction?.amount.toString() == "null") {
                ""
            } else {
                currentTransaction?.amount.toString()
            }
        )
    }
    var isIncome by remember { mutableStateOf(currentTransaction?.isIncome ?: true) }




    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Transaction") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 2.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 2.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Income")
                RadioButton(selected = isIncome, onClick = { isIncome = true })
                Spacer(modifier = Modifier.width(16.dp))
                Text("Expense")
                RadioButton(selected = !isIncome, onClick = { isIncome = false })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    if (description.isBlank() || amount.isBlank()) return@Button
                    onAddTransaction(
                        Transaction(
                            id = currentTransaction?.id ?: 0,
                            description = description,
                            amount = amount.toDouble(),
                            isIncome = isIncome
                        )
                    )
                }) {
                    Text("Add")
                }
                Button(onClick = onCancel) {
                    Text("Cancel")
                }
            }
        }

    }
}