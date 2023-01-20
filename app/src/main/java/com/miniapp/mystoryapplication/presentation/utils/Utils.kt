package com.miniapp.mystoryapplication.presentation.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.paging.PagingData
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last

fun Uri.uriToFile(context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(this) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun File.reduceFileImage(isCameraBack: Boolean): File {
    val bitmap = rotateBitmap(
        BitmapFactory.decodeFile(this.path),
        isCameraBack
    )
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(this))
    return this
}

@Suppress("UNCHECKED_CAST")
suspend fun <T : Any> PagingData<T>.toList(): List<T> {
    val flow = PagingData::class.java.getDeclaredField("flow").apply {
        isAccessible = true
    }.get(this) as Flow<Any?>
    val pageEventInsert = flow.last()
    val pageEventInsertClass = Class.forName("androidx.paging.PageEvent\$Insert")
    val pagesField = pageEventInsertClass.getDeclaredField("pages").apply {
        isAccessible = true
    }
    val pages = pagesField.get(pageEventInsert) as List<Any?>
    val transformablePageDataField =
        Class.forName("androidx.paging.TransformablePage").getDeclaredField("data").apply {
            isAccessible = true
        }
    val listItems =
        pages.flatMap { transformablePageDataField.get(it) as List<*> }
    return listItems as List<T>
}