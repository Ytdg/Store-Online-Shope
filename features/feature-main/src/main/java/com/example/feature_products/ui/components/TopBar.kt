package com.example.feature_products.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_products.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onClickNavIcon: () -> Unit,
    onClickActionIcon: () -> Unit,
    iconNav:ImageVector,
    iconAction:ImageVector,
    text: String
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = text,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }, navigationIcon = {
        IconButton(onClick = { onClickNavIcon() }) {

            Icon(imageVector = iconNav, contentDescription = null)
        }
    }, actions = {
        IconButton(onClick = {
            onClickActionIcon()
        }) {
            Icon(
                imageVector = iconAction,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    })
}