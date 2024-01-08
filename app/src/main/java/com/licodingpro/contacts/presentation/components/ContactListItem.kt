package com.licodingpro.contacts.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.licodingpro.contacts.domain.models.Contact

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier
) {

    val name = "${contact.firstName} ${contact.lastName}"
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactPhoto(
            contact,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
    }
}