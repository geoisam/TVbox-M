package com.pjs.tvbox.ui.page.tool

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.R
import com.pjs.tvbox.ui.view.CMDbYearView
import java.util.Calendar

sealed class CMDbYearScreen {
    object Main : CMDbYearScreen()
}

@Composable
fun CMDbYear(
    onBack: () -> Unit,
    title: Int,
) {
    var current by remember { mutableStateOf<CMDbYearScreen>(CMDbYearScreen.Main) }
    var selectedYear by remember { mutableStateOf(1) }

    BackHandler(enabled = true) {
        if (current == CMDbYearScreen.Main) {
            onBack()
        } else {
            current = CMDbYearScreen.Main
        }
    }

    when (current) {
        CMDbYearScreen.Main -> CMDbYearMain(
            onBack = onBack,
            title = title,
            selectedYear = selectedYear,
            onYearSelected = { year -> selectedYear = year }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CMDbYearMain(
    onBack: () -> Unit,
    title: Int,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = listOf(1) + (2017..currentYear).toList().reversed()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_calendar),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = {
                                    Text(if (year == 1) "全部" else "$year")
                                },
                                onClick = {
                                    onYearSelected(year)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CMDbYearView(
                modifier = Modifier.weight(1f),
                selectedYear = selectedYear,
            )
        }
    }
}