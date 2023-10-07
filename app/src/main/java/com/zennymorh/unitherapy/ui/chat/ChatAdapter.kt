package com.zennymorh.unitherapy.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.Message
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore


const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class ChatAdapter(private val chatMessages: List<Message>, private val uid: String):
    RecyclerView.Adapter<ViewHolder>() {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View?
        var viewHolder: ViewHolder? = null

        if (viewType == VIEW_TYPE_MY_MESSAGE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false);
            viewHolder = SentViewHolder(view);
        } else if (viewType == VIEW_TYPE_OTHER_MESSAGE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_received, parent, false);
            viewHolder = ReceivedViewHolder(view);
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatMessage = chatMessages[position]

        when (holder.itemViewType) {
            VIEW_TYPE_MY_MESSAGE -> (holder as SentViewHolder).bind(chatMessage)
            VIEW_TYPE_OTHER_MESSAGE -> (holder as ReceivedViewHolder).bind(chatMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = chatMessages[position]
        return if(user?.uid == message.receiver) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }


    class SentViewHolder(itemView: View) : ViewHolder(itemView) {

        private var chatTextSent: TextView? = null

        init {
            chatTextSent = itemView.findViewById(R.id.textview_chat_sent)
        }

        fun bind(message: Message) {
            chatTextSent?.text = message.messageText
        }

    }

    class ReceivedViewHolder(itemView: View) : ViewHolder(itemView) {

        private var chatTextReceived: TextView? = null

        init {
            chatTextReceived = itemView.findViewById(R.id.textview_chat_received)
        }

        fun bind(message: Message) {
            chatTextReceived?.text = message.messageText
        }

    }

}