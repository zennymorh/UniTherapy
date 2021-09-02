package com.zennymorh.unitherapy.ui.community.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.DATABASE_REFS
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Favorite
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.community.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_list.*

class FavouriteFragment : Fragment() {

    lateinit var favAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .collection("favorites")
        val options = FirestoreRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        favAdapter = FavoritesAdapter(options)

        list_community.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        favAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        favAdapter.stopListening()
    }

}