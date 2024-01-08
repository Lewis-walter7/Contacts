package com.licodingpro.contacts.presentation.utils

import android.util.Patterns
import com.licodingpro.contacts.domain.models.Contact

object ContactValidator {

    fun validateContact(contact: Contact): Validator {
        var result = Validator()

        if (contact.firstName.isBlank()){
            result = result.copy(
                firstNameError = "The first name is required"
            )
        }
        if (contact.lastName.isBlank()){
            result = result.copy(
                lastNameError = "The last name is required"
            )
        }
        if (contact.phoneNumber.isBlank()){
            result = result.copy(
                phoneNumberError = "The phoneNumber is required"
            )
        }
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (!contact.email.matches(emailRegex.toRegex())){
            result = result.copy(
                emailError = "The email provided is invalid"
            )
        }
        return result
    }

    data class Validator(
        val firstNameError: String? = "",
        val lastNameError: String? = "",
        val emailError: String? = "",
        val phoneNumberError: String? = "",
    )
}