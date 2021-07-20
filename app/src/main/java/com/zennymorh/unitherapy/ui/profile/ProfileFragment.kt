package com.zennymorh.unitherapy.ui.profile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.text.isDigitsOnly
import com.zennymorh.unitherapy.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isEditing: Boolean = true

        saveBtn.setOnClickListener {
            isEditing = false
        }
    }

    private fun editProfile() {
        performValidations()
    }

    private fun performValidations(): Boolean {
        if (fullNameET.text.toString() == "" || fullNameET.text.toString().isDigitsOnly()) {
            fullNameET.error = "Please Enter valid Name"
            return false
        }
        if (therapistTypeET.text.toString() == "" || therapistTypeET.text.toString().isDigitsOnly()) {
            therapistTypeET.error = "Please Enter valid Therapist type"
            return false
        }
        if (fullBioET.text.toString() == "" || fullBioET.text.toString().isDigitsOnly()) {
            fullBioET.error = "Please enter a bio"
            return false
        }
        return true
    }
}