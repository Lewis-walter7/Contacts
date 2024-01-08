package com.licodingpro.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licodingpro.contacts.data.ContactDao
import com.licodingpro.contacts.domain.models.Contact
import com.licodingpro.contacts.domain.models.SortType
import com.licodingpro.contacts.presentation.utils.ContactValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val dao: ContactDao
): ViewModel() {
    private val _state = MutableStateFlow(ContactListState())

    private val sortType = MutableStateFlow(SortType.FIRSTNAME)

    @OptIn(ExperimentalCoroutinesApi::class)
    val contacts = sortType
        .flatMapLatest { sortype ->
            when(sortype) {
                SortType.FIRSTNAME -> dao.getContactsByFirstname()
                SortType.LASTNAME -> dao.getContactsByLastname()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(
        _state,
        sortType,
        contacts
    ) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent){
        when(event) {
            is ContactListEvent.DelectContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.let { contact ->
                        _state.update {
                            it.copy(
                                isSelectedContactShetOpen = false
                            )
                        }
                        dao.deleteContact(contact)
                        _state.update {
                            it.copy(
                                selectedContact = null
                            )
                        }
                    }
                }
            }
            is ContactListEvent.DismissContact -> {
                _state.update {
                    it.copy(
                        isSelectedContactShetOpen = false,
                        isAddContactSheetOpen = false,
                        firstNameError = null,
                        lastNameError = null,
                        emailError = null,
                        phoneNumberError = null
                    )
                }
                newContact = null
                _state.update {
                    it.copy(
                        selectedContact = null
                    )
                }
            }
            is ContactListEvent.EditContact -> {
                _state.update {
                    it.copy(
                        isAddContactSheetOpen = true,
                        selectedContact = event.contact
                    )
                }
            }
            is ContactListEvent.OnAddNewContactClick -> {
                _state.update {
                    it.copy(
                        isAddContactSheetOpen = true
                    )
                }
            }
            is ContactListEvent.OnEmailChange -> {
                newContact = newContact?.copy(
                    email = event.value
                )
            }
            is ContactListEvent.OnFirstNameChange -> {
                newContact = newContact?.copy(
                    firstName = event.value
                )
            }
            is ContactListEvent.OnLastNameChange -> {
                val lastname = event.value
                newContact = newContact?.copy(
                    lastName = lastname
                )

                println(newContact?.lastName)
            }
            is ContactListEvent.OnPhoneNumberChange -> {
                newContact = newContact?.copy(
                    phoneNumber = event.value
                )
            }
            is ContactListEvent.OnPhotoPicked -> {
                newContact = newContact?.copy(
                    photoBytes = event.bytes
                )
            }
            is ContactListEvent.SelectContact -> {
                _state.update {
                    it.copy(
                        selectedContact = event.contact,
                        isSelectedContactShetOpen = true
                    )
                }
            }
            is ContactListEvent.SaveContact -> {
                newContact?.let { contact ->
                    val result = ContactValidator.validateContact(contact)
                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError,
                    )

                    if (errors.isEmpty()){
                        _state.update {
                            it.copy(
                                firstNameError = null,
                                lastNameError = null,
                                phoneNumberError = null,
                                emailError = null
                            )
                        }

                        viewModelScope.launch {
                            dao.upsertContact(contact)
                        }
                        newContact = null
                    } else {
                        _state.update {
                            it.copy(
                                firstNameError = result.firstNameError,
                                lastNameError = result.lastNameError,
                                emailError = result.emailError,
                                phoneNumberError = result.phoneNumberError
                            )
                        }
                    }
                }
            }
            else -> Unit
        }
    }
}
