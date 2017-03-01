package com.krepchenko.a5ideas.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.File
import java.util.*

/**
 * Created by krepc on 01.03.2017.
 */
object BitmapUtils {


    fun bitmapToUriConverter(context:Context,bitmap: Bitmap): Uri {
        var uri: Uri? = null
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            val newBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200,
                    true)
            val file = File(context.filesDir, "Image"
                    + Random().nextInt() + ".jpeg")
            val out = context.openFileOutput(file.name,
                    Context.MODE_WORLD_READABLE)
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            //get absolute path
            val realPath = file.absolutePath
            val f = File(realPath)
            uri = Uri.fromFile(f)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri!!
    }
}