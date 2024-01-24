package com.zennymorh.unitherapy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zennymorh.unitherapy.auth.AuthActivity
import com.zennymorh.unitherapy.databinding.ActivityMainBinding
import com.zennymorh.unitherapy.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    lateinit var bottomNav: BottomNavigationView
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    val firestore = FirebaseFirestore.getInstance()
    private var storageRef = FirebaseStorage.getInstance().reference
    private val userId = Firebase.auth.currentUser?.uid
    private val imagesRef = userId?.let { storageRef.child("images").child(it) }

    //    @Composable
//    fun MyFirstScreen() {
//        Text(text = "Somethihgn")
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            MaterialTheme {
//                MyFirstScreen()
//            }
//        }

        auth = Firebase.auth

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        setSupportActionBar(binding.mainActivityToolbar)
//        bottomNav = binding.bottom_nav_view


        navController = findNavController(R.id.nav_host_fragment)
        setupBottomNavigation()

//        drawerLayout = binding.drawer_Layout
        // For Navigation UP
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        //NavigationUI.setupWithNavController(navigation_view,navController)
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.navView.setNavigationItemSelectedListener(this)
        val navigationView : NavigationView  = findViewById(R.id.navView)
        val headerView : View = navigationView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.nameOfUser)

        firestore.collection("users").document(auth.currentUser?.uid.toString()).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document?.getString("name") != null) {
                        navUsername.text = document.getString("name")
                    }
                }
            }

//        imagesRef?.downloadUrl?.addOnSuccessListener {
//            Glide.with(this).load(it).into(binding.imageOfUser)
//        }
    }


    override fun onSupportNavigateUp(): Boolean {
        //return navController.navigateUp()
        return NavigationUI.navigateUp(navController, appBarConfiguration)

    }

    private fun setupBottomNavigation() {
        bottomNav.setupWithNavController(navController)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(
                    this,
                    "Profile clicked",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.signOut -> {
                Toast.makeText(
                    this,
                    "Successfully signed out",
                    Toast.LENGTH_SHORT
                ).show()
                auth.signOut()
                auth.addAuthStateListener {
                    if (auth.currentUser == null){
                        val intent = Intent(this, AuthActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }
                true
            }
            else -> false
        }
    }
}
