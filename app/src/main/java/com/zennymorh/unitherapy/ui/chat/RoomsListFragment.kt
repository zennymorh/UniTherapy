package com.zennymorh.unitherapy.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.databinding.FragmentRoomsListBinding
import com.zennymorh.unitherapy.model.Rooms

class RoomsListFragment : Fragment() {

    lateinit var roomsListAdapter: RoomsListAdapter
    private var _binding: FragmentRoomsListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRoomsListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .collection("rooms")
        val options = FirestoreRecyclerOptions.Builder<Rooms>().setQuery(query, Rooms::class.java).build()

        roomsListAdapter = RoomsListAdapter(options)

        binding.roomsList.apply {
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