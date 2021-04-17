package com.zennymorh.unitherapy.ui.therapist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zennymorh.unitherapy.R

class TherapistFragment : Fragment() {

    private lateinit var therapyViewModel: TherapyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        therapyViewModel =
            ViewModelProviders.of(this).get(TherapyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_therapist, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        therapyViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                textView.text = it
            }
        )
        return root
    }
}
