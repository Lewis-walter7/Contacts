package com.licodingpro.contacts.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.licodingpro.contacts.domain.models.Contact
import com.licodingpro.contacts.presentation.ContactListEvent
import com.licodingpro.contacts.presentation.ContactListState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    state: ContactListState,
    onEvent: (ContactListEvent) -> Unit,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                ModalSheetContent(drawerState)
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Contacts"
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                TODO()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onEvent(ContactListEvent.OnAddNewContactClick)
                        navController.navigate("addcontact")
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PersonAdd,
                        contentDescription = "Add Contact"
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 60.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "My Contacts (${state.contacts.size})",
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                items(state.contacts) { contact ->
                    ContactListItem(
                        contact = contact,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                onEvent(ContactListEvent.SelectContact(contact))
                            }
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}