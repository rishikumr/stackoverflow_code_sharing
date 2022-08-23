package com.example.android.codelabs.paging.ui.staggered

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ShimmerWrapper {
    const val url = "url"
    const val animDuration = 1200
    private val shimmerColorShades = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.9f)
    )

    @Composable
    fun getRectangularShimmer(width: Int, height: Int, colors: List<Color> = shimmerColorShades) {
        renderShimmer(RectangleShape, width, height, colors)
    }

    @Composable
    fun getCircularShimmer(width: Int, height: Int, colors: List<Color> = shimmerColorShades) {
        renderShimmer(CircleShape, width, height, colors)
    }

    @Composable
    fun getRoundedCornerShimmer(
        width: Int,
        height: Int,
        colors: List<Color> = shimmerColorShades,
        cornerRadius: Int
    ) {
        renderShimmer(RoundedCornerShape(cornerRadius.dp), width, height, colors)
    }

    @Composable
    fun getCutCornerShimmer(
        width: Int,
        height: Int,
        colors: List<Color> = shimmerColorShades,
        cornerRadius: Int
    ) {
        renderShimmer(CutCornerShape(cornerRadius.dp), width, height, colors)
    }

    // overload getCircularShimmer with arguments in dp
    @Composable
    fun GetCircularShimmer(width: Dp, height: Dp, colors: List<Color> = shimmerColorShades) {
        renderShimmer(CircleShape, width, height, colors)
    }

    // overload getRoundedCornerShimmer with arguments in dp
    @Composable
    fun GetRoundedCornerShimmer(
        width: Dp,
        height: Dp,
        colors: List<Color> = shimmerColorShades,
        cornerRadius: Dp
    ) {
        renderShimmer(RoundedCornerShape(cornerRadius), width, height, colors)
    }

    @Composable
    fun GetCapsuleShimmer(
        width: Dp,
        height: Dp,
        colors: List<Color> = shimmerColorShades
    ) {
        renderShimmer(
            RoundedCornerShape(
                topStartPercent = 50,
                bottomStartPercent = 50
            ), width, height, colors
        )

    }

}
