package com.zennymorh.unitherapy.ui.community.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.community.CommunityAdapter
import kotlinx.android.synthetic.main.community_item.*

class CommunitiesFragment : Fragment() {
//    private var communityArray = arrayListOf(
//        User(
//            id = "randomId",
//            name = "Zainab Jimoh",
//            post = "Feeling overwhelmed today,Send help"
//        ),
//        User(
//            id = "randomId",
//            name = "Ezichi Amarachi",
//            post = "I heard something interesting today that we treat others unconsciously how we wanna " +
//                    "be treated."
//        ),
//        User(
//            id = "randomId",
//            name = "Vivian Fatima",
//            post = "I'm doing better today, and I am so proud of myself"
//        ),
//        User(
//            id = "randomId",
//            name = "Segun Famisa",
//            post = "Have a good one guys!"
//        )
//    )

//    private val communityAdapter = CommunityAdapter(
//        communityArray
//    ){
//        // TODO: 18/07/2021
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (view as RecyclerView).adapter = communityAdapter

        val prefs: SharedPreferences = requireContext().getSharedPreferences(requireContext().getString(R.string.app_name), Context.MODE_PRIVATE)

//        fav_button.isChecked = prefs.getBoolean("favButtonChecked", false)
    }
}