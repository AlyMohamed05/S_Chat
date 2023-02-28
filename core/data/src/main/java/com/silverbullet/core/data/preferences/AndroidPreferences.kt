package com.silverbullet.core.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class AndroidPreferences @Inject constructor(
    private val sharedPref: SharedPreferences
) : Preferences {

}