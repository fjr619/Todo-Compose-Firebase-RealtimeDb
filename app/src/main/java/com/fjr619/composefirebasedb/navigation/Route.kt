package com.fjr619.composefirebasedb.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data object RootNav: AppRoute()

    @Serializable
    data object HomeScreen: AppRoute()

    @Serializable
    data object TaskScreen: AppRoute()
}

//Still buggy, waiting for next compose navigation version
//val TaskParametersType = object : NavType<Task>(isNullableAllowed = false){
//    override fun get(bundle: Bundle, key: String): Task?         {
//        return bundle.getParcelable(key)
//    }
//
//    override fun parseValue(value: String): Task {
//        return Json.decodeFromString<Task>(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: Task) {
//        bundle.putParcelable(key, value)
//    }
//
//}