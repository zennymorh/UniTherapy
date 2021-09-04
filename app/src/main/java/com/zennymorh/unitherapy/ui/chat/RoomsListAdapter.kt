package com.zennymorh.unitherapy.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Rooms

class RoomsListAdapter(options: FirestoreRecyclerOptions<Rooms>):
    FirestoreRecyclerAdapter<Rooms, RoomsListAdapter.RoomsListViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsListAdapter.RoomsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rooms_item, parent, false)

        return RoomsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomsListAdapter.RoomsListViewHolder, position: Int, model: Rooms) {
        holder.bind(model)

        holder.itemView.setOnClickListener {
            val action = RoomsListFragmentDirections.actionRoomsListFragmentToNavigationChat(model.roomId)
            Navigation.findNavController(it).navigate(action)
        }
    }

    inner class RoomsListViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(rooms: Rooms) {

            val name: TextView = itemView.findViewById(R.id.therapist_name)

            name.text = rooms.therapistName
        }
    }
}