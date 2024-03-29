package com.zennymorh.unitherapy.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.auth.AuthActivity
import com.zennymorh.unitherapy.model.Message
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    val chatMessages = ArrayList<Message>()
    var chatRegistration: ListenerRegistration? = null
    private lateinit var roomId: String
    private lateinit var receiverId: String
    private lateinit var therapistName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setViewListeners()

    }

    private fun setViewListeners() {
        button_send.setOnClickListener {
            sendChatMessage()
        }
    }

    private fun initList() {
        if (user == null)
            return

        recycler_view_messages.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ChatAdapter(chatMessages, user.uid)
        recycler_view_messages.adapter = adapter
        listenForChatMessages()
    }

    private fun listenForChatMessages() {
        roomId = arguments?.getString("roomId").toString()

        receiverId = arguments?.getString("receiverId").toString()

        therapistName = arguments?.getString("therapistName").toString()

        firestore.collection("users")
            .document(user?.uid.toString())
            .collection("rooms")
            .document(roomId)
            .set(
                mapOf(
                    Pair("roomId", roomId),
                    Pair("therapistName", therapistName)
                )
            )

        chatRegistration = firestore
            .collection("users")
            .document(user?.uid.toString())
            .collection("rooms")
            .document(roomId)
            .collection("messages")
            .addSnapshotListener { messageSnapshot, _ ->
                if (messageSnapshot == null || messageSnapshot.isEmpty)
                    return@addSnapshotListener

                chatMessages.clear()

                for (messageDocument in messageSnapshot.documents) {
                    chatMessages.add(
                        Message(
                            (messageDocument["text"] as? String).toString(),
                            messageDocument["user"] as? String,
                            messageDocument.getTimestamp("timestamp")?.toDate()
                        )
                    )
                }

                chatMessages.sortBy { it.timestamp }
                recycler_view_messages.adapter?.notifyDataSetChanged()
            }
    }

    private fun sendChatMessage() {
        val message = editText_message.text.toString()
        editText_message.setText("")

        firestore.collection("users")
            .document(user?.uid.toString())
            .collection("rooms")
            .document(roomId)
            .collection("messages")
            .add(
                mapOf(
                    Pair("text", message),
                    Pair("sender", user?.uid),
                    Pair("timestamp", Timestamp.now())
                )
            )
    }

    override fun onDestroy() {
        chatRegistration?.remove()
        super.onDestroy()
    }

}
