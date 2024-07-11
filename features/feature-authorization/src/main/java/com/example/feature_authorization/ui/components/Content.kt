package com.example.feature_authorization.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_authorization.ui.ContentScreen
import kotlinx.coroutines.delay

@Composable
fun Content(
    contentScreen: ContentScreen,
    boxScope: BoxScope,
    enableBtn: Boolean,
    enablePlaceHolderBtn:Boolean,
    onClickBtn: () -> Unit,
    contentInputs: @Composable () -> Unit
) {
    val paddingIme = WindowInsets.ime.asPaddingValues().calculateBottomPadding() / 2

    Image(
        painter = painterResource(id = contentScreen.idImageBack), contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f),
        contentScale = ContentScale.Crop
    )
    boxScope.run {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp))
                .background(Color(243, 245, 246))
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 35.dp, bottom = 10.dp, start = 25.dp, end = 25.dp)
            ) {
                Text(text = contentScreen.title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(25.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingIme),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    contentInputs()
                }
                Spacer(modifier = Modifier.height(35.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onClickBtn() },
                        enabled = enableBtn,
                        modifier = Modifier
                            .height(61.dp)
                            .widthIn(min = 152.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(
                                237,
                                170,
                                75
                            )
                        )
                    ) {
                        Text(
                            text = contentScreen.labelBtn,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                    TextButton(
                        onClick = { contentScreen.onClickPlaceHolder?.let { navigate -> navigate() } },
                        enabled = enablePlaceHolderBtn,
                        modifier = Modifier
                    ) {
                        Text(
                            text = contentScreen.placeHolderBottomCard,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
