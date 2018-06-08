package com.example.hermivaldo.projetoinicial.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeResource
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import java.io.File
import java.io.IOException
import android.widget.ImageView
import com.example.hermivaldo.projetoinicial.R
import android.graphics.drawable.VectorDrawable
import android.os.Build




class ImageConversor {

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        return image
    }

    fun loadImageFromResource(mImageView: ImageView, context: Context,resource: Int?){
        var bitmap: Bitmap?

        if (resource == null){
            bitmap = decodeResource(context.resources, R.drawable.book)
            mImageView.setImageBitmap(bitmap)
        }else {
            var drawable = context.resources.getDrawable(resource!!)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val vectorDrawable = drawable as VectorDrawable
                mImageView.setImageDrawable(vectorDrawable)
            } else {
                val bitmapDrawable = drawable as BitmapDrawable
                mImageView.setImageBitmap(bitmapDrawable.bitmap)
            }

        }


    }

    fun setPic(mImageView: ImageView, mCurrentPhotoPath: String) {
        // tamanho fixo pois quando vem uma imageView dentro de um list, mesmo com tamanho fixo
        // está chegando zerada para o objeto fazendo uma divisão por 0 e dndo erro
        var targetW = 400
        var targetH = 400

        if (mImageView.width != 0){
            targetW = mImageView.width
        }

        if (mImageView.height != 0){
            targetH = mImageView.height
        }
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true

        val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        mImageView.setImageBitmap(bitmap)
    }
}