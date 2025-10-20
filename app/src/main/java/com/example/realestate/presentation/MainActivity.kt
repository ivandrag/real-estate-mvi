package com.example.realestate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.realestate.presentation.navigation.NavHostContainer
import com.example.realestate.presentation.theme.RealEstateTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateTheme(
                darkTheme = false
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHostContainer(rememberNavController())
                }
            }
        }
    }
}
