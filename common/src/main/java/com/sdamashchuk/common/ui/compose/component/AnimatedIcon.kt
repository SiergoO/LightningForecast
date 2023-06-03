package com.sdamashchuk.common.ui.compose.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedIcon(
    modifier: Modifier = Modifier,
    @RawRes iconRes: Int
) {
    Box(modifier = modifier) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(iconRes))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            speed = 1f
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}