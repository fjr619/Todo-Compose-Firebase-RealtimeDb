package com.fjr619.composefirebasedb.ui.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fjr619.composefirebasedb.R

@Composable
fun ExitAlertDialog(cancel: () -> Unit, ok: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
        },
        // below line is use to display title of our dialog
        // box and we are setting text color to white.
        title = {
            Text(
                text = stringResource(R.string.close_the_app),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = stringResource(R.string.do_you_want_to_exit_the_app), fontSize = 16.sp)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    ok()
                }) {
                Text(
                    stringResource(R.string.yes),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    cancel()
                }) {
                Text(
                    stringResource(R.string.no),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
    )
}