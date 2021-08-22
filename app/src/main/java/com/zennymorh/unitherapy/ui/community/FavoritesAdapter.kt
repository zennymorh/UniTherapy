package com.zennymorh.unitherapy.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zennymorh.unitherapy.DATABASE_REFS
import com.zennymorh.unitherapy.model.Favorite
import kotlinx.android.synthetic.main.favorite_item.view.*

import com.firebase.ui.database.FirebaseRecyclerOptions

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User

class FavoritesAdapter(options: FirestoreRecyclerOptions<User>): FirestoreRecyclerAdapter<User, FavoritesViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)

        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int, model: User) {
        holder.bind(model)
    }

}

class FavoritesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(fav: User) {
        val name: TextView = view.fav_name
        val post: TextView = view.fav_post
        val delButton: ImageButton = view.delete_button

        name.text = fav.name
        post.text = fav.post
    }
}
//class FavoriteAdapter (private var favList: ArrayList<User>):
//    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
//
//    inner class FavoriteViewHolder(inflater: LayoutInflater, parent: ViewGroup):
//        RecyclerView.ViewHolder(inflater.inflate(
//            R.layout.favorite_item, parent,
//            false)) {
//        val name: TextView = itemView.fav_name
//        val post: TextView = itemView.fav_post
//        val delButton: ImageButton = itemView.delete_button
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return FavoriteViewHolder(inflater, parent)
//    }
//
//    override fun getItemCount(): Int = favList.size
//
//    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
//        val favorite = favList[position]
//
//        holder.name.text = favorite.name
//        holder.post.text = favorite.post
//
//    }
//
//    fun updateCommunityList(list: ArrayList<User>) {
//        favList = list
//        notifyDataSetChanged()
//    }
//}