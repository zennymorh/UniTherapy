package com.zennymorh.unitherapy.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private val storage = Firebase.storage
    var storageRef = storage.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var isEditing: Boolean = true

        saveBtn.setOnClickListener {
            isEditing = false
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

    private fun editProfile() {
        performValidations()

        val database = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid
        val email = Firebase.auth.currentUser?.email
        val name = fullNameET.text.toString()
        val title = therapistTypeET.text.toString()
        val bio = fullBioET.text.toString()
        val hobbies = hobbiesET.text.toString()
        val workExp = workExpET.text.toString()

        saveImage()

        val user = User(
            title = title,
            id = userId,
            name = name,
            email = email,
            bio = bio,
            hobbies = hobbies,
            workExp = workExp
        )
        database.collection("users")
            .document(userId!!)
            .set(user)
    }

    private fun saveImage() {
        val imgStream = imageUri?.let { contentResolver.openInputStream(it) }

        val file = Uri.fromFile(File(imageUri.toString()))
        val imagesRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = imgStream?.let { imagesRef.putStream(it) }
        uploadTask?.addOnProgressListener {

        }
            uploadTask?.addOnSuccessListener {
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            uploadTask?.addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()

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