package com.okrama.recipesbook.ui.core.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//Light
val primaryLight = Color(0xFF606219)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFE6E890)
val onPrimaryContainerLight = Color(0xFF1C1D00)
val secondaryLight = Color(0xFF606043)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFE6E4C0)
val onSecondaryContainerLight = Color(0xFF1C1D06)
val tertiaryLight = Color(0xFF3D6657)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFF84976D)
val onTertiaryContainerLight = Color(0xFF000000)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFFDF9EC) // Background
val onBackgroundLight = Color(0xFF1C1C14)
val surfaceLight = Color(0xFFFDF9EC)
val onSurfaceLight = Color(0xFF1C1C14)
val surfaceVariantLight = Color(0xFFE6E3D1)
val onSurfaceVariantLight = Color(0xFF48473A)
val outlineLight = Color(0xFF797869)
val outlineVariantLight = Color(0xFFC9C7B6)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF313128)
val inverseOnSurfaceLight = Color(0xFFF4F1E3)
val inversePrimaryLight = Color(0xFFCACB77)
val inversePrimaryContainerLight = Color(0xFF666737)
val surfaceDimLight = Color(0xFFDDDACD)
val surfaceBrightLight = Color(0xFFFDF9EC)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF7F4E6)
val surfaceContainerLight = Color(0xFFF1EEE1)
val surfaceContainerHighLight = Color(0xFFEBE8DB)
val surfaceContainerHighestLight = Color(0xFFE6E3D6)

//Dark
val primaryDark = Color(0xFFCACB77)
val onPrimaryDark = Color(0xFF313300)
val primaryContainerDark = Color(0xFF484A00)
val onPrimaryContainerDark = Color(0xFFE6E890)
val secondaryDark = Color(0xFFC9C8A5)
val onSecondaryDark = Color(0xFF313219)
val secondaryContainerDark = Color(0xFF48482D)
val onSecondaryContainerDark = Color(0xFFE6E4C0)
val tertiaryDark = Color(0xFFA4D0BE)
val onTertiaryDark = Color(0xFF0A372B)
val tertiaryContainerDark = Color(0xFF244E40)
val onTertiaryContainerDark = Color(0xFFBFECD9)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF14140C)
val onBackgroundDark = Color(0xFFE6E3D6)
val surfaceDark = Color(0xFF14140C)
val onSurfaceDark = Color(0xFFE6E3D6)
val surfaceVariantDark = Color(0xFF48473A)
val onSurfaceVariantDark = Color(0xFFC9C7B6)
val outlineDark = Color(0xFF939182)
val outlineVariantDark = Color(0xFF48473A)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE6E3D6)
val inverseOnSurfaceDark = Color(0xFF313128)
val inversePrimaryDark = Color(0xFF606219)
val surfaceDimDark = Color(0xFF14140C)
val surfaceBrightDark = Color(0xFF3A3A30)
val surfaceContainerLowestDark = Color(0xFF0F0F07)
val surfaceContainerLowDark = Color(0xFF1C1C14)
val surfaceContainerDark = Color(0xFF202018)
val surfaceContainerHighDark = Color(0xFF2B2A22)
val surfaceContainerHighestDark = Color(0xFF36352C)


internal val alertDialogButtonColors
    @Composable
    get() = ButtonDefaults.textButtonColors(
        contentColor = tertiaryLight,
        disabledContentColor = outlineLight
    )

internal val radioButtonColors
    @Composable
    get() = RadioButtonDefaults.colors(
        selectedColor = tertiaryLight,
        unselectedColor = outlineLight
    )

internal val checkboxColors
    @Composable
    get() = CheckboxDefaults.colors(
        checkedColor = tertiaryLight,
        uncheckedColor = outlineLight
    )