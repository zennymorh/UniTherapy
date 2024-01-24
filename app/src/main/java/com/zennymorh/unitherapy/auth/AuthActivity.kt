package com.zennymorh.unitherapy.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme

class AuthActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                SignInScreen(onSignInWithGoogle = {},
                    onSignInWithEmail = {})
            }
        }
//
//        val navController = findNavController(R.id.myNavHostFragment)
//
//        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
//            .build()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//    }
}
