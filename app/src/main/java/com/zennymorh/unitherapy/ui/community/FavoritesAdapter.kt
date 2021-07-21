package com.zennymorh.unitherapy.ui.community

import android.view.LayoutInflater
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
import com.zennymorh.unitherapy.R

private lateinit var database: DatabaseReference

class FavoriteAdapter (private var favList: ArrayList<Favorite>):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(
            R.layout.favorite_item, parent,
            false)) {
        val name: TextView = itemView.fav_name
        val post: TextView = itemView.fav_post
        val delButton: ImageButton = itemView.delete_button

        fun bind(fav: Favorite) {

            name.text = fav.name
            post.text = fav.post

            database = FirebaseDatabase.getInstance().getReference(DATABASE_REFS)

            delButton.setOnClickListener {

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = favList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favList[position]

        holder.name.text = favorite.name
        holder.post.text = favorite.post

//        holder.bind(favorite)

    }

    fun updateCommunityList(list: ArrayList<Favorite>) {
        favList = list
        notifyDataSetChanged()
    }
}

//class FavoritesAdapter(options: FirebaseRecyclerOptions<Favorite>):
//    FirebaseRecyclerAdapter<Favorite, FavoritesAdapter.FavoriteViewHolder>(options) {
//
//    inner class FavoriteViewHolder(inflater: LayoutInflater, parent: ViewGroup):
//        RecyclerView.ViewHolder(inflater.inflate(
//            com.zennymorh.unitherapy.R.layout.favorite_item, parent,
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
//    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int, model: Favorite) {
//
//        holder.name.text = model.name
//        holder.post.text = model.post
//
//        database = FirebaseDatabase.getInstance().getReference(DATABASE_REFS)
//
//        holder.delButton.setOnClickListener {
//
//        }
//    }
//}