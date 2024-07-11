package com.example.storeonline.ui.products.components.filter.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feature_products.events.EventsFilter
import com.example.feature_products.ui.components.TextFieldColor
import com.example.feature_products.util.toStringNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldTitleFilter(
    modifier: Modifier,
    value: String?,
    onEventFilter: (EventsFilter.SetTitle) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    BasicTextField(
        value = value.toStringNotNull(),
        onValueChange = { onEventFilter(EventsFilter.SetTitle(it)) },
        modifier = modifier,
        singleLine = true,
        interactionSource = interactionSource,
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = value.toStringNotNull(),
            innerTextField = innerTextField,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            colors = TextFieldColor(),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp,
                bottom = 0.dp,
            ),
            enabled = false,
        )

    }
}

