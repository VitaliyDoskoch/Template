package com.doskoch.template.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.doskoch.template.core.R
import com.doskoch.template.core.theme.Dimensions

@Composable
fun CoreTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    iconPainter: Painter? = null,
    onNavigationClick: () -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    elevation: Dp = 0.dp
) {
    TopAppBar(
        title = {
            title?.let {

            }
        },
        modifier = modifier,
        navigationIcon = {
            iconPainter?.let {
                Icon(
                    painter = it,
                    contentDescription = stringResource(R.string.desc_navigate_back),
                    modifier = Modifier
                        .padding(start = Dimensions.space_8)
                        .clickable(onClick = onNavigationClick)
                )
            }
        },
        backgroundColor = backgroundColor,
        elevation = elevation
    )
}