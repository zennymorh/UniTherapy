package com.zennymorh.unitherapy.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.databinding.FragmentPostBinding
import com.zennymorh.unitherapy.model.Posts

class PostFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid

        binding.editPost.background = null

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.postButton.setOnClickListener {

            if (binding.editPost.text.isEmpty()) {
                Toast.makeText(requireContext(), "Can't make empty post", Toast.LENGTH_LONG).show()
            } else if (userId != null) {
                database.collection("users").document(userId).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val document = task.result

                            val name = document?.getString("name").toString()
                            val postContent = binding.editPost.text.toString()

                            val timestamp = System.currentTimeMillis()


                                val postId = "$userId:$timestamp"

                                val post = Posts(
                                    postId = postId,
                                    name = name,
                                    post = postContent,
                                    timestamp = timestamp
                                )

                                database.collection("posts")
                                    .document(postId)
                                    .set(post)

                                activity?.onBackPressed()
                            }
                        }

            }
        }

    }
}