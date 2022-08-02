package com.doskoch.template

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontStyle
import androidx.fragment.app.FragmentActivity
import com.doskoch.template.core.theme.BasicTheme
import com.doskoch.template.core.theme.extraColors

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(ComposeView(this).apply {
            setContent {
                BasicTheme {
                    Column {
                        Text(text = "lorem", color = MaterialTheme.extraColors.tertiary)
                        Text(text = "lorem", style = MaterialTheme.typography.h2)
                        Text(text = "lorem", style = MaterialTheme.typography.h3)
                        Text(text = "lorem", style = MaterialTheme.typography.h4)
                        Text(text = "lorem", style = MaterialTheme.typography.h5)
                        Text(text = "lorem", style = MaterialTheme.typography.h6)

                        Text(text = "lorem", style = MaterialTheme.typography.subtitle1)
                        Text(text = "lorem", style = MaterialTheme.typography.subtitle2)

                        Text(text = "lorem", style = MaterialTheme.typography.body1)
                        Text(text = "lorem", style = MaterialTheme.typography.body2.copy(fontStyle = FontStyle.Italic))
                    }
                }
            }
        })
    }
}
