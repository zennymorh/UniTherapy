package com.zennymorh.unitherapy.ui.community.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.community.CommunityAdapter

class CommunitiesFragment : Fragment() {
    private var communityArray = arrayListOf(
        User(
            id = "randomId",
            name = "Zainab Jimoh",
            post = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships."
        ),
        User(
            id = "randomId",
            name = "Ezichi Amarachi",
            post = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships."
        ),
        User(
            id = "randomId",
            name = "Vivian Fatima",
            post = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships."
        ),
        User(
            id = "randomId",
            name = "Segun Famisa",
            post = "I believe everyone should have a safe and healing place to work through life's " +
                    "difficulties. I try to help bring families and couples closer together through " +
                    "therapeutic work and help to have fulfilling relationships."
        )
    )

    private val communityAdapter = CommunityAdapter(
        communityArray
    ){
        // TODO: 18/07/2021
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as RecyclerView).adapter = communityAdapter
    }
}