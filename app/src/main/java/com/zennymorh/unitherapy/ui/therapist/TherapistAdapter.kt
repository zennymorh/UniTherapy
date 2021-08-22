package com.zennymorh.unitherapy.ui.therapist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User

class TherapistAdapter(options: FirestoreRecyclerOptions<User>):
    FirestoreRecyclerAdapter<User, TherapistAdapter.TherapistViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TherapistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.therapist_item, parent, false)

        return TherapistViewHolder(view)
    }

    override fun onBindViewHolder(holder: TherapistViewHolder, position: Int, model: User) {
        holder.bind(model)

        holder.itemView.setOnClickListener { v ->
            val action = TherapistFragmentDirections.actionNavigationTherapistToTherapistDetailFragment(model)
            Navigation.findNavController(v).navigate(action)
        }
    }

    inner class TherapistViewHolder(private val view:View): RecyclerView.ViewHolder(view){
        fun bind(user: User) {

            val nameTV: TextView = itemView.findViewById(R.id.therapistName)
            val titleTV: TextView = itemView.findViewById(R.id.therapistTitle)
            val bioTV: TextView = itemView.findViewById(R.id.therapistBio)
            val bgImg: ImageView = itemView.findViewById(R.id.bgImg)

            nameTV.text = user.name
            titleTV.text = user.title
            bioTV.text = user.bio

        }
    }

}