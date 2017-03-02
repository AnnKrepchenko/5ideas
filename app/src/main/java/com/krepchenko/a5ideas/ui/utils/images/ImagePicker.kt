package com.krepchenko.a5ideas.ui.utils.images

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import com.krepchenko.a5ideas.R
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

object ImagePicker {

    val PICK_IMAGE_REQUEST_CODE = 221
    private val DEFAULT_MIN_WIDTH_QUALITY = 600        // min pixels
    private val DEFAULT_MIN_HEIGHT_QUALITY = 600        // min pixels
    private val TAG = ImagePicker::class.java.simpleName
    private val TEMP_IMAGE_NAME = "tempImage"

    private var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY
    private var minHeightQuality = DEFAULT_MIN_HEIGHT_QUALITY

    /**
     * Launch a dialog to pick an image from camera/gallery apps.

     * @param activity which will launch the dialog.
     */
    fun pickImage(activity: Activity) {
        val chooserTitle = activity.getString(R.string.pick_image_intent_text)
        pickImage(activity, chooserTitle)
    }

    /**
     * Launch a dialog to pick an image from camera/gallery apps.

     * @param fragment which will launch the dialog and will get the result in
     * *                 onActivityResult()
     */
    fun pickImage(fragment: Fragment) {
        val chooserTitle = fragment.getString(R.string.pick_image_intent_text)
        pickImage(fragment, chooserTitle)
    }

    /**
     * Launch a dialog to pick an image from camera/gallery apps.

     * @param activity     which will launch the dialog.
     * *
     * @param chooserTitle will appear on the picker dialog.
     */
    fun pickImage(activity: Activity, chooserTitle: String) {
        val chooseImageIntent = getPickImageIntent(activity, chooserTitle)
        activity.startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * Launch a dialog to pick an image from camera/gallery apps.

     * @param fragment     which will launch the dialog and will get the result in
     * *                     onActivityResult()
     * *
     * @param chooserTitle will appear on the picker dialog.
     */
    fun pickImage(fragment: Fragment, chooserTitle: String) {
        val chooseImageIntent = getPickImageIntent(fragment.context, chooserTitle)
        fragment.startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * Get an Intent which will launch a dialog to pick an image from camera/gallery apps.

     * @param context      context.
     * *
     * @param chooserTitle will appear on the picker dialog.
     * *
     * @return intent launcher.
     */
    fun getPickImageIntent(context: Context, chooserTitle: String): Intent? {
        var chooserIntent: Intent? = null
        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intentList = addIntentsToList(context, intentList, pickIntent)
        if (!appManifestContainsPermission(context, Manifest.permission.CAMERA) || hasCameraAccess(context)) {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoIntent.putExtra("return-data", true)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTemporalFile(context)))
            intentList = addIntentsToList(context, intentList, takePhotoIntent)
        }

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1),
                    chooserTitle)
            chooserIntent!!.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toTypedArray<Parcelable>())
        }

        return chooserIntent
    }

    private fun addIntentsToList(context: Context, list: MutableList<Intent>, intent: Intent): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.`package` = packageName
            list.add(targetedIntent)
        }
        return list
    }

    /**
     * Checks if the current context has permission to access the camera.
     * @param context             context.
     */
    private fun hasCameraAccess(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if the androidmanifest.xml contains the given permission.
     * @param context             context.
     * *
     * @return Boolean, indicating if the permission is present.
     */
    private fun appManifestContainsPermission(context: Context, permission: String): Boolean {
        val pm = context.packageManager
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            var requestedPermissions: Array<String>? = null
            if (packageInfo != null) {
                requestedPermissions = packageInfo.requestedPermissions
            }
            if (requestedPermissions == null) {
                return false
            }

            if (requestedPermissions.size > 0) {
                val requestedPermissionsList = Arrays.asList(*requestedPermissions)
                return requestedPermissionsList.contains(permission)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * Called after launching the picker with the same values of Activity.getImageFromResult
     * in order to resolve the result and get the image.

     * @param context             context.
     * *
     * @param requestCode         used to identify the pick image action.
     * *
     * @param imageReturnedIntent returned intent where is the image data.
     * *
     * @return image.
     */
    fun getImageFromResult(context: Context, requestCode: Int,
                           imageReturnedIntent: Intent?): Bitmap? {
        var bm: Bitmap? = null
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            val imageFile = getTemporalFile(context)
            val selectedImage: Uri
            val isCamera = imageReturnedIntent == null
                    || imageReturnedIntent.data == null
                    || imageReturnedIntent.data.toString().contains(imageFile.toString())
            if (isCamera) {
                selectedImage = Uri.fromFile(imageFile)
            } else {
                selectedImage = imageReturnedIntent!!.data
            }
            bm = getImageResized(context, selectedImage)
            val rotation = ImageRotator.getRotation(context, selectedImage, isCamera)
            bm = ImageRotator.rotate(bm, rotation)
        }
        return bm
    }

    private fun getTemporalFile(context: Context): File {
        return File(context.externalCacheDir, TEMP_IMAGE_NAME)
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     */
    private fun getImageResized(context: Context, selectedImage: Uri): Bitmap? {
        var bm: Bitmap?
        val sampleSizes = intArrayOf(5, 3, 2, 1)
        var i = 0
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i])
            i++
        } while (bm != null
                && (bm.width < minWidthQuality || bm.height < minHeightQuality)
                && i < sampleSizes.size)
        return bm
    }

    private fun decodeBitmap(context: Context, theUri: Uri, sampleSize: Int): Bitmap? {
        var actuallyUsableBitmap: Bitmap? = null
        val fileDescriptor: AssetFileDescriptor
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize

        try {
            fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri, "r")
            actuallyUsableBitmap = BitmapFactory
                    .decodeFileDescriptor(fileDescriptor.fileDescriptor, null, options)
            if (actuallyUsableBitmap != null) {
                Log.d(TAG, "Trying sample size " + options.inSampleSize + "\t\t"
                        + "Bitmap width: " + actuallyUsableBitmap.width
                        + "\theight: " + actuallyUsableBitmap.height)
            }
            fileDescriptor.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return actuallyUsableBitmap
    }

    fun setMinQuality(minWidthQuality: Int, minHeightQuality: Int) {
        ImagePicker.minWidthQuality = minWidthQuality
        ImagePicker.minHeightQuality = minHeightQuality
    }
}
