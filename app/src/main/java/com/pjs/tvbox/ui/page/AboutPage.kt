package com.pjs.tvbox.ui.page

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.R
import com.pjs.tvbox.util.AppUtil
import androidx.core.net.toUri

sealed class AboutScreen {
    object Main : AboutScreen()
    data class Markdown(val file: String, val title: String) : AboutScreen()
}

@Composable
fun AboutPage(onDismiss: () -> Unit) {
    var current by remember { mutableStateOf<AboutScreen>(AboutScreen.Main) }

    when (current) {
        AboutScreen.Main -> AboutMainScreen(
            onBack = onDismiss,
            onOpenMarkdown = { file, title ->
                current = AboutScreen.Markdown(file, title)
            }
        )

        is AboutScreen.Markdown -> {
            val m = current as AboutScreen.Markdown
            MarkdownPage(
                pageTitle = m.title,
                assetFile = m.file,
                onBack = { current = AboutScreen.Main }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutMainScreen(
    onBack: () -> Unit,
    onOpenMarkdown: (file: String, title: String) -> Unit
) {
    val context = LocalContext.current
    val appVersionName = AppUtil.getVersionName(context)
    val appVersionCode = AppUtil.getVersionCode(context)

    val changeTitle = stringResource(R.string.about_change)
    val sponsorTitle = stringResource(R.string.about_sponsor)
    val thanksTitle = stringResource(R.string.about_thanks)
    val disclaimerTitle = stringResource(R.string.about_disclaimer)
    val agreementTitle = stringResource(R.string.about_agreement)
    val permissionsTitle = stringResource(R.string.about_permissions)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.mine_about),
                        style = MaterialTheme.typography.titleMedium,
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_logo_fill),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(99.dp)
                            .padding(16.dp)
                            .background(
                                Color.Black,
                                RoundedCornerShape(19.dp)
                            )
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "v$appVersionName.$appVersionCode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            context.startActivity(Intent(Intent.ACTION_VIEW, "https://github.com/geoisam/TVD-Mobile".toUri()))
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_github),
                            null,
                            modifier = Modifier.size(56.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f)
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.about_github_url),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 2.dp, bottom = 1.dp)
                            )
                            Text(
                                text = stringResource(R.string.about_github_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 1.dp, bottom = 2.dp)
                            )
                        }
                        Text(
                            text = stringResource(R.string.LICENSE),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(vertical = 8.dp)
                    ){
                        ListItem(
                            headlineContent = {
                                Text(
                                text = changeTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                    )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Changelog.md", changeTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(text = sponsorTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                    )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Sponsor.md", sponsorTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = thanksTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Thanks.md", thanksTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = disclaimerTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                    )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Disclaimer.md", disclaimerTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                text = agreementTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Agreement.md", agreementTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                text = permissionsTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                )
                                              },
                            trailingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier.clickable {
                                onOpenMarkdown("md/Permissions.md", permissionsTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                    }
                }
            }
        }
    }
}