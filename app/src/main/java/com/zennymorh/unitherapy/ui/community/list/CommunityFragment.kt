package com.zennymorh.unitherapy.ui.community.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.zennymorh.unitherapy.databinding.FragmentCommunityBinding
import com.zennymorh.unitherapy.model.Posts


class CommunitiesFragment : Fragment() {
    lateinit var postsAdapter: PostAdapter
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("posts")
        val options =
            FirestoreRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        postsAdapter = PostAdapter(options)

//        binding.list_community.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            adapter = postsAdapter
//            setHasFixedSize(true)
//        }
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