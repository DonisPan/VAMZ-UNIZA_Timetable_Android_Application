package com.example.vamz_semestralka

import Clock
import Editor
import TimeTable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vamz_semestralka.ui.theme.Vamz_semestralkaTheme

class MainActivity : ComponentActivity() {

    private val clock = Clock()
    private val timeTable = TimeTable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vamz_semestralkaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            MainScreen(navController, clock, timeTable)
                        }
                        composable("editor/{index}",
                            arguments = listOf(navArgument("index") {type = NavType.IntType })
                        ) {
                                backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: 0
                            Editor(index) { updatedBlock ->
                                timeTable.replaceBlock(index, updatedBlock)
                                navController.popBackStack()
                            }
                    }
                }
            }
        }
    }
}
@Composable
fun MainScreen(navController: NavController, clock: Clock, timeTable: TimeTable) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Row(modifier = Modifier
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically) {
            GetClockScreen(clock)
        }
        Row(modifier = Modifier
            .weight(6f)) {
            GetTimeTable(navController, timeTable)
        }
    }
}
@Composable
private fun GetClockScreen(clock: Clock) {
    clock.GetHours()
}
@Composable
private fun GetTimeTable(navController: NavController, timeTable: TimeTable) {
    timeTable.Table(navController)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Vamz_semestralkaTheme {
    }
}}