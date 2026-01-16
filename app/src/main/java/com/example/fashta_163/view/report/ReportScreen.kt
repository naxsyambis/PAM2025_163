package com.example.fashta_163.view.report

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.report.ReportViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    navigateBack: () -> Unit,
    viewModel: ReportViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
        viewModel.loadReport()
    }

    var expandedType by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laporan Stok") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            /* ================= FILTER PRODUK ================= */
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = viewModel.selectedProduct?.product_name ?: "Pilih Produk",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Produk") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))

            /* ================= FILTER ITEM ================= */
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = viewModel.selectedItem?.product_name ?: "Pilih Item Produk",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Item Produk") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))

            /* ================= FILTER TYPE ================= */
            ExposedDropdownMenuBox(
                expanded = expandedType,
                onExpandedChange = { expandedType = !expandedType }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedType ?: "IN & OUT",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Transaksi") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expandedType)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false }
                ) {
                    listOf("IN", "OUT").forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                viewModel.onTypeSelected(it)
                                expandedType = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ================= DATE PICKER ================= */
            DatePickerField(
                label = "Tanggal Mulai",
                selectedDate = viewModel.startDate,
                onDateSelected = viewModel::onStartDateSelected,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            DatePickerField(
                label = "Tanggal Akhir",
                selectedDate = viewModel.endDate,
                onDateSelected = viewModel::onEndDateSelected,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            /* ================= BUTTON FILTER ================= */
            Button(
                onClick = { viewModel.loadReport() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Terapkan Filter")
            }

            Spacer(Modifier.height(12.dp))

            /* ================= DOWNLOAD ================= */
            OutlinedButton(
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://10.0.2.2:8080/FASH_API/reports/report_export.php")
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Download Laporan")
            }

            Spacer(Modifier.height(16.dp))

            /* ================= LIST LAPORAN ================= */
            LazyColumn {
                items(viewModel.reports) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                "${item.product_name} (${item.size}/${item.color})",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("${item.movement_type} - ${item.quantity}")
                            Text("Alasan: ${item.reason}")
                            Text(
                                item.created_at,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   DATE PICKER (INLINE, SATU FILE)
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()

    OutlinedTextField(
        value = selectedDate?.toString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = modifier,
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, null)
            }
        }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = state.selectedDateMillis
                    if (millis != null) {
                        onDateSelected(
                            Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = state)
        }
    }
}