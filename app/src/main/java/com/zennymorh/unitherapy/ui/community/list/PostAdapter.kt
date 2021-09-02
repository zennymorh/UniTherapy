package com.zennymorh.unitherapy.ui.community.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Posts
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.therapist.TherapistAdapter
import com.zennymorh.unitherapy.ui.therapist.TherapistFragmentDirections


class PostAdapter(options: FirestoreRecyclerOptions<Posts>):
    FirestoreRecyclerAdapter<Posts, PostAdapter.PostViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item, parent, false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int, model: Posts) {
        holder.bind(model)
    }

    inner class PostViewHolder(view:View): RecyclerView.ViewHolder(view){
        fun bind(post: Posts) {

            val nameTV: TextView = itemView.findViewById(R.id.user_name_tv)
            val postTV: TextView = itemView.findViewById(R.id.user_post_tv)
            val favBtn: ToggleButton = itemView.findViewById(R.id.fav_button)

            nameTV.text = post.name
            postTV.text = post.post

            val db = FirebaseFirestore.getInstance()
            val userId = Firebase.auth.currentUser?.uid.toString()

            val postId = post.postId
            val name = post.name
            val postContent = post.post
            val timestamp = post.timestamp

            val saveFav = Posts(
                postId = postId,
                name = name,
                post = postContent,
                timestamp = timestamp
            )

            favBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if (favBtn.isChecked) {
                    db.collection("users").document(userId)
                        .collection("favorites").document(postId.toString())
                        .set(saveFav)
                } else if (!favBtn.isChecked) {
                    db.collection("users").document(userId)
                        .collection("favorites").document(postId.toString())
                        .delete()
                }
            }
        }
    }
}