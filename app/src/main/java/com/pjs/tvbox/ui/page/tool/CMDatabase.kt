package com.pjs.tvbox.ui.page.tool

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
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
import com.pjs.tvbox.ui.view.CMDatabaseView

sealed class CMDatabaseScreen {
    object Main : CMDatabaseScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMDatabase(
    onBack: () -> Unit
) {
    var current by remember { mutableStateOf<CMDatabaseScreen>(CMDatabaseScreen.Main) }

    BackHandler(enabled = true) {
        if (current == CMDatabaseScreen.Main) {
            onBack()
        } else {
            current = CMDatabaseScreen.Main
        }
    }

    when (current) {
        CMDatabaseScreen.Main -> CMDatabaseMain(
            onBack = onBack,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CMDatabaseMain(
    onBack: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tool_ticket),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_refresh),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CMDatabaseView(modifier = Modifier.weight(1f))
        }
    }
}