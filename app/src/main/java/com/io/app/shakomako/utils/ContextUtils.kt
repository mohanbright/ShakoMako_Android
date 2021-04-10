package com.io.app.shakomako.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Environment

import android.os.LocaleList
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.util.*


class ContextUtils(base: Context) : ContextWrapper(base) {

    init {

    }

    companion object {

        fun updateLocale(
            context: Context,
            localeToSwitchTo: Locale?
        ): ContextWrapper? {
            var context = context
            val resources: Resources = context.resources
            val configuration: Configuration = resources.configuration // 1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val localeList = LocaleList(localeToSwitchTo) // 2
                LocaleList.setDefault(localeList) // 3
                configuration.setLocales(localeList) // 4
            } else {
                configuration.locale = localeToSwitchTo // 5
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                context = context.createConfigurationContext(configuration) // 6
            } else {
                resources.updateConfiguration(configuration, resources.getDisplayMetrics()) // 7
            }
            return ContextUtils(context) // 8
        }

        fun imageList(): ArrayList<String> {
            val storyUrls = ArrayList<String>()

            storyUrls.add("https://images.pexels.com/photos/1366630/pexels-photo-1366630.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
            storyUrls.add("https://dummyimage.com/qvga")
            storyUrls.add("https://images.pexels.com/photos/1366630/pexels-photo-1366630.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
            storyUrls.add("https://dummyimage.com/qvga")
            storyUrls.add("https://images.pexels.com/photos/1366630/pexels-photo-1366630.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
            storyUrls.add("https://dummyimage.com/qvga")
            storyUrls.add("https://dummyimage.com/qvga")
            storyUrls.add("https://images.pexels.com/photos/1366630/pexels-photo-1366630.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")

            return storyUrls
        }

        fun uploadImage(mFile: File): MultipartBody.Part {
            var fileName: String
            val newFile: File
            if (mFile.name.contains("jpg") || mFile.name
                    .contains("png") || mFile.name.contains("jpeg")
            ) {
                newFile = File(
                    Environment.getRootDirectory()
                        .toString() + "/ShowAide" + File.separator + "SHAKO_MAKO_IMG_" + System.currentTimeMillis() + ".png"
                )
                mFile.renameTo(newFile)
                fileName = newFile.name
            } else if (mFile.name.contains("mp4")) {
                newFile = File(
                    Environment.getRootDirectory()
                        .toString() + "/ShowAide" + File.separator + "SHAKO_MAKO_VIDEO_" + System.currentTimeMillis() + ".mp4"
                )
                mFile.renameTo(newFile)
                fileName = newFile.name
            } else if (mFile.name.contains("gif")) {
                newFile = File(
                    Environment.getRootDirectory()
                        .toString() + "/ShowAide" + File.separator + "SHAKO_MAKO_GIF_" + System.currentTimeMillis() + ".gif"
                )
                mFile.renameTo(newFile)
                fileName = newFile.name
            } else {
                fileName = mFile.name
            }
            if (fileName.contains(" ")) fileName = fileName.replace(" ".toRegex(), "")
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFile)
            return MultipartBody.Part.createFormData("media", fileName, requestFile)
        }

        /**
         * Method returns the digits containing by int
         *
         * @param value
         */
        fun getDigits(value: Int): Int {
            var value = value
            var count = 0
            while (value != 0) {
                value /= 10
                count++
            }
            return count
        }

        fun getAddressFromLatLng(
            context: Context?,
            lat: Double,
            lng: Double
        ): Address? {
            val geocoder = Geocoder(context)
            var address: Address? = null
            try {
                val addresses =
                    geocoder.getFromLocation(lat, lng, 1)
                if (addresses != null && !addresses.isEmpty()) {
                    address = addresses[0]
                } else {
                    Log.e("TAG", "Unable to find zip code")
                }
            } catch (e: IOException) {
                Log.e("TAG", e.localizedMessage)
            }
            return address
        }



    }


}