package com.pjs.tvbox.ui.page

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
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
import com.pjs.tvbox.ui.theme.LogoFont
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class AboutScreen {
    object Main : AboutScreen()
    data class Markdown(val file: String, val title: String) : AboutScreen()
}

@Composable
fun AboutPage(
    onBack: () -> Unit
) {
    var current by remember { mutableStateOf<AboutScreen>(AboutScreen.Main) }

    BackHandler(enabled = true) {
        if (current == AboutScreen.Main) {
            onBack()
        } else {
            current = AboutScreen.Main
        }
    }

    when (current) {
        AboutScreen.Main -> AboutMain(
            onBack = onBack,
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
private fun AboutMain(
    onBack: () -> Unit,
    onOpenMarkdown: (file: String, title: String) -> Unit
) {
    val context = LocalContext.current
    val appVersionName = AppUtil.getVersionName(context)
    val appVersionCode = AppUtil.getVersionCode(context)

    val appName = stringResource(R.string.app_name)
    val githubName = stringResource(R.string.about_github_name)
    val githubRepo = stringResource(R.string.about_github_repo)
    val githubUrl = "https://github.com/${githubName}/${githubRepo}"
    val changeTitle = stringResource(R.string.about_change)
    val sponsorTitle = stringResource(R.string.about_sponsor)
    val thanksTitle = stringResource(R.string.about_thanks)
    val disclaimerTitle = stringResource(R.string.about_disclaimer)
    val agreementTitle = stringResource(R.string.about_agreement)
    val privacyTitle = stringResource(R.string.about_privacy)
    val permissionTitle = stringResource(R.string.about_permission)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.mine_about),
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
                            Toast.makeText(context, "更多", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_mail),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_logo_fill),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(88.dp)
                            .background(
                                Color.Black,
                                MaterialTheme.shapes.medium
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = appName,
                        fontFamily = LogoFont,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "v$appVersionName.$appVersionCode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    githubUrl.toUri()
                                )
                            )
                        },
                    shape = MaterialTheme.shapes.small,
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
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = "${githubName}/${githubRepo}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = stringResource(R.string.about_github_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.78f),
                                    MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.LICENSE),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
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
                                onOpenMarkdown("md/UpdateLogs.md", changeTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = sponsorTitle,
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
                                onOpenMarkdown("md/ThanksList.md", thanksTitle)
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
                                    text = privacyTitle,
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
                                onOpenMarkdown("md/Privacy.md", privacyTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = permissionTitle,
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
                                onOpenMarkdown("md/Permission.md", permissionTitle)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.about_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = stringResource(R.string.about_author),
                        fontFamily = LogoFont,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "Copyright ©${getYearNumber()} ${appName}. All Rights Reserved",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

fun getYearNumber(): Int {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy")
    return currentDate.format(formatter).toInt()
}