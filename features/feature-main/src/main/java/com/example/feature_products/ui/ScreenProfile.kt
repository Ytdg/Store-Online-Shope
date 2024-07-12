package com.example.feature_products.ui

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.feature_products.R
import com.example.feature_products.ui.components.ImageItemUri
import com.example.feature_products.ui.components.TextFieldColor
import com.example.feature_products.ui.components.TopBar
import com.example.feature_products.util.ImageLoading
import com.example.feature_products.util.keyBoardAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait

@Immutable
private data class TextFieldInformation(
    val imageVector: ImageVector,
    val label: String,
    val visualTransformation: VisualTransformation
)


@Composable
fun DropDownMenuProfile(
    modifier: Modifier = Modifier,
    dpOffset: DpOffset,
    expanded: State<Boolean>,
    onDismiss: () -> Unit,
    items: @Composable ColumnScope.() -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            DropdownMenu(
                shape = RoundedCornerShape(15.dp),
                expanded = expanded.value,
                onDismissRequest = onDismiss,
                offset = dpOffset,
                containerColor = Color(243, 245, 246),
            ) {
                items()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile() {
    val stateMenuProfile = remember {
        mutableStateOf(false)
    }
    val readonlyData = rememberSaveable() {
        mutableStateOf(true)
    }
    val density = LocalDensity.current

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopBar(
            onClickNavIcon = { /*TODO*/ },
            onClickActionIcon = {
                if (readonlyData.value) {
                    stateMenuProfile.value = true
                } else {
                    readonlyData.value = true;
                }
            },
            text = "Profile",
            iconNav = Icons.Filled.ArrowBack,
            iconAction = if (readonlyData.value) ImageVector.vectorResource(id = R.drawable.sort) else Icons.Default.Done
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray)
                ) {
                    val launchPicker = remember {
                        mutableStateOf(false)
                    }
                    val stateImage = remember {
                        mutableStateOf<Uri>(Uri.EMPTY)
                    }
                    if (readonlyData.value) {
                        ImageItemUri(
                            uri = stateImage.value.toString(),
                            modifier = Modifier
                                .matchParentSize(),
                            imageLoading = ImageLoading.InfrequentlyLoading,
                        )
                    }
                    if (!readonlyData.value) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.camera),
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(40.dp)
                                .align(Alignment.Center)
                                .clickable(
                                    interactionSource = null,
                                    indication = ripple(),
                                    onClick = { launchPicker.value = true })
                        )
                        val launcherActivity =
                            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
                                it?.let { stateImage.value = it }
                                launchPicker.value = false
                            }
                        if (launchPicker.value) {
                            LaunchedEffect(key1 = true) {
                                launcherActivity.launch(
                                    input = PickVisualMediaRequest.Builder()
                                        .setMediaType(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        .build()
                                )
                            }
                        }

                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = "Albert Collins", fontSize = 30.sp, color = Color.Black)
                    Text(text = "ID:1231331", fontSize = 18.sp, color = Color.Gray)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                androidx.compose.material.Button(onClick = { /*TODO*/ },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            243,
                            245,
                            246
                        )
                    ),
                    modifier = Modifier
                        .width(150.dp)
                        .height(60.dp)
                        .clickable(
                            interactionSource = null,
                            indication = ripple(color = Color.Black),
                            onClick = {}
                        ),
                    shape = RoundedCornerShape(20.dp)) {
                    Text(text = "About Me", fontSize = 20.sp, color = Color.Black)
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(237, 170, 75),
                    ),
                    modifier = Modifier
                        .width(150.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Reviews", fontSize = 20.sp, color = Color.Black)
                }
            }
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color(243, 245, 246))
            ) {
                val stateKeyboard = keyBoardAsState()
                val focusManager = LocalFocusManager.current
                if (!stateKeyboard.value) {
                    LaunchedEffect(key1 = true) {
                        delay(100)
                        focusManager.clearFocus()
                    }
                }
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .imePadding(),
                    contentPadding = PaddingValues(top = 27.dp, end = 40.dp, start = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    item(1) {
                        TextFieldInformationUser(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            textFieldInformation = remember {
                                TextFieldInformation(
                                    Icons.Default.Phone, "Phone Number",
                                    VisualTransformation.None
                                )
                            },
                            keyboardType = KeyboardType.Number,
                            readonly = readonlyData
                        )
                    }
                    item(2) {
                        TextFieldInformationUser(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            textFieldInformation = remember {
                                TextFieldInformation(
                                    Icons.Default.Email, "Email",
                                    VisualTransformation.None
                                )
                            },
                            keyboardType = KeyboardType.Unspecified,
                            readonly = readonlyData
                        )
                    }
                    item(3) {
                        TextFieldInformationUser(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            textFieldInformation = remember {
                                TextFieldInformation(
                                    Icons.Default.LocationOn, "Direction",
                                    VisualTransformation.None
                                )
                            },
                            keyboardType = KeyboardType.Unspecified,
                            readonly = readonlyData
                        )
                    }
                    item(4)
                    {
                        Spacer(modifier = Modifier.height(60.dp))
                        Text(
                            "See more",
                            color = Color(237, 170, 75),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        DropDownMenuProfile(
            expanded = stateMenuProfile,
            onDismiss = { stateMenuProfile.value = false },
            dpOffset = DpOffset(x = 0.dp, y = with(density) { 30.dp.toPx() }.dp)
        ) {
            DropdownMenuItem(
                text = { Text(text = "Edit data") },
                onClick = { stateMenuProfile.value = false;readonlyData.value = false },
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldInformationUser(
    modifier: Modifier,
    readonly: State<Boolean>,
    textFieldInformation: TextFieldInformation,
    keyboardType: KeyboardType
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    BasicTextField(
        value = String(),
        onValueChange = {
        },
        enabled = !readonly.value,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = String(),
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = textFieldInformation.visualTransformation,
                interactionSource = interactionSource,
                shape = RoundedCornerShape(20.dp), colors = TextFieldColor(),
                leadingIcon = {
                    Icon(
                        imageVector = textFieldInformation.imageVector,
                        contentDescription = null,
                        tint = Color(237, 170, 75)
                    )
                },
                label = { Text(text = textFieldInformation.label, color = Color.Gray) }
            )

        },
        interactionSource = interactionSource,
        modifier = modifier
    )
}