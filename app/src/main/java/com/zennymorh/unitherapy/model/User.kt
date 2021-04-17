package com.zennymorh.unitherapy.model

data class User(
    val id: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val email: String? = null,
    val isTherapist: Boolean = false
)
