package com.pjs.tvbox.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pjs.tvbox.ui.theme.ContrastAwareReplyTheme

@Composable
fun HomePage() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Home Page")
        }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    ContrastAwareReplyTheme {
        HomePage()
    }
}