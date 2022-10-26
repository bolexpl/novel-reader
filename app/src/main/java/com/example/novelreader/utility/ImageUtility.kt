package com.example.novelreader.utility

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.net.URL


object ImageUtility {

    // TODO save image
    // TODO read image

    fun saveCover(imageUrl: String) {
        saveFileToExternalStorage(imageUrl, "plik.")
    }

    fun saveFileToExternalStorage(url: String, fileName: String) {
        val target = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        URL(url).openStream().use { input ->
            FileOutputStream(target).use { output ->
                input.copyTo(output)
            }
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