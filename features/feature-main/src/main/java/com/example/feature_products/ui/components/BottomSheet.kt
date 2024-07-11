package com.example.feature_products.ui.components


import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

internal var defaultMinHeightSheet = 400.dp

internal var heightScreenPx by Delegates.notNull<Float>()

internal var heightScheetPx by Delegates.notNull<Float>()

internal var offsetHidden by Delegates.notNull<Float>()

internal var offsetExpand by Delegates.notNull<Float>()


internal val getBottomSheetVisible = { offset: Float ->
    when (offset) {
        offsetExpand -> {
            BottomSheetVisible.Expand
        }

        offsetHidden -> {
            BottomSheetVisible.Hidden
        }

        else -> {
            BottomSheetVisible.HalfExpand
        }
    }
}


@Immutable
enum class BottomSheetVisible {
    Hidden, Expand, HalfExpand
}

internal val checkInitValue = {
    try {
        offsetHidden.isNaN()
        true
    } catch (ex: Exception) {
        false
    }
}

@Immutable
data class BottomSheetState(
    val bottomSheetVisible: BottomSheetVisible,
    val shutDown: Boolean,
    val close: () -> Unit
)

@Composable
@NonRestartableComposable
@Stable
internal fun InitValues(heightSheet: Dp) {
    val localDensity = LocalDensity.current
    defaultMinHeightSheet = heightSheet
    heightScreenPx =
        with(localDensity) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    heightScheetPx = with(localDensity) { heightSheet.toPx() }
    offsetHidden = with(localDensity) {
        WindowInsets.Companion.navigationBars.asPaddingValues(localDensity)
            .calculateBottomPadding().toPx()
    } + heightScreenPx
    offsetExpand = (heightScreenPx - heightScheetPx) + 8
}

@Composable
fun CustomBottomSheet(
    stateVisibleBottomSheet: State<Boolean>,
    heightSheet: Dp = defaultMinHeightSheet,
    onClose: () -> Unit,
    content: @Composable (State<BottomSheetState>, BoxScope) -> Unit
) {
    if (stateVisibleBottomSheet.value) {
        if (!checkInitValue()) {
            InitValues(heightSheet = heightSheet)
        }
        val offset = remember { Animatable(initialValue = offsetHidden) }
        val scrimColor = remember { Animatable(initialValue = Color.Transparent) }

        val startAnimation = remember {
            mutableStateOf(true)
        }
        Animation(
            state = startAnimation,
            offsetAnimatable = offset,
            colorAnimatable = scrimColor,
            finishedListener = { onClose() })
        BottomSheetContainer(
            modifier = Modifier,
            offset = offset,
            scrimColor = scrimColor,
            finishedListenerAnimation = {
                startAnimation.value = false
            }) { boxScoupe ->

            content(
                rememberUpdatedState(
                    newValue = BottomSheetState(
                        close = {
                            startAnimation.value = false
                        },
                        bottomSheetVisible = getBottomSheetVisible(offset.value),
                        shutDown = !startAnimation.value
                    )
                ),
                boxScoupe
            )
        }

        BackHandler {
            if (offset.value == offset.targetValue) {
                startAnimation.value = false
            }
        }
    }
}


@Composable
internal fun BottomSheetContainer(
    modifier: Modifier,
    offset: Animatable<Float, AnimationVector1D>,
    scrimColor: Animatable<Color, AnimationVector4D>,
    finishedListenerAnimation: () -> Unit,
    content: @Composable (BoxScope) -> Unit,
) {
    Canvas(modifier = Modifier
        .navigationBarsPadding()
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures {
                if (offset.value == offset.targetValue && scrimColor.value == scrimColor.targetValue) {
                    finishedListenerAnimation()
                }
            }
        }
        .zIndex(1f)
    ) {
        drawRect(color = scrimColor.value)
    }
    Surface(
        Modifier
            .systemBarsPadding()
            .height(defaultMinHeightSheet)
            .fillMaxWidth()
            .offset {
                IntOffset(
                    x = 0,
                    y = offset.value.toInt()
                )
            }
            .zIndex(1f),
        color = Color.White,
        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
    ) {

        Box(
            modifier = Modifier
        ) {
            content(this)
        }
    }
}


@Composable
@NonRestartableComposable
internal fun Animation(
    state: State<Boolean>,
    offsetAnimatable: Animatable<Float, AnimationVector1D>,
    colorAnimatable: Animatable<Color, AnimationVector4D>,
    finishedListener: () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = state.value) {
        coroutine.launch {
            colorAnimatable.animateTo(
                targetValue = if (state.value) Color.Black.copy(0.1f) else Color.Transparent,
                animationSpec = tween(450, delayMillis = 200)
            )
        }.invokeOnCompletion {
            if (!state.value) finishedListener()
        }
        offsetAnimatable.animateTo(
            targetValue = if (state.value) offsetExpand else offsetHidden,
            animationSpec = tween(400, 150)
        )

    }
}


@Composable
fun TextFieldColor(): TextFieldColors {

    val colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        focusedBorderColor = Color(122, 122, 122),
        unfocusedBorderColor = Color(122, 122, 122),
        disabledBorderColor = Color(122, 122, 122),
    )
    return colors
}