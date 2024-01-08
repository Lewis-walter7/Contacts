package com.licodingpro.contacts.presentation.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun rememberByteFromUri(uri: Uri?, context: Context): ByteArray? {
    return withContext(Dispatchers.IO) {
        uri?.let { uri ->
            try {
                context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}