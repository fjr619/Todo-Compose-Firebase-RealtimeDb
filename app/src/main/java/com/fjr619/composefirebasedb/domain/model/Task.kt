package com.fjr619.composefirebasedb.domain.model

data class Task(
    val id: String,
    val title: String,
    val desc: String,
    val favorite: Boolean,
    val completed: Boolean
)