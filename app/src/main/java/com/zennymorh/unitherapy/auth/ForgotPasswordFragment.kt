package com.zennymorh.unitherapy.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {
        private var auth: FirebaseAuth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.proceedBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(
                    context,
                    "Enter a valid email address",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            binding.indeterminateBar.transitionName = "Loading"

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Reset password email has been successfully sent to the email",
                            Toast.LENGTH_SHORT
                        ).show()
//                        findNavController().navigate(
//                            R.id.action_forgotPasswordFragment_to_signInFragment
//                        )
                        binding.indeterminateBar.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            context, "Something went wrong. ", Toast.LENGTH_SHORT).show()
                        binding.indeterminateBar.visibility = View.GONE
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    binding.indeterminateBar.visibility = View.GONE
                }
                .addOnSuccessListener {
                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                    binding.indeterminateBar.visibility = View.GONE
                }
        }
    }
}
