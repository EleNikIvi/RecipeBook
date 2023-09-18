package com.okrama.recipesbook.core.ui

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(group = "phone", showSystemUi = true, device = Devices.PHONE)
@Preview(group = "phone_landscape", showSystemUi = true, device = Devices.FOLDABLE)
@Preview(group = "tablet", showSystemUi = true, device = Devices.TABLET)
@Preview(group = "foldable", showSystemUi = true, device = Devices.FOLDABLE)
annotation class DevicePreviews