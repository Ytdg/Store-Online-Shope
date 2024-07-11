package com.example.feature_products.ui.components_products_screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feature_products.events.EventsFilter
import com.example.feature_products.models.ValuePlaceHolderFilter
import com.example.feature_products.ui.components.TextFieldColor
import com.example.feature_products.util.keyBoardAsState
import com.example.feature_products.util.toStringNotNull


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsPriceFilter(
    modifier: Modifier,
    priceMin: String?,
    priceMax: String?,
    onEventFilter: (EventsFilter) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        val interactionSource = remember {
            MutableInteractionSource()
        }

        BasicTextField(
            value =  priceMin.toStringNotNull() ,
            onValueChange = {
                onEventFilter(EventsFilter.SetPriceMin(it))
            },
            modifier = modifier,
            singleLine = true,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value =  priceMin.toStringNotNull(),
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
                placeholder = {
                    Text(
                        text = ValuePlaceHolderFilter.priceMin,
                        color = Color.Gray,
                        fontFamily = FontFamily.Cursive
                    )
                }
            )
            if (!keyBoardAsState().value) {
                LaunchedEffect(true) {
                    focusManager.clearFocus()
                }
            }
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = Color(237, 170, 75),
            modifier = Modifier.width(20.dp)
        )
        BasicTextField(
            value =  priceMax.toStringNotNull() ,
            onValueChange = {
                onEventFilter(EventsFilter.SetPriceMax(it))
            },
            modifier = modifier,
            singleLine = true,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value =  priceMax.toStringNotNull(),
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
                placeholder = {
                    Text(
                        text = ValuePlaceHolderFilter.priceMax,
                        color = Color.Gray,
                        fontFamily = FontFamily.Cursive
                    )
                }
            )
        }

    }
}





