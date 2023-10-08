package com.zennymorh.unitherapy.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.MainActivity
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.fragment_forgot_password.indeterminateBar
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            val name = userNameET.text.toString()
            val email = emailAddressInput.text.toString()
            val password = passwordInput.text.toString()

            if (!isValidEntry(name, email, password)) {
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        handleSignUpSuccess(name, email)
                        indeterminateBar.visibility = View.GONE
                    } else {
                        handleSignUpFailure(task)
                        indeterminateBar.visibility = View.GONE
                    }
                }
        }

        signInTV.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun handleSignUpFailure(task: Task<AuthResult>) {
        // If sign in fails, display a message to the user.
        Log.w(TAG, "createUserWithEmail:failure", task.exception)
        Toast.makeText(
            context,
            "Authentication failed." + task.exception?.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun handleSignUpSuccess(name: String, email: String) {
        Log.d(TAG, "createUserWithEmail:success")

        val db = Firebase.firestore
        val userId = auth.currentUser?.uid
        val isTherapistChecked = therapistCheckBox.isChecked

        val user = User(
            id = userId,
            name = name,
            email = email,
            isTherapist = isTherapistChecked
        )
        db.collection("users")
            .document(userId!!)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                val intent = Intent(this.activity, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
                Toast.makeText(context, "Adding to DB failed.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun isValidEntry(
        name: String,
        email: String,
        password: String
    ): Boolean {

        return when {
            name.isEmpty() -> {
                Toast.makeText(context, "Enter a valid name", Toast.LENGTH_SHORT).show()
                false
            }
            email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "Enter a valid name", Toast.LENGTH_SHORT).show()
                false
            }
            password.isEmpty() -> {
                Toast.makeText(context, "Enter a valid password", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }
}
