package com.example.simsekolah.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.simsekolah.core.data.model.User
import com.google.gson.Gson

class SharedPref(activity: Activity) {
    var login = "Login"
    var nama = "nama"
    var email = "email"
    var user = "user"
    val mypref = "MY_PREF"
    val sp: SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean) {
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin(): Boolean {
        return sp.getBoolean(login, false)
    }

    fun setUser(value: User) {
        val data: String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser(): User? {
        val data: String = sp.getString(user, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

    fun setString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun getStrin(key: String): String {
        return sp.getString(key, "")!!
    }
}