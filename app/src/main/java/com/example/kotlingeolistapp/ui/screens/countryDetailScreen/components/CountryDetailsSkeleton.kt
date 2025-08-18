package com.example.kotlingeolistapp.ui.screens.countryDetailScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CountryDetailSkeleton(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "detailSkeleton")
    val alpha by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alphaAnim"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = alpha))
            )
            Spacer(Modifier.height(12.dp))
            RectPlaceholder(width = 220.dp, height = 24.dp, alpha = alpha)
            Spacer(Modifier.height(8.dp))
            RectPlaceholder(width = 180.dp, height = 16.dp, alpha = alpha)
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        RectPlaceholder(width = 120.dp, height = 14.dp, alpha = alpha)
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Gray.copy(alpha = alpha))
                        )
                    }
                    SkeletonRow(alpha = alpha, labelWidth = 80.dp, valueWidth = 140.dp)
                    SkeletonRow(alpha = alpha, labelWidth = 88.dp, valueWidth = 160.dp)
                    SkeletonRow(alpha = alpha, labelWidth = 72.dp, valueWidth = 120.dp)
                    SkeletonRow(alpha = alpha, labelWidth = 52.dp, valueWidth = 100.dp)
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    SkeletonRow(alpha = alpha, labelWidth = 92.dp, valueWidth = 140.dp)
                    SkeletonRow(alpha = alpha, labelWidth = 100.dp, valueWidth = 180.dp)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        RectPlaceholder(width = 130.dp, height = 14.dp, alpha = alpha)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            ChipPlaceholder(alpha = alpha)
                            ChipPlaceholder(alpha = alpha)
                        }
                    }

                    SkeletonRow(alpha = alpha, labelWidth = 86.dp, valueWidth = 180.dp)
                    SkeletonRow(alpha = alpha, labelWidth = 78.dp, valueWidth = 160.dp)
                }
            }
        }
    }
}

@Composable
private fun RectPlaceholder(
    width: Dp,
    height: Dp,
    alpha: Float,
    shape: Shape = RoundedCornerShape(4.dp)
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(shape)
            .background(Color.Gray.copy(alpha = alpha))
    )
}

@Composable
private fun SkeletonRow(
    alpha: Float,
    labelWidth: Dp,
    valueWidth: Dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RectPlaceholder(width = labelWidth, height = 14.dp, alpha = alpha)
        RectPlaceholder(width = valueWidth, height = 14.dp, alpha = alpha)
    }
}

@Composable
private fun ChipPlaceholder(alpha: Float) {
    Box(
        modifier = Modifier
            .height(28.dp)
            .width(72.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.Gray.copy(alpha = alpha))
    )
}
