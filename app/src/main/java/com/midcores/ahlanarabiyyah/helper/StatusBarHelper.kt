package com.midcores.ahlanarabiyyah.helper

import android.app.Activity
import android.content.res.Configuration
import com.jaeger.library.StatusBarUtil

object StatusBarHelper {
    fun setTransparentStatusBar(activity: Activity) {
        StatusBarUtil.setTransparent(activity)
        when (activity.resources.configuration.uiMode and
              Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                StatusBarUtil.setDarkMode(activity)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                StatusBarUtil.setLightMode(activity)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                StatusBarUtil.setLightMode(activity)
            }
        }
    }
}