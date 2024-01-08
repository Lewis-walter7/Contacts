package com.licodingpro.contacts.presentation

import com.licodingpro.contacts.domain.models.Contact

sealed interface ContactListEvent {
    object OnAddNewContactClick: ContactListEvent
    object DismissContact: ContactListEvent
    data class OnFirstNameChange(val value: String): ContactListEvent
    data class OnLastNameChange(val value: String): ContactListEvent
    data class OnEmailChange(val value: String): ContactListEvent
    data class OnPhoneNumberChange(val value: String): ContactListEvent
    class OnPhotoPicked(val bytes: ByteArray): ContactListEvent
    object OnAddPhotoClicked: ContactListEvent
    data class SelectContact(val contact: Contact): ContactListEvent
    data class EditContact(val contact: Contact): ContactListEvent
    object DelectContact: ContactListEvent
    object SaveContact: ContactListEvent
}