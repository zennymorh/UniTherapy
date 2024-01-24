package com.zennymorh.unitherapy

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zennymorh.unitherapy.auth.SignInScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profile") {
        composable("signInScreen") {
            SignInScreen(onSignInWithEmail = {}, onSignInWithGoogle = {})
        }
        composable("signUpScreen") { SignUpScreen(onSignUpWithEmail = {}) }
    }
}

