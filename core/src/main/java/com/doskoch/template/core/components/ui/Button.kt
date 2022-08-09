package com.doskoch.template.core.components.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.doskoch.template.core.components.theme.Dimensions

@Composable
fun CoreButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimensions.space_8, vertical = Dimensions.space_4)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text.uppercase(),
                modifier = Modifier
                    .padding(start = Dimensions.space_32)
                    .weight(1f),
                style = MaterialTheme.typography.button,
                color = if(enabled) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if(isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(start = Dimensions.space_8)
                        .size(Dimensions.icon_24),
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = 3.dp
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(start = Dimensions.space_8)
                        .size(Dimensions.icon_24)
                )
            }
        }
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
                .weight(1f),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}