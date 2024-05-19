package com.okrama.recipesbook.ui.core.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.RecipesBookTheme
import com.okrama.recipesbook.ui.core.theme.backgroundLight

@Composable
fun RecipeGalleryImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    onImageAdded: (String) -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = RecipesBookTheme.elevation.small
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundLight
        )
    ) {

        var imageUri by remember {
            mutableStateOf<Uri?>(if (imageUrl == null) null else Uri.parse(imageUrl))
        }

        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                onImageAdded(it.toString())
                imageUri = it
            }
        }

        Column(
            modifier = Modifier
                .clickable { launcher.launch("image/*") }
                .background(backgroundLight)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (imageUri != null) {
                imageUri?.let {
                    ImageComponent(
                        imageUri = it.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "",
                    )
                }
            } else if (imageUrl != null) {
                ImageComponent(
                    imageUri = imageUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "",
                )
            } else {
                val addPhotoLabel = stringResource(id = R.string.add_photo_label)
                ImageComponent(
                    modifier = Modifier.size(70.dp),
                    contentDescription = addPhotoLabel,
                    defaultIcon = R.drawable.ic_add_a_photo_24,
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { contentDescription = addPhotoLabel },
                    text = addPhotoLabel,
                    style = RecipesBookTheme.typography.headingSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeGalleryImagePreview() {
    RecipesBookTheme {
        RecipeGalleryImage(
            onImageAdded = {},
        )
    }
}