package com.zennymorh.unitherapy.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.zennymorh.unitherapy.databinding.FragmentChatBinding
import com.zennymorh.unitherapy.model.Message

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setViewListeners()

    }

    private fun setViewListeners() {
        binding.buttonSend.setOnClickListener {
            sendChatMessage()
        }
    }

    private fun initList() {
        if (user == null)
            return

        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ChatAdapter(chatMessages, user.uid)
        binding.recyclerViewMessages.adapter = adapter
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
                binding.recyclerViewMessages.adapter?.notifyDataSetChanged()
            }
    }

    private fun sendChatMessage() {
        val message = binding.editTextMessage.text.toString()
        binding.editTextMessage.setText("")

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
