package com.example.android.codelabs.paging.ui.staggered

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.codelabs.paging.ui.staggered.ShimmerWrapper.animDuration

// Generic Composable Function for creating different types of Shimmering Shapes

@Composable
fun renderShimmer(
    shape: Shape,
    width: Int,
    height: Int,
    colors: List<Color>,
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        /*
         Specify animation positions,
         initial Values 0F means it
         starts from 0 position
        */
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(

            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = animDuration, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    /*
      Create a gradient using the list of colors
      Use Linear Gradient for animating in any direction according to requirement
      start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
      end = Animate the end position to give the shimmer effect using the transition created above
    */

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(shape)
            .background(brush)
    )
}

// overload renderShimmer with arguments in dp
@Composable
fun renderShimmer(
    shape: Shape,
    width: Dp,
    height: Dp,
    colors: List<Color>,
) {
    val transition = rememberInfiniteTransition()
    val widthPx : Float = with(LocalDensity.current) { width.toPx() }
    val translateAnim by transition.animateFloat(
        /*
         Specify animation positions,
         initial Values 0F means it
         starts from 0 position
        */
        initialValue = 0f,
        targetValue = widthPx,
        animationSpec = infiniteRepeatable(

            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = animDuration, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    /*
      Create a gradient using the list of colors
      Use Linear Gradient for animating in any direction according to requirement
      start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
      end = Animate the end position to give the shimmer effect using the transition created above
    */

    val startOffset = 10f
    val endOffset = maxOf(10f,translateAnim)
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(startOffset, startOffset),
        end = Offset(endOffset, endOffset)
    )
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(shape)
            .background(brush)
    )
}