package com.okrama.recipesbook.ui.core.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Custom Typography class. Unlike material [androidx.compose.material.Typography], it
 * has more variations for text styles.
 */
@Immutable
data class RecipesBookTypography internal constructor(
    // Heading
    val headingXLarge: TextStyle,
    val headingLarge: TextStyle,
    val headingMedium: TextStyle,
    val headingSmallStrong: TextStyle,
    val headingSmall: TextStyle,
    // Body
    val bodyLarge: TextStyle,
    val bodyLargeStrong: TextStyle,
    val bodyMedium: TextStyle,
    val bodyMediumStrong: TextStyle,
    val bodySmall: TextStyle,
    val bodySmallStrong: TextStyle,
    // Button
    val buttonLarge: TextStyle,
    val buttonMedium: TextStyle,
    val buttonExtraLarge: TextStyle,
    // Caption
    val captionMedium: TextStyle,
    val captionMediumStrong: TextStyle,
    // Code
    val codeMediumStrong: TextStyle,
    val codeSmall: TextStyle,
    val codeSmallStrong: TextStyle,
)

/**
 * Create Typography with required styles
 */
internal val Typography = RecipesBookTypography(
    headingXLarge = TextStyle(
        fontSize = 36.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive
    ),
    headingLarge = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    ),
    headingMedium = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    headingSmallStrong = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    headingSmall = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    bodyLargeStrong = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    bodyMediumStrong = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    bodySmallStrong = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Default
    ),
    buttonLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    buttonMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    ),
    buttonExtraLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    captionMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    captionMediumStrong = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    ),
    codeMediumStrong = TextStyle(
        fontSize = 24.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    codeSmall = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    codeSmallStrong = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    )
)

/**
 * This CompositionLocal holds the default Typography. It can be replaced inside the Theme
 * using CompositionLocalProvider.
 */
internal val LocalRecipesBookTypography = staticCompositionLocalOf {
    RecipesBookTypography(
        headingXLarge = TextStyle.Default,
        headingLarge = TextStyle.Default,
        headingMedium = TextStyle.Default,
        headingSmallStrong = TextStyle.Default,
        headingSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyLargeStrong = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodyMediumStrong = TextStyle.Default,
        bodySmall = TextStyle.Default,
        bodySmallStrong = TextStyle.Default,
        buttonLarge = TextStyle.Default,
        buttonMedium = TextStyle.Default,
        buttonExtraLarge = TextStyle.Default,
        captionMedium = TextStyle.Default,
        captionMediumStrong = TextStyle.Default,
        codeMediumStrong = TextStyle.Default,
        codeSmall = TextStyle.Default,
        codeSmallStrong = TextStyle.Default
    )
}

@Preview(widthDp = 1000, showBackground = true)
@Composable
private fun TypographyPreview() {
    val textStyles = listOf(
        "headingXLarge" to Typography.headingXLarge,
        "headingLarge" to Typography.headingLarge,
        "headingMedium" to Typography.headingMedium,
        "headingSmall" to Typography.headingSmallStrong,
        "bodyLarge" to Typography.bodyLarge,
        "bodyLargeStrong" to Typography.bodyLargeStrong,
        "bodyMedium" to Typography.bodyMedium,
        "bodyMediumStrong" to Typography.bodyMediumStrong,
        "bodySmall" to Typography.bodySmall,
        "bodySmallStrong" to Typography.bodySmallStrong,
        "buttonLarge" to Typography.buttonLarge,
        "buttonMedium" to Typography.buttonMedium,
        "buttonExtraLarge" to Typography.buttonExtraLarge,
        "captionMedium" to Typography.captionMedium,
        "captionMediumStrong" to Typography.captionMediumStrong,
        "codeMediumStrong" to Typography.codeMediumStrong,
        "codeSmall" to Typography.codeSmall,
        "codeSmallStrong" to Typography.codeSmallStrong
    )
    RecipesBookTheme {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            textStyles.forEach { (name, style) ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(160.dp),
                        text = name,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "Almost before we knew it, we had left the ground.",
                        style = style
                    )
                }
                Divider()
            }
        }
    }
}
