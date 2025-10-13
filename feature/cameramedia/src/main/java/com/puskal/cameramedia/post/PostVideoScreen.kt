package com.puskal.cameramedia.post

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.puskal.composable.TopBar
import com.puskal.theme.R
import com.puskal.theme.TikTokTheme
import com.puskal.theme.White

@Composable
fun PostVideoScreen(
    videoUri: String,
    onBack: () -> Unit,
    onPublish: () -> Unit = {},
    onFriendDaily: () -> Unit = {}
) {
    TikTokTheme(darkTheme = true) {
        Scaffold(
            topBar = { TopBar { onBack() } },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onFriendDaily) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_friends),
                            contentDescription = null,
                            tint = White
                        )
                    }
                    IconButton(onClick = onPublish) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = White
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = videoUri,
                    contentDescription = null,
                    modifier = Modifier
                        .width(200.dp)
                        .height(400.dp)
                )
                val desc = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = desc.value,
                    onValueChange = { desc.value = it },
                    placeholder = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Text(text = "#topic    @friend", modifier = Modifier.padding(16.dp))
                Text(text = stringResource(id = R.string.location), modifier = Modifier.padding(16.dp))
                Text(text = stringResource(id = R.string.add_labels), modifier = Modifier.padding(16.dp))
                Text(text = stringResource(id = R.string.private_public), modifier = Modifier.padding(16.dp))
            }
        }
    }
}
