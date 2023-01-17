package com.doskoch.template.auth.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.di.authFeatureComponentHolder
import com.doskoch.template.auth.screens.signIn.SignInScreen
import com.doskoch.template.auth.screens.signUp.SignUpScreen
import com.doskoch.template.core.components.navigation.CoreNavGraph
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.composable
import dagger.hilt.EntryPoints

@Composable
fun AuthFeatureNavGraph() {
    CoreNavGraph(navigator = EntryPoints.get(authFeatureComponentHolder!!.value, AuthFeatureComponent.ComponentEntryPoint::class.java).navigator()) {
        Node.SignUp.composable(this) {
            SignUpScreen(vm = hiltViewModel(it))
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
