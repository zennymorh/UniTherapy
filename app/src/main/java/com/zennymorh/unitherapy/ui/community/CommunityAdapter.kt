package com.zennymorh.unitherapy.ui.community

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.community_item.view.*


typealias CommunityItemClickListener = (User) -> Unit

private lateinit var editor: SharedPreferences
private lateinit var auth: FirebaseAuth

class CommunityAdapter (private var communityList: List<User>, var communityItemClickListener: CommunityItemClickListener):
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    inner class CommunityViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(
            R.layout.community_item, parent,
            false)), View.OnClickListener {
        private val userNameTV: TextView = itemView.findViewById(R.id.user_name_tv)
        private val userPostTV: TextView = itemView.findViewById(R.id.user_post_tv)
        private val favButton: ToggleButton = itemView.fav_button

        fun bind(user: User) {

            val context = itemView.context

            val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            val editor = prefs.edit()

            favButton.isChecked = prefs.getBoolean("favButtonChecked", false)

            userNameTV.text = user.name
            userPostTV.text = user.post

            favButton.setOnClickListener {

                auth = Firebase.auth

                val userId = auth.currentUser?.uid

                val database = FirebaseFirestore.getInstance().collection("users").document(userId.toString())

                val fav = User(
                    id = user.id,
                    name = user.name,
                    post = user.post
                )

                if (favButton.isChecked) {

                    database.update(mapOf(
                        "post" to user.post,
                        "isFavorite" to true
                    ))
                        .addOnSuccessListener {
                            Toast.makeText(context, "Added to favorite: $userId", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error adding to favorite: $it. Please try again", Toast.LENGTH_LONG).show()
                        }

                    editor.putBoolean("favButtonChecked", true)
                    editor.apply()

                } else {
                    editor.putBoolean("favButtonChecked", false)
                    editor.apply()

                    database.update(mapOf(
                        "isFavorite" to false,
                        "post" to FieldValue.delete()
                    ))
                        .addOnSuccessListener {
                            Toast.makeText(context, "Deleted favorite: $userId", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error deleting favorite: $userId", Toast.LENGTH_LONG).show()
                        }
                }

//                database = FirebaseDatabase.getInstance().getReference(DATABASE_REFS)
//                val id = database.push().key ?: ""
//
//                val fav = Favorite(
//                    id = id,
//                    name = user.name!!,
//                    post = user.post!!
//                )
//
//                if (favButton.isChecked) {
//                    editor.putBoolean("favButtonChecked", true)
//                    editor.apply()
//
//                    database.child(id).setValue(fav)
//                        .addOnSuccessListener {
//                            Toast.makeText(context, "Added to favorite: $id", Toast.LENGTH_LONG).show()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(context, "Error adding to favorite: $it. Please try again", Toast.LENGTH_LONG).show()
//                        }
//                } else {
//                    editor.putBoolean("favButtonChecked", false)
//                    editor.apply()
//
//                    database.child(id).removeValue()
//                        .addOnSuccessListener {
//                            Toast.makeText(context, "Deleted favorite: $id", Toast.LENGTH_LONG).show()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(context, "Error deleting favorite: $id", Toast.LENGTH_LONG).show()
//                        }
//                }
            }
        }


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val community = communityList[adapterPosition]
            communityItemClickListener.invoke(community)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommunityViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = communityList.size

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val community: User = communityList[position]

        holder.bind(community)
    }

    fun updateCommunityList(list: List<User>) {
        communityList = list
        notifyDataSetChanged()
    }
}