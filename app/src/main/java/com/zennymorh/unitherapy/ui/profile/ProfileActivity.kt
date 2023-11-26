package com.zennymorh.unitherapy.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zennymorh.unitherapy.MainActivity
import com.zennymorh.unitherapy.databinding.ActivityProfileBinding
import com.zennymorh.unitherapy.model.User


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding


    private val pickImage = 100
    private var imageUri: Uri? = null
    private var storageRef = FirebaseStorage.getInstance().reference
    private val userId = Firebase.auth.currentUser?.uid
    private val imagesRef = storageRef.child("images").child(userId!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getProfile()

        binding.saveBtn.setOnClickListener {
            editProfile()
        }
        binding.uploadImgTV.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            binding.profileImage.setImageURI(imageUri)
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun getProfile() {

        val database = FirebaseFirestore.getInstance()
        val userId = Firebase.auth.currentUser?.uid

        imagesRef.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.profileImage)
        }

        database.collection("users").document(userId!!).get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val document = task.result

                    if (document?.getString("name") != null) {
                        binding.fullNameET.hint = ""
                        binding.fullNameET.text = document.getString("name")?.toEditable()
                    }

                    if (document?.getString("title") != null) {
                        binding.therapistTypeET.hint = ""
                        binding.therapistTypeET.text = document.getString("title")?.toEditable()
                    }

                    if (document?.getString("bio") != null) {
                        binding.fullBioET.hint = ""
                        binding.fullBioET.text = document.getString("bio")?.toEditable()
                    }

                    if (document?.getString("hobbies") != null) {
                        binding.hobbiesET.hint = ""
                        binding.hobbiesET.text = document.getString("hobbies")?.toEditable()
                    }

                    if (document?.getString("workExp") != null) {
                        binding.workExpET.hint = ""
                        binding.workExpET.text = document.getString("workExp")?.toEditable()
                    }

                    if (document?.getBoolean("isTherapist") == true) {
                        binding.therapistCheckBox.isChecked = true
                    }
                }
            }
    }

    private fun editProfile() {
        performValidations()

        val database = FirebaseFirestore.getInstance()
        val email = Firebase.auth.currentUser?.email
        val name = binding.fullNameET.text.toString()
        val title = binding.therapistTypeET.text.toString()
        val bio = binding.fullBioET.text.toString()
        val hobbies = binding.hobbiesET.text.toString()
        val workExp = binding.workExpET.text.toString()
        val isTherapistChecked = binding.therapistCheckBox.isChecked

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
        if (binding.fullNameET.text.toString() == "" || binding.fullNameET.text.toString()
                .isDigitsOnly()
        ) {
            binding.fullNameET.error = "Please Enter valid Name"
            return false
        }
        if (binding.therapistTypeET.text.toString() == "" || binding.therapistTypeET.text.toString()
                .isDigitsOnly()
        ) {
            binding.therapistTypeET.error = "Please Enter valid Therapist type"
            return false
        }
        if (binding.fullBioET.text.toString() == "" || binding.fullBioET.text.toString()
                .isDigitsOnly()
        ) {
            binding.fullBioET.error = "Please enter a bio"
            return false
        }
        return true
    }
}