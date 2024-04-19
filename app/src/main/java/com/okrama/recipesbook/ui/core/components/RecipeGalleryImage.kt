package com.okrama.recipesbook.ui.core.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okrama.recipesbook.R
import com.okrama.recipesbook.ui.core.theme.Grey3
import com.okrama.recipesbook.ui.core.theme.Grey0

@Composable
fun RecipeGalleryImage(
    modifier: Modifier,
    imageUrl: String?,
    onImageAdded: (String) -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Grey3
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
                imageUri = uri
            }
        }

        Column(
            modifier = Modifier
                .clickable { launcher.launch("image/*") }
                .background(Grey0)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Log.d(
                "RecipeGalleryImage",
                "imageUri $imageUri"
            )
            if (imageUri != null) {
                imageUri?.let {
                    ImageComponent(
                        imageUri = it.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Recipe image",
                    )
                }
            } else {
                ImageComponent(
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Meal photo", // TODO resource
                    defaultIcon = R.drawable.ic_add_a_photo_24,
                )
                Text(
                    modifier = Modifier
                        .semantics { contentDescription = "Add a photo" }, // TODO resource
                    text = "Add a photo", // TODO resource
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}