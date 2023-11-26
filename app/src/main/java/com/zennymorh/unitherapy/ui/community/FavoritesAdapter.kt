package com.zennymorh.unitherapy.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Posts

class FavoritesAdapter(options: FirestoreRecyclerOptions<Posts>):
    FirestoreRecyclerAdapter<Posts, FavoritesAdapter.FavoritesViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)

        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int, model: Posts) {
        holder.bind(model)
    }

    inner class FavoritesViewHolder(view:View): RecyclerView.ViewHolder(view){
        fun bind(post: Posts) {

            val nameTV: TextView = itemView.findViewById(R.id.fav_name)
            val postTV: TextView = itemView.findViewById(R.id.fav_post)
            val delBtn: ImageButton = itemView.findViewById(R.id.delete_button)

            nameTV.text = post.name
            postTV.text = post.post

            val db = FirebaseFirestore.getInstance()
            val userId = Firebase.auth.currentUser?.uid.toString()

            val postId = post.postId

            delBtn.setOnClickListener {
                db.collection("users").document(userId)
                    .collection("favorites").document(postId.toString()).delete()
            }
        }
    }

}