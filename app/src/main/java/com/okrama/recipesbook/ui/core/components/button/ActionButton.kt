package com.okrama.recipesbook.ui.core.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.ui.core.theme.outlineLight
import com.okrama.recipesbook.ui.core.theme.outlineVariantLight

@Composable
fun ActionButton(
    onAction: () -> Unit,
    icon: ImageVector,
    @StringRes iconContentResId: Int
) {
    Box(modifier = Modifier.padding(8.dp)) {
        IconButton(
            onClick = onAction,
            enabled = true,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 25.dp))
                .size(35.dp)
                .background(outlineLight),
        ) {
            Icon(
                painter = rememberVectorPainter(image = icon),
                tint = outlineVariantLight,
                contentDescription = stringResource(id = iconContentResId)
            )
        }
    }
}