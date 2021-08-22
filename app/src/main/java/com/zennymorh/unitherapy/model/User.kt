package com.zennymorh.unitherapy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val email: String? = null,
    @field:JvmField
    val isTherapist: Boolean = false,
    val isFavorite: Boolean = false,
    val backgroundImg: Int? = null,
    val title: String? = null,
    val fullDesc: String? = null,
    val workExp: String? = null,
    val hobbies: String? = null,
    val post: String? = null
) : Parcelable
