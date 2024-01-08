package com.licodingpro.contacts.presentation.utils

import android.content.ContentUris
import android.content.Context
import android.provider.ContactsContract
import com.licodingpro.contacts.domain.models.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ContactsProvider(context: Context): List<Contact>{
    val contacts = mutableListOf<Contact>()

    val contentUri = ContactsContract.Contacts.CONTENT_URI

    val projection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
        ContactsContract.Contacts.PHOTO_URI
    )
    val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
    context.contentResolver.query(
        contentUri,
        projection,
        null,
        null,
        sortOrder,
    )?.use {cursor ->
        val idColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
        val nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val phoneNumberColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
        val emailColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val phoneNumber = cursor.getInt(phoneNumberColumn)
            //val email = cursor.getString(emailColumn)
            val photoUri = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,
                id
            )
            CoroutineScope(Dispatchers.IO).launch {
                val photoBytes = rememberByteFromUri(photoUri, context)
                contacts.add(
                    Contact(
                        id = id,
                        phoneNumber = phoneNumber.toString(),
                        email = "email@gmail.com",
                        firstName = name.split("\\s+".toRegex())[0],
                        lastName = name.split("\\s+".toRegex())[1],
                        photoBytes = null
                    )
                )
            }
        }
    }

    return contacts
}