package com.licodingpro.contacts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.licodingpro.contacts.domain.models.Contact

@Database(
    entities = [Contact::class],
    version = 2,
    exportSchema = false
)
abstract class ContactDatabase: RoomDatabase() {
    abstract val contactDao: ContactDao
}