package com.mednote.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mednote.app.ui.viewmodel.SeizureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SeizureViewModel,
    onNavigateToHistory: () -> Unit,
    onNavigateToExport: () -> Unit
) {
    val todayRecords by viewModel.todayRecords.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    
    var bigChain by remember { mutableStateOf("") }
    var smallChain by remember { mutableStateOf("") }
    var tonic by remember { mutableStateOf("") }
    var clonic by remember { mutableStateOf("") }
    
    var showDatePicker by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "癫痫记录",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = onNavigateToHistory,
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Default.History, contentDescription = "历史记录")
                }
                FloatingActionButton(
                    onClick = onNavigateToExport,
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "导出")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "日期: $selectedDate",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "选择日期",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            if (showDatePicker) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        viewModel.selectDate(date)
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "填写记录",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    InputField(
                        label = "大串",
                        value = bigChain,
                        onValueChange = { bigChain = it },
                        placeholder = "格式：时间,个数"
                    )
                    
                    InputField(
                        label = "小串",
                        value = smallChain,
                        onValueChange = { smallChain = it },
                        placeholder = "格式：时间,个数"
                    )
                    
                    InputField(
                        label = "强直",
                        value = tonic,
                        onValueChange = { tonic = it },
                        placeholder = "格式：时间,个数"
                    )
                    
                    InputField(
                        label = "阵挛",
                        value = clonic,
                        onValueChange = { clonic = it },
                        placeholder = "格式：时间,个数"
                    )
                    
                    Button(
                        onClick = {
                            viewModel.saveRecord(bigChain, smallChain, tonic, clonic)
                            bigChain = ""
                            smallChain = ""
                            tonic = ""
                            clonic = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("保存记录", fontSize = 16.sp)
                    }
                }
            }
            
            if (todayRecords.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "今日记录 (${todayRecords.size})",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        todayRecords.forEach { record ->
                            RecordItem(record = record)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun RecordItem(record: com.mednote.app.data.model.SeizureRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (record.bigChain.isNotEmpty()) {
                RecordRow(label = "大串", value = record.bigChain)
            }
            if (record.smallChain.isNotEmpty()) {
                RecordRow(label = "小串", value = record.smallChain)
            }
            if (record.tonic.isNotEmpty()) {
                RecordRow(label = "强直", value = record.tonic)
            }
            if (record.clonic.isNotEmpty()) {
                RecordRow(label = "阵挛", value = record.clonic)
            }
        }
    }
}

@Composable
fun RecordRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        .format(java.util.Date(millis))
                    onDateSelected(date)
                }
            }) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
