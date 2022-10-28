package com.example.novelreader.utility

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore

object ImageUtility {

    // TODO save image
    // TODO read image

    fun saveCover(imageUrl: String, context: Context) {
        // TODO save cover


        // Add a specific media item.
        val resolver = context.contentResolver

        // Find all audio files on the primary external storage device.
        val imageCollection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        // Publish a new image.
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "cover.jpg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
                put(MediaStore.Images.Media.RELATIVE_PATH, "NovelReader/covers")
            }
        }

        // Keeps a handle to the new image's URI in case we need to modify it
        // later.
        val imageContentUri = resolver.insert(imageCollection, imageDetails)

        imageContentUri?.let {
            resolver
                .openFileDescriptor(imageContentUri, "w", null)
                .use { pfd ->
                    // Write data into the pending image file.
                }

            // Now that we're finished, release the "pending" status, and allow other apps
            // to read the image.
            imageDetails.clear()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageDetails.put(MediaStore.Audio.Media.IS_PENDING, 0)
            }
            resolver.update(imageContentUri, imageDetails, null, null)
        }
    }

    fun saveIllustration() {
        TODO("Not yet implemented")
    }

    fun readCover() {
        TODO("Not yet implemented")
    }

    fun readIllustration() {
        TODO("Not yet implemented")
    }
}