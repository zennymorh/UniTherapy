package com.zennymorh.unitherapy.ui.therapist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.chat.ChatFragment
import kotlinx.android.synthetic.main.fragment_therapist_detail.*

class TherapistDetailFragment : Fragment() {

    private val args: TherapistDetailFragmentArgs by navArgs()
    private var storageRef = FirebaseStorage.getInstance().reference
    private val imagesRef = storageRef.child("images")
    private val auth = Firebase.auth.currentUser?.uid
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_therapist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val therapist = args.selectedTherapist
        bind(therapist)

        chatFAB.setOnClickListener {
            val roomId: String = therapist.id.toString() + auth
            Log.d("FUCKKKK", roomId)
            if (roomId.isEmpty()) {
                return@setOnClickListener
            }

            auth.let {
                if (it != null) {
                    firestore.collection("users").document(it).collection("rooms")
                        .document(roomId).set(mapOf(
                            Pair("id", roomId)
                        ))
                }
            }

            val bundle = bundleOf("roomId" to roomId)
            findNavController().navigate(R.id.action_therapistDetailFragment_to_navigation_chat, bundle)
        }
    }

    private fun bind(therapist: User) {
        user_name.text = therapist.name
        user_therapy_type.text = therapist.title
        user_desc_TV.text = therapist.fullDesc
        work_exp.text = therapist.workExp
        hobbies.text = therapist.hobbies
        therapist.backgroundImg?.let { profileImage.setImageResource(it) }

        imagesRef.child(therapist.id.toString()).downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(profileImage)
        }

    }

}