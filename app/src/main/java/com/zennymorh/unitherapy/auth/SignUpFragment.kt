package com.zennymorh.unitherapy.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.MainActivity
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            val email = emailAddressET.text.toString()
            val password = passwordET.text.toString()
            val name = userNameET.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = auth.currentUser

                        val db = Firebase.firestore
                        val userId = auth.currentUser.uid
                        val user = User(
                            id = userId,
                            name = name,
                            email = email,
                            isTherapist = false
                        )
                        db.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully written!")
                                val intent = Intent(this.activity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener {
                                    e -> Log.w(TAG, "Error writing document", e)
                                Toast.makeText(context, "Adding to DB failed.",
                                    Toast.LENGTH_SHORT).show()
                            }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed." + task.exception?.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}