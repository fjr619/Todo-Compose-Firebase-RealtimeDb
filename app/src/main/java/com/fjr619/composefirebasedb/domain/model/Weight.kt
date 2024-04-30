package com.fjr619.composefirebasedb.domain.model

data class Weight(
    val id : String,
    val licenseNumber : String,
    val driverName : String,
    val inboundWeight : Int,
    val outboundWeight : Int,
    val date : String,
    val pushId : String,
)