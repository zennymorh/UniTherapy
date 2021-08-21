package com.zennymorh.unitherapy.ui.community.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.zennymorh.unitherapy.DATABASE_REFS
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Favorite
import com.zennymorh.unitherapy.ui.community.FavoriteAdapter

class FavouriteFragment : Fragment() {

    private lateinit var favFragment : View

    private lateinit var database: DatabaseReference
    private lateinit var favRecycler: RecyclerView
    private lateinit var favList: ArrayList<Favorite>
    var favAdapter = FavoriteAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favFragment = inflater.inflate(R.layout.fragment_list, container, false)

        favRecycler = favFragment.findViewById(R.id.list_community)
        favRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = favAdapter
            setHasFixedSize(true)
        }

        favList = arrayListOf<Favorite>()
        getFavorites()

        return favFragment
    }



    private fun getFavorites() {

        database = FirebaseDatabase.getInstance().getReference(DATABASE_REFS)

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val fav = i.getValue(Favorite::class.java)

                        favAdapter.updateCommunityList(favList)
                        favList.add(fav!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}