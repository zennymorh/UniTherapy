package com.zennymorh.unitherapy.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zennymorh.unitherapy.R
import com.zennymorh.unitherapy.model.User
import com.zennymorh.unitherapy.ui.therapist.TherapistAdapter
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {

    private lateinit var communityCollectionAdapter: CommunityCollectionAdapter
    private lateinit var viewPager: ViewPager2
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

    private val communityAdapter: CommunityAdapter by lazy{
        CommunityAdapter(communityArray, onCommunityItemSelected)
    }

    private val onCommunityItemSelected by lazy {
        object : CommunityItemClickListener {
            override fun invoke(p1: User) {
                //Nothing to happen yet
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityCollectionAdapter = CommunityCollectionAdapter(this)
        viewPager = view.findViewById(R.id.community_pager)
        viewPager.adapter = communityCollectionAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        rvCommunity.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvCommunity.adapter = communityAdapter

    }
}

class CommunityCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CommunityFragment()
            1 -> fragment = FavouriteFragment()
        }
        return fragment!!
    }
}