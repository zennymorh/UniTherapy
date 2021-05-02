package com.zennymorh.unitherapy.ui.therapist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import kotlinx.android.synthetic.main.fragment_therapist.*

class TherapistFragment : Fragment() {

    private lateinit var therapyViewModel: TherapyViewModel

    private var therapistArray = arrayListOf(
        User(
            id = "randomId",
            name = "Zainab Jimoh",
            bio = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships.",
            backgroundImg = R.drawable.bg_img,
            title = "Sex Therapist"
        ),
        User(
            id = "randomId",
            name = "Ezichi Amarachi",
            bio = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships.",
            backgroundImg = R.drawable.bg_img_2,
            title = "Family Therapist"
        ),
        User(
            id = "randomId",
            name = "Vivian Fatima",
            bio = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships.",
            backgroundImg = R.drawable.bg_img,
            title = "Relationship Counselor"
        ),
        User(
            id = "randomId",
            name = "Segun Famisa",
            bio = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships.",
            backgroundImg = R.drawable.bg_img_2,
            title = "Trauma Counselor"
        )
    )

    private val therapistAdapter: TherapistAdapter by lazy{
        TherapistAdapter(therapistArray, onTherapistItemSelected)
    }

    private val onTherapistItemSelected by lazy {
        object : TherapistItemClickListener {
            override fun invoke(therapist: User) {
                val action = TherapistFragmentDirections.actionNavigationTherapistToTherapistDetailFragment(therapist)
                findNavController().navigate(action)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        therapyViewModel =
            ViewModelProviders.of(this).get(TherapyViewModel::class.java)

        return inflater.inflate(R.layout.fragment_therapist, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTherapist.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvTherapist.adapter = therapistAdapter


    }


}
