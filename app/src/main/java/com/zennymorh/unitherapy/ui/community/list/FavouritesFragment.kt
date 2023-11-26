package com.zennymorh.unitherapy.ui.community.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.ui.community.FavoritesAdapter

class FavouriteFragment : Fragment() {

    lateinit var favAdapter: FavoritesAdapter

//    private var _binding: FragmentFavouriteBinding? = null
//    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        return inflater.inflate(R.layout.fra, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query =
            firestore.collection("users").document(Firebase.auth.currentUser?.uid.toString())
                .collection("favorites")
        val options =
            FirestoreRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        favAdapter = FavoritesAdapter(options)

//        list_community.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            adapter = favAdapter
//            setHasFixedSize(true)
//        }
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