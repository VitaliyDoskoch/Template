package com.doskoch.template.core.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.doskoch.template.core.components.theme.Dimensions

@Composable
fun CoreTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    maxLength: Int? = null,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val showHint = !hint.isNullOrBlank() && value.isEmpty()

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (maxLength != null) {
                onValueChange(it.take(maxLength))
            } else {
                onValueChange(it)
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(if (errorMessage.isNullOrBlank()) MaterialTheme.colors.primary else MaterialTheme.colors.error),
        decorationBox = { InnerTextField ->
            Column {
                Box {
                    InnerTextField()

                    if (showHint) {
                        Text(
                            text = hint.orEmpty(),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = Dimensions.space_10)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            color = when {
                                !errorMessage.isNullOrBlank() -> MaterialTheme.colors.error
                                else -> MaterialTheme.colors.primary
                            },
                            shape = MaterialTheme.shapes.small
                        )
                )

                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier
                            .padding(top = Dimensions.space_4),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.error,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )
}