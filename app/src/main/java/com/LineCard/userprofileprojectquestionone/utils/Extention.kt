package com.LineCard.userprofileprojectquestionone.utils

import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.annotation.NonNull

/** connection manager **/
@NonNull
fun Context.isConnectedToTheInternet(): Boolean{
    val cnxManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    try{
        val netInfo : NetworkInfo? = cnxManager.activeNetworkInfo
        return netInfo?.isConnectedOrConnecting ?: false
    }catch (e: Exception){
        Log.e(ContentValues.TAG, "isConnectedToTheInternet: ${e.message}")
    }
    return false
}