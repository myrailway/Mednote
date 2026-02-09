package com.mednote.app.ui.screen

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mednote.app.ui.viewmodel.SeizureViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    viewModel: SeizureViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val allRecords by viewModel.allRecords.collectAsState()
    var isExporting by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "导出Excel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.Download,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "导出记录到Excel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "共有 ${allRecords.size} 条记录",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            isExporting = true
                            exportToExcel(context, allRecords) { success ->
                                isExporting = false
                                if (success) {
                                    Toast.makeText(
                                        context,
                                        "导出成功",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "导出失败",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        enabled = !isExporting && allRecords.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (isExporting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("开始导出", fontSize = 16.sp)
                        }
                    }
                }
            }
            
            if (allRecords.isEmpty()) {
                Text(
                    text = "暂无记录可导出",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

suspend fun exportToExcel(
    context: Context,
    records: List<com.mednote.app.data.model.SeizureRecord>,
    onComplete: (Boolean) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("癫痫记录")
            
            val headerStyle = workbook.createCellStyle().apply {
                fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
                setFont(workbook.createFont().apply {
                    bold = true
                    fontHeightInPoints = 12
                })
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
            }
            
            val cellStyle = workbook.createCellStyle().apply {
                alignment = HorizontalAlignment.LEFT
                verticalAlignment = VerticalAlignment.CENTER
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
                setBorder(BorderStyle.THIN, IndexedColors.BLACK.index)
            }
            
            val headers = listOf("日期", "大串", "小串", "强直", "阵挛")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.cellStyle = headerStyle
            }
            
            records.forEachIndexed { index, record ->
                val row = sheet.createRow(index + 1)
                
                listOf(
                    record.date,
                    record.bigChain,
                    record.smallChain,
                    record.tonic,
                    record.clonic
                ).forEachIndexed { colIndex, value ->
                    val cell = row.createCell(colIndex)
                    cell.setCellValue(value)
                    cell.cellStyle = cellStyle
                }
            }
            
            headers.forEachIndexed { index, _ ->
                sheet.setColumnWidth(index, 4000)
            }
            
            val downloadsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            )
            val fileName = "大宝记录_${System.currentTimeMillis()}.xlsx"
            val file = File(downloadsDir, fileName)
            
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            
            workbook.close()
            
            withContext(Dispatchers.Main) {
                onComplete(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onComplete(false)
            }
        }
    }
}
