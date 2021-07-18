package com.zennymorh.unitherapy.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User

typealias CommunityItemClickListener = (User) -> Unit

class CommunityAdapter (private var communityList:List<User>, var communityItemClickListener: CommunityItemClickListener):
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    inner class CommunityViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(
            R.layout.community_item, parent,
            false)), View.OnClickListener {
        val userNameTV: TextView = itemView.findViewById(R.id.user_name_tv)
        val userPostTV: TextView = itemView.findViewById(R.id.user_post_tv)


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
        val nameText = holder.userNameTV
        nameText.text = community.name
        val postText = holder.userPostTV
        postText.text = community.post
    }

    fun updateCommunityList(list: List<User>) {
        communityList = list
        notifyDataSetChanged()
    }
}