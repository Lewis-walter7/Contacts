package com.licodingpro.contacts.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.licodingpro.contacts.domain.models.ModalNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalSheetContent(drawerState: DrawerState) {
    var selected by remember {
        mutableIntStateOf(0)
    }
    ModalDrawerSheet(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Contacts",
                fontSize = 20.sp
            )
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
        )
        Labels()
    }
}

@Composable
fun Labels(){
    val items = listOf(
        ModalNavigationItem(
            icon = Icons.Filled.Label,
            label = "Company"
        ),
        ModalNavigationItem(
            icon = Icons.Filled.Label,
            label = "Family"
        ),
        ModalNavigationItem(
            icon = Icons.Filled.Label,
            label = "Friends"
        ),
        ModalNavigationItem(
            icon = Icons.Filled.Label,
            label = "Others"
        ),
        ModalNavigationItem(
            icon = Icons.Filled.Label,
            label = "VIP"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.label,
                    fontSize = 20.sp
                )
            }
        }
    }
}