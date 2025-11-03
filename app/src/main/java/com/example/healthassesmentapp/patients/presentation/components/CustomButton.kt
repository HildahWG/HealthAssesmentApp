package com.example.healthassesmentapp.patients.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSmallButton (
    modifier: Modifier = Modifier,
    value :String,
    onNavigate : () -> Unit
) {
    Button(
        onClick = onNavigate,
        modifier = modifier
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFFd2e6d2),
            ),
    ) {
        Text(
            text = value,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,

            )

    }

}