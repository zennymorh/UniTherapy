package com.zennymorh.unitherapy.ui.community.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.therapist.TherapistAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class CommunitiesFragment : Fragment() {
    lateinit var postsAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("posts")
        val options = FirestoreRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        postsAdapter = PostAdapter(options)

        list_community.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = postsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        postsAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postsAdapter.stopListening()
    }
}