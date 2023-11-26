package com.zennymorh.unitherapy.ui.therapist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.databinding.FragmentTherapistDetailBinding
import com.zennymorh.unitherapy.model.User

class TherapistDetailFragment : Fragment() {

    private val args: TherapistDetailFragmentArgs by navArgs()
    private var storageRef = FirebaseStorage.getInstance().reference
    private val imagesRef = storageRef.child("images")
    private val auth = Firebase.auth.currentUser?.uid
    val firestore = FirebaseFirestore.getInstance()
    private var _binding: FragmentTherapistDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTherapistDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val therapist = args.selectedTherapist

        bind(therapist)

        val navHostFragment = fragmentManager?.findFragmentById(R.id.therapistDetailFragment)
        val navController = navHostFragment?.findNavController()

        val roomId = therapist.id+auth

        binding.chatFAB.setOnClickListener { chat_fab ->
            Log.d("FUCKKKK", roomId)

            val bundle = bundleOf(
                "roomId" to roomId,
                "receiverId" to therapist.id,
                "therapistName" to therapist.name
            )

            if (roomId.isEmpty()) {
                return@setOnClickListener
            } else {

                firestore.collection("users")
                    .document(auth.toString())
                    .collection("rooms")
                    .document(roomId)
                    .get()
                    .addOnSuccessListener { document1 ->
                        if (document1.exists()) {
                            navController?.navigate(
                                TherapistDetailFragmentDirections.actionTherapistDetailFragmentToNavigationChat(roomId)
                            )
                        } else {
                            firestore.collection("users")
                                .document(auth.toString())
                                .collection("rooms")
                                .document(auth+therapist.id)
                                .get()
                                .addOnSuccessListener { document2 ->
                                    if (document2.exists()) {
                                        navController?.navigate(
                                            TherapistDetailFragmentDirections.actionTherapistDetailFragmentToNavigationChat(auth+therapist.id)
                                        )
                                    } else {
                                        firestore.collection("users")
                                            .document(auth.toString())
                                            .collection("rooms")
                                            .document(roomId)
                                            .set(
                                                mapOf(
                                                    Pair("roomId", roomId),
                                                    Pair("therapistName", therapist.name)
                                                )
                                            )

                                        navController?.navigate(
                                            TherapistDetailFragmentDirections.actionTherapistDetailFragmentToNavigationChat(roomId)
                                        )
                                    }
                                }
                        }
                    }


            }

            findNavController().navigate(
                R.id.action_therapistDetailFragment_to_navigation_chat,
                bundle
            )
        }
    }

    private fun bind(therapist: User) {
        binding.userName.text = therapist.name
        binding.userTherapyType.text = therapist.title
        binding.userDescTV.text = therapist.bio
        binding.workExp.text = therapist.workExp
        binding.hobbies.text = therapist.hobbies
        therapist.backgroundImg?.let { binding.profileImage.setImageResource(it) }

        imagesRef.child(therapist.id.toString()).downloadUrl.addOnSuccessListener {
            this.activity?.let { it1 -> Glide.with(it1).load(it).into(binding.profileImage) }
        }

    }

}