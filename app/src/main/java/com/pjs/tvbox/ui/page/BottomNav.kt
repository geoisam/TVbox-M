package com.pjs.tvbox.ui.page

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.pjs.tvbox.ui.view.MainView

@Composable
fun BottomNav(
    currentRoute: String,
    onTabSelected: (MainView) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(MainView.Main, MainView.Live, MainView.Mine)

    NavigationBar(
        modifier = modifier
    ) {
        tabs.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconResId),
                        contentDescription = stringResource(screen.titleResId),
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.titleResId),
                        style = if (currentRoute == screen.route) {
                            MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        } else {
                            MaterialTheme.typography.labelMedium
                        }
                    )
                },
                selected = currentRoute == screen.route,
                onClick = { onTabSelected(screen) }
            )
        }
    }
}