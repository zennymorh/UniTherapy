package com.zennymorh.unitherapy.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.fragment_post.*
import java.sql.Timestamp
import java.util.ArrayList

class PostFragment : Fragment() {

    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid

        edit_post.background = null

        close_button.setOnClickListener {
            activity?.onBackPressed()
        }

        post_button.setOnClickListener {

            if (edit_post.text.isEmpty()) {
                Toast.makeText(requireContext(), "Can't make empty post", Toast.LENGTH_LONG).show()
            } else {
                if (userId != null) {
                    database.collection("users").document(userId).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val document = task.result

                                val name = document?.getString("name").toString()
                                val postContent = edit_post.text.toString()

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
}