package com.example.healthassesmentapp.patients.presentation.components

import android.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelInputRow(
    label :String,
    textValue : String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row (
        modifier =modifier
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.4f)

        )
        androidx.compose.material3.OutlinedTextField(
            value = textValue,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .weight(0.6f)
                .padding(start = 8.dp),
        )

    }

}