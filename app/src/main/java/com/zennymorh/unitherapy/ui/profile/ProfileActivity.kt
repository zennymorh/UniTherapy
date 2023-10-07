package com.zennymorh.unitherapy.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.zennymorh.unitherapy.MainActivity
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.nav_header_main.*


class ProfileActivity : AppCompatActivity() {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private var storageRef = FirebaseStorage.getInstance().reference
    private val userId = Firebase.auth.currentUser?.uid
    private val imagesRef = storageRef.child("images").child(userId!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getProfile()

        saveBtn.setOnClickListener {
            editProfile()
        }
        uploadImgTV.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            Log.d("WAHALAA", imageUri.toString())
            profileImage.setImageURI(imageUri)
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun getProfile() {

        val database = FirebaseFirestore.getInstance()
        val userId = Firebase.auth.currentUser?.uid

        imagesRef.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(profileImage)
            Log.d("TAG", "I got here")
        }

        database.collection("users").document(userId!!).get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val document = task.result

                    if (document?.getString("name") != null) {
                        fullNameET.hint = ""
                        fullNameET.text = document.getString("name")?.toEditable()
                    }

                    if (document?.getString("title") != null) {
                        therapistTypeET.hint = ""
                        therapistTypeET.text = document.getString("title")?.toEditable()
                    }

                    if (document?.getString("bio") != null) {
                        fullBioET.hint = ""
                        fullBioET.text = document.getString("bio")?.toEditable()
                    }

                    if (document?.getString("hobbies") != null) {
                        hobbiesET.hint = ""
                        hobbiesET.text = document.getString("hobbies")?.toEditable()
                    }

                    if (document?.getString("workExp") != null) {
                        workExpET.hint = ""
                        workExpET.text = document.getString("workExp")?.toEditable()
                    }

                    if (document?.getBoolean("isTherapist") == true) {
                        therapistCheckBox.isChecked = true
                    }
                }
            }
    }

    private fun editProfile() {
        performValidations()

        val database = FirebaseFirestore.getInstance()
        val email = Firebase.auth.currentUser?.email
        val name = fullNameET.text.toString()
        val title = therapistTypeET.text.toString()
        val bio = fullBioET.text.toString()
        val hobbies = hobbiesET.text.toString()
        val workExp = workExpET.text.toString()
        val isTherapistChecked = therapistCheckBox.isChecked

        saveImage()

        val user = User(
            title = title,
            id = userId,
            name = name,
            email = email,
            bio = bio,
            hobbies = hobbies,
            workExp = workExp,
            isTherapist = isTherapistChecked
        )
        database.collection("users")
            .document(userId!!)
            .set(user)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveImage() {
        val imgStream = imageUri?.let { contentResolver.openInputStream(it) }

        val uploadTask = imgStream?.let {
            imagesRef.putStream(it)
        }
        uploadTask?.addOnProgressListener {

        }
            uploadTask?.addOnSuccessListener {
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            uploadTask?.addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(this, "Image upload unsuccessful", Toast.LENGTH_SHORT).show()

            }
    }

    private fun performValidations(): Boolean {
        if (fullNameET.text.toString() == "" || fullNameET.text.toString().isDigitsOnly()) {
            fullNameET.error = "Please Enter valid Name"
            return false
        }
        if (therapistTypeET.text.toString() == "" || therapistTypeET.text.toString().isDigitsOnly()) {
            therapistTypeET.error = "Please Enter valid Therapist type"
            return false
        }
        if (fullBioET.text.toString() == "" || fullBioET.text.toString().isDigitsOnly()) {
            fullBioET.error = "Please enter a bio"
            return false
        }
        return true
    }
}