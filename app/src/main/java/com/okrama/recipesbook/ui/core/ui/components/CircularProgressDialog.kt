package com.okrama.recipesbook.ui.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.ui.theme.RecipesBookTheme

@Composable
fun CircularProgressDialog(
    message: String,
) {
    Dialog(onDismissRequest = { /*NOOP*/ }) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = colorResource(id = R.color.white)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.purple_500)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = message,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun CircularProgressMaterial3Preview() {
    RecipesBookTheme {
        CircularProgressDialog(
            message = "LoremIpsum",
        )
    }
}
