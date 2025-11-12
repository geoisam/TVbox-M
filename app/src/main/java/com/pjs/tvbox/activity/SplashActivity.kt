package com.pjs.tvbox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pjs.tvbox.R
import com.pjs.tvbox.ui.theme.ContrastAwareReplyTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            installSplashScreen()
            setContent {
                ContrastAwareReplyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SplashScreenContent(
                            onTimeout = {
                                try {
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } catch (e: Exception) {
                                    Log.e("SplashActivity", "Failed to start MainActivity", e)
                                }
                            }
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SplashActivity", "Error in onCreate", e)
        }
    }
}

@Composable
fun SplashScreenContent(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
    LaunchedEffect(Unit) {
        try {
            delay(2000)
            onTimeout()
        } catch (e: Exception) {
            Log.e("SplashScreenContent", "Error in LaunchedEffect", e)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ContrastAwareReplyTheme {
        SplashScreenContent(onTimeout = {})
    }
}