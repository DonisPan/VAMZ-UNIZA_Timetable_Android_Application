package com.example.vamz_semestralka

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import project.clock.Clock
import project.timetable.TimeTable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vamz_semestralka.ui.theme.Vamz_semestralkaTheme
import project.editor.EditorScreen
import project.notifications.NotificationScreen
import project.notifications.TimeTableNotifications


class MainActivity : ComponentActivity()
{
    private val clock = Clock()
    private val timeTable = TimeTable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        createNotificationChannel()
        val service = TimeTableNotifications(applicationContext)
        //LOAD FROM LOCAL
        timeTable.loadFromLocal(this)

        setContent {
            Vamz_semestralkaTheme ()
            {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "main_screen") {
                        composable("main_screen")
                        {
                            MainScreen(navController, clock, timeTable)
                        }

                        composable(
                            "editor/{index}",
                            arguments = listOf(navArgument("index") { type = NavType.IntType }))
                        { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: 0
                            EditorScreen(navController, index)
                            { updatedBlock ->
                                timeTable.replaceBlock(index, updatedBlock)
                            }
                        }

                        composable("notification_screen")
                        {
                            NotificationScreen(navController, service)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController, clock: Clock, timeTable: TimeTable) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GetClockScreen(clock)
                }
                Spacer(modifier = Modifier.height(30.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GetTimeTable(navController, timeTable)
                }
            }

            item {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier
                            .padding(3.dp),
                        onClick = { timeTable.saveToLocal(this@MainActivity)}
                    )
                    {
                        Text(
                            text = "Ulož zmeny",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun GetClockScreen(clock: Clock)
    {
        clock.GetClock()
    }

    @Composable
    private fun GetTimeTable(navController: NavController, timeTable: TimeTable)
    {
        timeTable.Table(navController, this@MainActivity)
    }

    //NOTIFICATION CHANNEL
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "timetable_notification_channel",
            "TimeTable",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "show TimeTable notification"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

