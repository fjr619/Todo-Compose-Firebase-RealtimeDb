package com.fjr619.composefirebasedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Task(
    val id: String= "",
    val title: String = "",
    val desc: String = "",
    val favorite: Boolean = false,
    val completed: Boolean = false
) : Parcelable