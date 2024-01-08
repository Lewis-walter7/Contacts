package com.licodingpro.contacts.presentation

import com.licodingpro.contacts.domain.models.Contact
import com.licodingpro.contacts.domain.models.SortType

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val recentlyAddedContacts: List<Contact> = emptyList(),
    val selectedContact: Contact? = null,
    val isAddContactSheetOpen: Boolean = false,
    val isSelectedContactShetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,
    val sortType: SortType = SortType.FIRSTNAME
)
