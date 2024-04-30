package com.fjr619.composefirebasedb.data.model

import com.fjr619.composefirebasedb.domain.model.Weight

data class WeightEntity(
    val id: String,
    val licenseNumber: String,
    val driverName: String,
    val inboundWeight: Int,
    val outboundWeight: Int,
    val date: String = "",
    val pushId: String,
) {
    constructor() : this("0", "", "", 0, 0, "", "")
}

fun WeightEntity.toDomain() =
    Weight(id, licenseNumber, driverName, inboundWeight, outboundWeight, date, pushId)