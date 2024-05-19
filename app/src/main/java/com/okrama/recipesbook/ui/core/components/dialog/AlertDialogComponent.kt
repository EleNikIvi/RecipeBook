package com.okrama.recipesbook.ui.core.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.okrama.recipesbook.ui.core.LoremIpsum
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.alertDialogButtonColors
import com.okrama.recipesbook.ui.core.theme.onBackgroundLight
import com.okrama.recipesbook.ui.core.theme.primaryLight

@Composable
fun AlertDialogComponent(
    title: String,
    message: String,
    icon: ImageVector? = null,
    confirmButtonText: String,
    confirmButtonClicked: () -> Unit,
    dismissButtonText: String? = null,
    dismissButtonClicked: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = { /* modal dialog by default, NOOP */ },
) {
    AlertDialogComponent(
        title = title,
        confirmButtonText = confirmButtonText,
        confirmButtonClicked = confirmButtonClicked,
        dismissButtonText = dismissButtonText,
        dismissButtonClicked = dismissButtonClicked,
        onDismissRequest = onDismissRequest,
        icon = icon
    ) {
        Text(
            modifier = Modifier.testTag("alert_dialog_message"),
            text = message,
            style = RecipesBookTheme.typography.bodyLarge,
            color = onBackgroundLight
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogComponent(
    title: String,
    icon: ImageVector? = null,
    confirmButtonText: String,
    confirmButtonClicked: () -> Unit,
    dismissButtonText: String? = null,
    dismissButtonClicked: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = { /* modal dialog by default, NOOP */ },
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .widthIn(min = 280.dp, max = 560.dp),
            shape = DialogStyle.shape,
            color = DialogStyle.color
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = DialogStyle.contentPadding)
                        .padding(top = DialogStyle.contentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon?.let { icon ->
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp, end = 16.dp)
                                .size(36.dp),
                            imageVector = icon,
                            tint = primaryLight,
                            contentDescription = null,
                        )
                    }

                    Text(
                        modifier = Modifier.testTag("alert_dialog_title"),
                        text = title,
                        style = RecipesBookTheme.typography.headingSmall,
                        color = onBackgroundLight
                    )
                }
                Box(modifier = Modifier.padding(horizontal = DialogStyle.contentPadding)) {
                    content()
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = DialogStyle.buttonPadding)
                        .padding(bottom = DialogStyle.buttonPadding),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        dismissButtonText?.let { text ->
                            DialogButton(
                                text = text,
                                capitalize = DialogStyle.capitalizeButton,
                                onClick = { dismissButtonClicked?.invoke() },
                            )
                        }
                        DialogButton(
                            text = confirmButtonText,
                            capitalize = DialogStyle.capitalizeButton,
                            onClick = confirmButtonClicked,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    text: String,
    capitalize: Boolean,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        colors = alertDialogButtonColors
    ) {
        Text(
            text = if (capitalize) text.uppercase() else text,
            style = RecipesBookTheme.typography.bodyLarge
        )
    }
}

@Preview(group = "dialog size", showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AlertDialogPreview() {
    RecipesBookTheme {
        AlertDialogComponent(
            title = LoremIpsum.SHORT,
            message = LoremIpsum.LONG,
            confirmButtonClicked = {},
            confirmButtonText = "Confirm",
            dismissButtonClicked = {},
            dismissButtonText = "Dismiss"
        )
    }
}

@Preview(group = "dialog variation", showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AlertDialogWithIconPreview() {
    RecipesBookTheme {
        AlertDialogComponent(
            title = LoremIpsum.SHORT,
            message = LoremIpsum.LONG,
            confirmButtonClicked = {},
            confirmButtonText = "Confirm",
            dismissButtonClicked = {},
            dismissButtonText = "Dismiss",
            icon = Icons.Rounded.Edit
        )
    }
}

@Preview(group = "dialog variation", showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AlertDialogVariationPreview() {
    RecipesBookTheme {
        AlertDialogComponent(
            title = "Dialog variation",
            message = "This alert dialog has no dismiss button.",
            confirmButtonClicked = {},
            confirmButtonText = "Confirm"
        )
    }
}

@Preview(group = "dialog variation", showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AlertDialogContentPreview() {
    RecipesBookTheme {
        AlertDialogComponent(
            title = "Custom content",
            confirmButtonClicked = {},
            confirmButtonText = "Confirm"
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color.Blue
            )
        }
    }
}

@Preview(group = "dialog style", showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AlertDialogMaterial3StylePreview() {
    RecipesBookTheme {
        AlertDialogComponent(
            title = "Material 3 style",
            message = "This alert dialog has Material 3 styling.",
            confirmButtonClicked = {},
            confirmButtonText = "Confirm",
        )
    }
}