package com.zennymorh.unitherapy.ui.therapist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.zennymorh.unitherapy.databinding.FragmentTherapistBinding
import com.zennymorh.unitherapy.model.User

class TherapistFragment : Fragment() {

    private lateinit var therapyViewModel: TherapyViewModel
    lateinit var therapistAdapter: TherapistAdapter

    private var _binding: FragmentTherapistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        therapyViewModel = ViewModelProviders.of(this).get(TherapyViewModel::class.java)

        _binding = FragmentTherapistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("users").whereEqualTo("isTherapist", true)
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()

        therapistAdapter = TherapistAdapter(options)

        binding.rvTherapist.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = therapistAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        therapistAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        therapistAdapter.stopListening()
    }
}
