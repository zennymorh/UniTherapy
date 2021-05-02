package com.zennymorh.unitherapy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val email: String? = null,
    val isTherapist: Boolean = false,
    val backgroundImg: Int? = null,
    val title: String? = null
) : Parcelable
