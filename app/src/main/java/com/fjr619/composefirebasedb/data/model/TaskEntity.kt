package com.fjr619.composefirebasedb.data.model

import com.fjr619.composefirebasedb.domain.model.Task

data class TaskEntity(
    val id: String,
    val title: String,
    val desc: String,
    val date: String = "",
) {
    constructor() : this("0", "", "")
}

fun TaskEntity.toDomain() =
    Task(id, title, desc, date)