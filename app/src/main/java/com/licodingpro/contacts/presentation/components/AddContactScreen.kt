package com.licodingpro.contacts.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.licodingpro.contacts.domain.models.Contact
import com.licodingpro.contacts.presentation.ContactListEvent
import com.licodingpro.contacts.presentation.ContactListState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddContactScreen(
    state:ContactListState,
    //newContact: Contact?,
    onEvent: (ContactListEvent) -> Unit,
    navController: NavController
) {
    var firstname by remember {
        mutableStateOf("")
    }
    var lastname by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var phonenumber by remember {
        mutableStateOf("")
    }

    var newContact: Contact? by remember {
        mutableStateOf(null)
    }

    println(newContact?.lastName)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create new Contact")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(ContactListEvent.DismissContact)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Button(
                       onClick = {
                           onEvent(ContactListEvent.SaveContact)
                       }
                    ) {
                        Text(
                            text = "SAVE",
                        )
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFF60797d))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp),
                        tint = Color.LightGray,
                    )
                }
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
                error = state.firstNameError,
                placeholder = "First name",
                onValueChange = {
                    onEvent(ContactListEvent.OnFirstNameChange(firstname))
                },
                value = firstname,
                icon = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
                error = state.lastNameError,
                placeholder = "Last name",
                onValueChange = {
                    newContact?.lastName = it
                    onEvent(ContactListEvent.OnLastNameChange(lastname))
                },
                value = newContact?.lastName ?: "",
                icon = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
                error = state.emailError,
                placeholder = "Email",
                onValueChange = {
                    email = it
                    onEvent(ContactListEvent.OnEmailChange(email))
                },
                value = email,
                icon = Icons.Default.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            ContactField(
                error = state.phoneNumberError,
                placeholder = "Phone number",
                onValueChange = {
                    phonenumber = it
                    onEvent(ContactListEvent.OnPhoneNumberChange(phonenumber))
                },
                value = phonenumber,
                icon = Icons.Default.ContactPhone
            )
        }
    }
}

@Composable
private fun ContactField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    icon: ImageVector
){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder
                )
            },
            shape = RoundedCornerShape(20),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        )
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 15.sp
            )
        }
    }
}