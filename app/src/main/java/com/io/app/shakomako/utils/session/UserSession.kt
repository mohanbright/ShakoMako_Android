package com.io.app.shakomako.utils.session

import android.content.SharedPreferences
import com.io.app.shakomako.utils.constants.AppConstant
import javax.inject.Inject

class UserSession @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {
    val userToken: String?
        get() = getStringValue(SessionConstants.USER_TOKEN)

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getIsUserVerified(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    fun getIntValue(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun updateUserToken(userToken: String?) {
        saveCommit(SessionConstants.USER_TOKEN, userToken)
    }

    fun userTokenSync(isSync: Boolean) {
        save(SessionConstants.FCM_TOKEN_SYNC, isSync)
    }

    fun updateFcmToken(fcmToken: String) {
        save(SessionConstants.FCM_TOKEN, fcmToken)
    }

    val fcmToken: String?
        get() = sharedPreferences.getString(SessionConstants.FCM_TOKEN, "")

    fun createSession(userToken: String, verified: Boolean) {
        save(SessionConstants.USER_TOKEN, userToken)
        save(SessionConstants.USER_LOGGED_IN, verified)
    }

    fun save(key: String?, `val`: String?) {
        editor.putString(key, `val`)
        editor.apply()
    }

    fun save(key: String?, `val`: Boolean) {
        editor.putBoolean(key, `val`)
        editor.apply()
    }

    fun save(key: String?, `val`: Int) {
        editor.putInt(key, `val`)
        editor.apply()
    }

    fun saveCommit(key: String?, `val`: String?) {
        editor.putString(key, `val`)
        editor.apply()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    fun saveLanguage(value: String) {
        editor.putString(AppConstant.LANGUAGE_TYPE, value)
        editor.apply()

    }

    var language = sharedPreferences.getString(AppConstant.LANGUAGE_TYPE, "en")
}