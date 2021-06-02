package app.capstone.bantuansosialdetector.core.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)

    var nikUserPref: String?
    get() = preferences.getString(ID_USER, null)
    set(value) = preferences.edit().putString(ID_USER, value).apply()


    companion object {
        private const val USER_PREF = "pref_key"
        private const val ID_USER = "id_user"
    }
}