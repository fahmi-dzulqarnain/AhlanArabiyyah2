package com.midcores.ahlanarabiyyah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.midcores.ahlanarabiyyah.helper.StatusBarHelper.setTransparentStatusBar
import com.midcores.ahlanarabiyyah.ui.component.BottomNavigation
import com.midcores.ahlanarabiyyah.ui.component.Screen
import com.midcores.ahlanarabiyyah.ui.screen.KamusScreen
import com.midcores.ahlanarabiyyah.ui.screen.LearnJourneyScreen
import com.midcores.ahlanarabiyyah.ui.theme.AhlanArabiyyahTheme
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing

class JourneyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setTransparentStatusBar(this)
        setContent {
            var currentScreen by remember { 
                mutableStateOf<Screen>(Screen.Beranda) 
            }

            AhlanArabiyyahTheme {
                Scaffold (
                    bottomBar = {
                        BottomNavigation(
                            currentScreenRoute = currentScreen.route,
                            modifier = Modifier
                                .padding(bottom = spacing.lessLarge)
                                .background(SugarCrystal)
                        ) {
                            currentScreen = it
                        }
                    }
                ) { padding ->
                    Navigation(
                        screen = currentScreen,
                        activity = this,
                        modifier = Modifier.padding(
                            top = padding.calculateTopPadding()
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(screen: Screen, activity: JourneyActivity, modifier: Modifier) {
    when (screen.route) {
        "beranda" -> LearnJourneyScreen(modifier, activity)
        "kamus" -> KamusScreen(activity, modifier)
    }
}
