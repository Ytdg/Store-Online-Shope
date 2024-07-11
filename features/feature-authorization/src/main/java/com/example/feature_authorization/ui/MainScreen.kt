package com.example.feature_authorization.ui

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.ripple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.feature_authorization.R
import com.example.feature_authorization.ui.components.Content
import com.example.feature_authorization.ui.components.Inputfield
import com.example.feature_authorization.ui.events.EventsLogin
import com.example.feature_authorization.ui.events.EventsSignIn
import com.example.feature_authorization.ui.models.StateRequest
import com.example.feature_authorization.ui.models.ViewModelAuthorization
import com.example.feature_authorization.ui.navigate.Destination
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Immutable
data class ContentScreen(
    val idImageBack: Int,
    val title: String,
    val labelBtn: String,
    val placeHolderBottomCard: String,
    val onClickPlaceHolder: (() -> Unit)? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    viewModel: ViewModelAuthorization,
    destination: Destination,
    navHostController: NavController,
    endNavTransition: Boolean
) {
    val context = LocalContext.current
    val paddingValues = PaddingValues(
        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    )
    val stateResponse: StateRequest = viewModel.stateRequest.collectAsState().value
    val keyBoardState = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current
    val keyBoardOpt = LocalSoftwareKeyboardController.current
    Log.d("/?e", endNavTransition.toString())
    if (!keyBoardState) {
        LaunchedEffect(key1 = true) {
            keyBoardOpt?.hide()
            focusManager.clearFocus()
        }
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (destination) {
            is Destination.ScreenLogin -> {

                val stateDataLogin = viewModel.stateDataLogin.collectAsState().value
                Content(
                    contentScreen = ContentScreen(
                        idImageBack = R.drawable.boarding5,
                        title = "Hello!",
                        labelBtn = "Login",
                        placeHolderBottomCard = "Don't have an account?",
                        onClickPlaceHolder = {

                            if (navHostController.currentDestination!!.route != Destination.ScreenSign.route) navHostController.navigate(
                                Destination.ScreenSign.route
                            )
                        }
                    ),
                    boxScope = this,
                    enableBtn = stateDataLogin.isValid,
                    onClickBtn = {
                        viewModel.onEventLogin(EventsLogin.Login)
                    },
                    enablePlaceHolderBtn = endNavTransition
                ) {
                    Inputfield(
                        modifier = Modifier,
                        value = stateDataLogin.email.value,
                        onValueChange = { value ->
                            viewModel.onEventLogin(
                                EventsLogin.ChangeEmail(
                                    value
                                )
                            )
                        },
                        visualTransformation = VisualTransformation.None,
                        placeHolder = "Email",
                        trailingIcon = Icons.Default.Email,
                        modifierIcon = Modifier,
                    )
                    var isShowPassword by remember {
                        mutableStateOf(false)
                    }
                    Inputfield(
                        modifier = Modifier,
                        value = stateDataLogin.password.value,
                        onValueChange = { value ->
                            viewModel.onEventLogin(
                                EventsLogin.ChangePassword(
                                    value
                                )
                            )
                        },
                        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        placeHolder = "Password",
                        trailingIcon = ImageVector.vectorResource(id = R.drawable.icon_password_visible),
                        modifierIcon = Modifier.clickable(
                            interactionSource = null,
                            indication = ripple(radius = 10.dp),
                            onClick = {
                                isShowPassword = !isShowPassword
                            })
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        val interation = remember {
                            MutableInteractionSource()
                        }

                        TextButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .align(Alignment.CenterEnd)
                                .clickable(
                                    interactionSource = interation,
                                    indication = ripple(color = Color.Gray.copy(0.4f)),
                                    onClick = {}),
                            interactionSource = interation,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = "Forgot your password?",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            is Destination.ScreenSign -> {
                val stateDataSign =
                    viewModel.stateDataSignIn.collectAsState().value
                Content(
                    contentScreen = ContentScreen(
                        idImageBack = R.drawable.boarding1,
                        title = "Welcome to our family!",
                        labelBtn = "Sign Up",
                        placeHolderBottomCard = "Already registered?",
                        onClickPlaceHolder = { if (navHostController.currentDestination!!.route != Destination.ScreenLogin.route) navHostController.popBackStack() }
                    ),
                    boxScope = this,
                    enableBtn = stateDataSign.isValid,
                    onClickBtn = { viewModel.onEventSign(EventsSignIn.SignIn) },
                    enablePlaceHolderBtn = endNavTransition
                ) {
                    Inputfield(
                        modifier = Modifier,
                        value = stateDataSign.email.value,
                        onValueChange = {
                            viewModel.onEventSign(
                                EventsSignIn.ChangeEmail(
                                    it
                                )
                            )
                        },
                        visualTransformation = VisualTransformation.None,
                        placeHolder = "Enter email",
                        trailingIcon = Icons.Default.Email,
                        modifierIcon = Modifier,
                        textNotValid = with(stateDataSign.email) { isValid?.let { if (!it) "incorrect email" else null } }
                    )
                    var isShowPassword by remember {
                        mutableStateOf(false)
                    }
                    Inputfield(
                        modifier = Modifier,
                        value = stateDataSign.password.value,
                        onValueChange = {
                            viewModel.onEventSign(
                                EventsSignIn.ChangePassword(
                                    it
                                )
                            )
                        },
                        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        placeHolder = "Enter password",
                        trailingIcon = ImageVector.vectorResource(id = R.drawable.icon_password_visible),
                        modifierIcon = Modifier.clickable(
                            interactionSource = null,
                            indication = ripple(radius = 10.dp),
                            onClick = {
                                isShowPassword = !isShowPassword
                            }),
                        textNotValid = with(stateDataSign.password) { isValid?.let { if (!it) "min length 8" else null } }
                    )
                    var isShowPasswordConfirm by remember {
                        mutableStateOf(false)
                    }
                    Inputfield(
                        modifier = Modifier,
                        value = stateDataSign.confirmPassword.value,
                        onValueChange = {
                            viewModel.onEventSign(
                                EventsSignIn.ConfirmPassword(
                                    it
                                )
                            )
                        },
                        visualTransformation = if (isShowPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                        placeHolder = "Confirm password",
                        trailingIcon = ImageVector.vectorResource(id = R.drawable.icon_password_visible),
                        modifierIcon = Modifier.clickable(
                            interactionSource = null,
                            indication = ripple(radius = 10.dp),
                            onClick = {
                                isShowPasswordConfirm = !isShowPasswordConfirm
                            }),
                        textNotValid = with(stateDataSign.confirmPassword) { isValid?.let { if (!it) "passwords don't match" else null } }
                    )
                }
            }
        }
        if (stateResponse is StateRequest.Error) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Snackbar(snackbarData = it, shape = RoundedCornerShape(10.dp))
            }
            LaunchedEffect(key1 = true) {
                snackbarHostState.showSnackbar(message = stateResponse.message)
                viewModel.finishUiRequest()
            }
        }
    }
    if (stateResponse is StateRequest.Successful) {
        LaunchedEffect(key1 = true) {
            val intent = Intent()
            intent.setComponent(
                ComponentName(
                    "com.example.storeonline",
                    "com.example.feature_products.MainActivity"
                )
            )
            val act = context as Activity
            viewModel.finishUiRequest()
            act.finish()
            act.startActivity(intent)
        }
    }

    if (stateResponse is StateRequest.Loading) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = Color(240, 240, 240).copy(0.3f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LinearProgressIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    color = Color.Gray,
                    trackColor = Color.Transparent
                )
            }
        }
    }
    navHostController.currentDestination?.let {
        it.route?.let {
            if (it == Destination.ScreenSign.route) {
                BackHandler {
                    if (endNavTransition) {
                        navHostController.popBackStack()
                    }
                }
            }
        }
    }
}
