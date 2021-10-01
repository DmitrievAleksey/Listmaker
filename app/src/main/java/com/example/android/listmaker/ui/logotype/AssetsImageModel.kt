package com.example.android.listmaker.ui.logotype

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory

private const val IMAGE_FOLDER = "sample_image"

data class AssetsImageModel(val name: String, val bitmap: Bitmap)

fun loadLogoImages(assetManager: AssetManager): List<AssetsImageModel> {
    val imageModels: MutableList<AssetsImageModel> = mutableListOf()
    return try {
        val imageList = assetManager.list(IMAGE_FOLDER)!!
        imageList.asList()

        for (asset in imageList) {
            val bytes = assetManager.open("$IMAGE_FOLDER/$asset")
                .use { inputStream -> inputStream.readBytes() }
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            imageModels.add(AssetsImageModel(asset, bmp))
        }
        imageModels
    } catch (e: Exception) {
        emptyList()
    }
}
