package com.zennymorh.unitherapy.auth

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.fragment_sign_in.*

class ForgotPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    companion object {
        private var auth: FirebaseAuth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proceedBtn.setOnClickListener {
            val email = emailET.text.toString()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(
                    context,
                    "Enter a valid email address",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            indeterminateBar.transitionName = "Loading"

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Reset password email has been successfully sent to the email",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(
                            R.id.action_forgotPasswordFragment_to_signInFragment
                        )
                        indeterminateBar.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            context,
                            "Something went wrong. ",
                            Toast.LENGTH_SHORT
                        ).show()
                        indeterminateBar.visibility = View.GONE
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        "I failed woefully",
                        Toast.LENGTH_SHORT
                    ).show()
                    indeterminateBar.visibility = View.GONE
                }
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "I am successful in jesus' name",
                        Toast.LENGTH_SHORT
                    ).show()
                    indeterminateBar.visibility = View.GONE
                }
        }
    }
}
