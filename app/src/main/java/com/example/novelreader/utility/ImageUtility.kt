package com.example.novelreader.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream


object ImageUtility {

    fun saveCover(imageUrl: String, title: String, context: Context): String {
        val mediaStorageDir = context.filesDir
        val dirName = "${mediaStorageDir!!.absoluteFile}/$title"
        val fileName = "cover.png"

        saveImage(imageUrl, dirName, fileName, context)

        return getCoverPath(title, context)
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

    fun getCoverPath(title: String, context: Context): String {
        val dirName = getTitlePath(title, context)
        val fileName = "cover.png"
        return "$dirName/$fileName"
    }

    fun getTitlePath(title: String, context: Context): String {
        val mediaStorageDir = context.filesDir
        return "${mediaStorageDir!!.absoluteFile}/$title"
    }

    fun removeDir(dirPath: String) {
        val dir = File(dirPath)
        val files = dir.listFiles() ?: emptyArray()
        for (f in files) {
            if (f.isDirectory)
                removeDir(f.absolutePath)
            else
                f.delete()
        }
        dir.delete()
    }

    private fun saveImage(imageUrl: String, dirName: String, fileName: String, context: Context) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    if (isExternalStorageWritable()) {
                        val myDir = File(dirName)
                        if (!myDir.exists()) myDir.mkdirs()

                        val file = File(myDir, fileName)
                        if (file.exists()) file.delete()

                        try {
                            val out = FileOutputStream(file)
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                            out.flush()
                            out.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(context, "Błąd", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}