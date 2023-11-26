package com.zennymorh.unitherapy.model

import java.util.Date

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
    var rooms: MutableMap<String, Any>? = null
)

data class Posts(
    val postId: String? = null,
    val name: String? = null,
    val post: String? = null,
    val timestamp: Long? = null
)

data class Message(
    val messageText: String = "",
    val user: String? = null,
    val timestamp: Date?,
    val receiver: String? = null
)

data class Rooms(
    val roomId: String = "",
    val therapistName: String = ""
)