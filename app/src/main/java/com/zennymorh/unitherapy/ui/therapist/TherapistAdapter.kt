package com.zennymorh.unitherapy.ui.therapist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User

typealias TherapistItemClickListener = (User) -> Unit

class TherapistAdapter(private var therapistList:List<User>, var therapistItemClickListener: TherapistItemClickListener):
    RecyclerView.Adapter<TherapistAdapter.TherapistViewHolder>() {

    inner class TherapistViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(
            R.layout.therapist_item, parent,
            false)), View.OnClickListener {
        val nameTV: TextView = itemView.findViewById<TextView>(R.id.therapistName)
        val titleTV: TextView = itemView.findViewById<TextView>(R.id.therapistTitle)
        val bioTV: TextView = itemView.findViewById<TextView>(R.id.therapistBio)
        val bgImg: ImageView = itemView.findViewById<ImageView>(R.id.bgImg)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val therapist = therapistList[adapterPosition]
            therapistItemClickListener.invoke(therapist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TherapistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TherapistViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = therapistList.size

    override fun onBindViewHolder(holder: TherapistViewHolder, position: Int) {
        val therapist: User = therapistList[position]
        val nameText = holder.nameTV
        nameText.text = therapist.name
        val titleText = holder.titleTV
        titleText.text = therapist.title
        //Create a title kinni in the user data class and the mock kinni in the fragment
        val bioText = holder.bioTV
        bioText.text = therapist.bio
        val imgBg = holder.bgImg
        therapist.backgroundImg?.let { imgBg.setImageResource(it) }
    }

    fun updateTherapistList(list: List<User>) {
        therapistList = list
        notifyDataSetChanged()
    }
}