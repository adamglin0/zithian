package com.adamglin.zithian.compose.scaffold

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import io.github.fletchmckee.liquid.liquid

@Composable
fun ScaffoldScope.TitleWithLeadingAppBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .hazeEffect(hazeState) {
                blurRadius = 6.dp
                progressive = HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)
            }
            .statusBarsPadding()
            .padding(10.dp)
    ) {
        val backgroundColor = ZithianTheme.colors.surfacePure.copy(.2f)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .liquid(liquidState) {
                    shape = CircleShape
                    tint = backgroundColor
                    frost = 0.dp
                    refraction = 0.15f
                    curve = 0.25f
                    edge = 0.02f
                    saturation = 1f
                    dispersion = 0.26f
                }
                .padding(5.dp)
        ) {
            CoilIcon(
                modifier = Modifier
                    .rotate(180f)
                    .size(27.dp).rotate(180f).clickable { onBack() },
                uri = ZithianResources.getUri("drawable/ic_chevron_left.svg"),
                contentDescription = null,
                tint = ZithianTheme.colors.text3,
            )
        }
        AnimatedContent(
            targetState = title,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                modifier = Modifier,
                text = it ?: "",
                style = ZithianTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}