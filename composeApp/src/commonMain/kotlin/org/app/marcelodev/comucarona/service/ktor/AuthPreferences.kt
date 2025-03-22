package org.app.marcelodev.comucarona.service.ktor

import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilder

class AuthPreferences(
    private val sharedPreferences: SharedPreferencesBuilder
) {
    /**
     * userId this is user id of user
     * @return Int this is return user id
     * @param value this is value of user id
     */
    var userName: String?
        get() = sharedPreferences.getString("username", null)
        set(value) {
            if (value != null) {
                sharedPreferences.putString("username", value)
            }
        }

    /**
     * photoUrl this is photo url of user
     * @return String this is return photo url
     * @param value this is value of photo url
     */
    var photoUrl: String?
        get() = sharedPreferences.getString("photo_url", null)
        set(value) {
            if (value != null) {
                sharedPreferences.putString("photo_url", value)
            }
        }

    /**
     * accessToken this is access token of user
     * @return String this is return access token
     * @param value this is value of access token
     */
    var accessToken: String?
        get() = sharedPreferences.getString("access_token", null)
        set(value) {
            if (value != null) {
                sharedPreferences.putString("access_token", value)
            }
        }

    /**
     * refreshToken this is refresh token of user
     * @return String this is return refresh token
     * @param value this is value of refresh token
     */
    var refreshToken: String?
        get() = sharedPreferences.getString("refresh_token", null)
        set(value) {
            if (value != null) {
                sharedPreferences.putString("refresh_token", value)
            }
        }

    /**
     * saveTokens this method is used to save access token and refresh token in shared preferences
     * @param accessToken this is access token of user
     * @param refreshToken this is refresh token of user
     * @return Unit this is return nothing
     */
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.apply {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
        }
    }

    /**
     * clearTokens this method is used to clear access token and refresh token in shared preferences
     * @return Unit this is return nothing
     */
    fun clearTokens() {
        sharedPreferences.apply {
            putString("access_token", "")
            putString("refresh_token", "")
        }
    }
}