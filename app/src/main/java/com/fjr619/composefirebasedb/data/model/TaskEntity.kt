package com.fjr619.composefirebasedb.data.model

import com.fjr619.composefirebasedb.domain.model.Task

data class TaskEntity(
    val id: String = "",
    val title: String = "",
    val desc: String = "",
    val favorite: Boolean = false,
    val completed: Boolean = false,
)
fun TaskEntity.toDomain() =
    Task(id, title, desc, favorite, completed)

fun Task.toEntity() = TaskEntity(id, title, desc, favorite, completed)