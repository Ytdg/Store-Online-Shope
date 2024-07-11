package com.example.feature_products.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LabelNotifications(text: String, modifier: Modifier) {
    Box(
        modifier
            .widthIn(max = 40.dp, min = 25.dp)
            .height(25.dp)
            .clip(CircleShape)
            .background(Color.Red), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 3.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}