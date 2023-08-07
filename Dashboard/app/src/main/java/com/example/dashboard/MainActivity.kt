package com.example.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dashboard.components.MainPage
import com.example.dashboard.ui.theme.DashboardTheme


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            DashboardTheme {
                MainPage()
            }
        }
    }
}