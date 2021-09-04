package com.zennymorh.unitherapy.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.model.Rooms
import kotlinx.android.synthetic.main.fragment_rooms_list.*

class RoomsListFragment : Fragment() {

    lateinit var roomsListAdapter: RoomsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .collection("rooms")
        val options = FirestoreRecyclerOptions.Builder<Rooms>().setQuery(query, Rooms::class.java).build()

        roomsListAdapter = RoomsListAdapter(options)

        rooms_list.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = roomsListAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        roomsListAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        roomsListAdapter.stopListening()
    }
}