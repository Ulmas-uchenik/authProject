package com.example.myapplication.data

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random

object Const {
    const val PREFERENCE_NAME = "PreferenceDoctor"
    const val SHARED_PREFS_KEY = "shared_key"
    const val SHARED_PREFS_UID = "shared_uid"
    const val SHARED_PREFS_USER_ID = "shared_user_id"

    const val OK = "OK"
    const val ERROR = "Error"

    const val READED = "Readed"
    const val UNREADED = "Unreaded"

    const val NEW = "new"
    const val OLD = "old"

    const val HOW_FRAGMENT_OPEN = "HOW_FRAGMENT_OPEN"
    const val NOTIFICATION_FRAGMENT = "Notification Fragment"
}

fun main(){
    println(md5Hash("fffhello"))
}

private fun md5Hash(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest("${str}qwerty".toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}