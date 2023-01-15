package com.doskoch.template.auth.navigation

import androidx.compose.runtime.Composable
import com.doskoch.template.auth.di.Module
import com.doskoch.template.auth.screens.signIn.SignInScreen
import com.doskoch.template.auth.screens.signUp.SignUpScreen
import com.doskoch.template.core.components.navigation.CoreNavGraph
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable

@Composable
fun AuthFeatureNavGraph() {
    CoreNavGraph(navigator = Module.Provider::navigator) {
        Node.SignUp.composable(this) {
            SignUpScreen()
        }
        Node.SignIn.composable(this) {
            SignInScreen()
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object SignUp : Node("signUp")
    object SignIn : Node("signIn")
}
