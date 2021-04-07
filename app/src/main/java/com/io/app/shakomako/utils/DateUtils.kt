package com.io.app.shakomako.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun dateFormat(dateString: String): String {
    try {


        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        sourceFormat.timeZone = utc

        val destFormat = SimpleDateFormat("HH:mm a")
        destFormat.timeZone = TimeZone.getDefault()


        val convertedDate = sourceFormat.parse(dateString)
        return destFormat.format(convertedDate)

    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }

}

fun isValid(value: String?): Boolean {
    return value == null || value.isEmpty()
}



