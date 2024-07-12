package com.example.feature_products.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_products.R

@Composable
 fun ErrorConnectionMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.no_wifi),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(64.dp)
        )
        Text(
            text = "Check your internet connection",
            color = Color.Gray,
            fontSize = 16.sp
        )
    }

}