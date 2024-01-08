package com.licodingpro.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.compose.ContactsTheme
import com.licodingpro.contacts.data.ContactDatabase
import com.licodingpro.contacts.presentation.ContactListViewModel
import com.licodingpro.contacts.presentation.components.AddContactScreen
import com.licodingpro.contacts.presentation.components.ContactListScreen

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact.db"
        ).build()
    }
    private val viewModel by viewModels<ContactListViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactListViewModel(db.contactDao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )
        //val contacts = ContactsProvider(this)
        super.onCreate(savedInstanceState)
        setContent {
            ContactsTheme {
                val state by viewModel.state.collectAsState()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        ContactListScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }
                    composable("addcontact") {
                        AddContactScreen(
                            state = state,
                            //newContact = viewModel.newContact,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
