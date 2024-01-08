package com.licodingpro.contacts.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.licodingpro.contacts.domain.models.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsByFirstname(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsByLastname(): Flow<List<Contact>>

    @Query("SELECT * FROM contact LIMIT 10")
    fun getRecentContacts(): Flow<List<Contact>>
}