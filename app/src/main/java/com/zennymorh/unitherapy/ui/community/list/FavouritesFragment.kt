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
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.zennymorh.unitherapy.DATABASE_REFS
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Favorite
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.community.FavoritesAdapter

class FavouriteFragment : Fragment() {

    private lateinit var favFragment : View

    private lateinit var database: FirebaseFirestore
    private lateinit var favRecycler: RecyclerView
    private lateinit var favList: ArrayList<User>
    private lateinit var favAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favFragment = inflater.inflate(R.layout.fragment_list, container, false)

        val database = FirebaseFirestore.getInstance()
        val query = database.collection("users").orderBy("name")
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()

        favRecycler = favFragment.findViewById(R.id.list_community)

        favAdapter = FavoritesAdapter(options)
        favRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
            setHasFixedSize(true)
        }

        favList = arrayListOf()

        return favFragment
    }

    override fun onStart() {
        super.onStart()

        favAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()

        favAdapter.stopListening()
    }



//    private fun getFavorites() {
//
//        val database = FirebaseFirestore.getInstance()
//        val query = database.collection("users").orderBy("name")
//        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()
//
////        database.collection("users")
//
////        database = FirebaseDatabase.getInstance().getReference(DATABASE_REFS)
//
////        database.addValueEventListener(object : ValueEventListener{
////            override fun onDataChange(snapshot: DataSnapshot) {
////                if (snapshot.exists()) {
////                    for (i in snapshot.children) {
////                        val fav = i.getValue(Favorite::class.java)
////
////                        favAdapter.updateCommunityList(favList)
////                        favList.add(fav!!)
////                    }
////                }
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                TODO("Not yet implemented")
////            }
////
////        })
//    }
}