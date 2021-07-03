package com.zennymorh.unitherapy.ui.therapist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.fragment_therapist_detail.*

class TherapistDetailFragment : Fragment() {

    private val args: TherapistDetailFragmentArgs by navArgs()

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
            findNavController().navigate(R.id.action_therapistDetailFragment_to_navigation_chat)
        }
    }

    private fun bind(therapist: User) {
        user_name.text = therapist.name
        user_therapy_type.text = therapist.title
        user_desc_TV.text = therapist.fullDesc
        work_exp.text = therapist.workExp
        hobbies.text = therapist.hobbies
    }

}