package com.xuannie.weatheroa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.lifecycle.viewmodel.compose.viewModel
import com.xuannie.weatheroa.ui.screen.WeatherOaApp
import com.xuannie.weatheroa.ui.screen.WeatherViewModel
import com.xuannie.weatheroa.ui.theme.WeatherOATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherOATheme {
                val weatherViewModel: WeatherViewModel =
                    viewModel(this, factory = WeatherViewModel.Factory)
                WeatherOaApp(weatherViewModel = weatherViewModel)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    WeatherOATheme {
//        Greeting("Android")
//    }
//}