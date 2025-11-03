package com.example.healthassesmentapp.patients.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title : String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(

        title = {
            Row(
                modifier = Modifier
                    .background(Color(0xFFFEEFC8))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        },

        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFEEFC8)
        ),
        modifier =
            modifier
                .padding(bottom = 20.dp)
                .background(Color(0xFFFEEFC8)),


    )
}
