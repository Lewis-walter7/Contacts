package com.licodingpro.contacts.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "firstname") val firstName: String,
    @ColumnInfo(name = "lastname") var lastName: String,
    @ColumnInfo(name = "phonenumber") val phoneNumber: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "photoBytes")val photoBytes: ByteArray?
)
