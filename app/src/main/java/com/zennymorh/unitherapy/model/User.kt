package com.zennymorh.unitherapy.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlinx.android.parcel.RawValue

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
    var rooms: @RawValue MutableMap<String, Any>? = null
) : Parcelable

@Parcelize
data class Posts(
    val postId: String? = null,
    val name: String? = null,
    val post: String? = null,
    val timestamp: Long? = null
): Parcelable

@Parcelize
data class Message(
    val user: String? = null,
    val receiver: String? = null,
    val messageText: String = ""
): Parcelable

@Parcelize
data class Rooms(
    val roomId: String = "",
    val therapistName: String = ""
): Parcelable