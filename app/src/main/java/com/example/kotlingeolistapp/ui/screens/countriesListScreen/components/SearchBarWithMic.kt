package com.example.kotlingeolistapp.ui.screens.countriesListScreen.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@SuppressLint("ContextCastToActivity")
@Composable
fun SearchBarWithMic(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    val context = LocalContext.current as Activity
    val speechRecognizerLauncher = rememberSpeechLauncher { spokenText ->
        onSearchChange(removeAccents(spokenText))
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchSpeechRecognizer(context, speechRecognizerLauncher)
        } else {
            Toast.makeText(context, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
        }
    }

    val focusRequester = remember { FocusRequester() }
    val textFieldValue = remember { mutableStateOf(TextFieldValue(searchText)) }

    LaunchedEffect(searchText) {
        textFieldValue.value = textFieldValue.value.copy(text = searchText)
    }

    TextField(
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
            onSearchChange(it.text)
        },
        placeholder = { Text("Buscar...", color = Color.Gray, fontSize = 16.sp) },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray)
        },
        trailingIcon = {
            IconButton(onClick = {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }) {
                Icon(Icons.Filled.Mic, contentDescription = "Voice Search", tint = Color.Gray)
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(20),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .defaultMinSize(minHeight = 48.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused && searchText.isNotEmpty()) {
                    textFieldValue.value = textFieldValue.value.copy(
                        selection = TextRange(0, textFieldValue.value.text.length)
                    )
                }
            }
    )
}
