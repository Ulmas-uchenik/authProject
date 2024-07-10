package com.example.myapplication.data

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random

object Const {
    const val PREFERENCE_NAME = "PreferenceDoctor"
    const val SHARED_PREFS_KEY = "shared_key"
    const val SHARED_PREFS_UID = "shared_uid"
}

fun main(){
    val uid = Random.nextInt(100000000, 1000000000).toString()
    val secret = jj("${uid}qwerty")

    println("UID - $uid")
    println("Secret - $secret")
}
fun jj(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}