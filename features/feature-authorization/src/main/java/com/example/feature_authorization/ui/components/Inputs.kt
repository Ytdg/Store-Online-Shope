package com.example.feature_authorization.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inputfield(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation,
    placeHolder: String,
    trailingIcon: ImageVector?,
    modifierIcon: Modifier,
    textNotValid: String? = null
) {

    val interationSoure = remember {
        MutableInteractionSource()
    }
    BasicTextField(
        value = value,
        interactionSource = interationSoure,
        textStyle = TextStyle(fontSize = 20.sp),
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        visualTransformation = visualTransformation,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            supportingText = {
                textNotValid?.let {
                    Text(text = textNotValid, color = Color.Red)
                }
            },
            value = value,
            innerTextField = { innerTextField() },
            enabled = false,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            },
            trailingIcon = {
                trailingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifierIcon
                    )
                }
            }, shape = RoundedCornerShape(20.dp),
            visualTransformation = visualTransformation,
            interactionSource = interationSoure,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
