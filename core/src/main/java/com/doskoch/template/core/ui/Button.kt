package com.doskoch.template.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.doskoch.template.core.theme.Dimensions
import java.util.*

@Composable
fun CoreButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier
                .padding(horizontal = Dimensions.space_16, vertical = Dimensions.space_4)
                .fillMaxWidth(),
            style = MaterialTheme.typography.button,
            color = if(enabled) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CoreTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(),
        elevation = null
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier
                .padding(horizontal = Dimensions.space_16, vertical = Dimensions.space_4)
                .fillMaxWidth(),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}