package com.example.healthassesmentapp.patients.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionWithCheckboxes(
    question: String,
    option1: String,
    option2: String,
    modifier: Modifier = Modifier
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        Text(
            text = question,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Checkbox(
                checked = selectedOption == option1,
                onCheckedChange = { checked ->
                    selectedOption = if (checked) option1 else null
                }
            )
            Text(
                text = option1,
                fontSize = 16.sp
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selectedOption == option2,
                onCheckedChange = { checked ->
                    selectedOption = if (checked) option2 else null
                }
            )
            Text(
                text = option2,
                fontSize = 16.sp
            )
        }
    }
}