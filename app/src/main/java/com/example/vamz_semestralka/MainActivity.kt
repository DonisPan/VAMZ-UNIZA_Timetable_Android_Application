package com.example.vamz_semestralka

import Clock
import TimeTable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    Column(modifier = Modifier
                        .fillMaxSize()) {
                        Box(modifier = Modifier
                            .weight(1f)) {
                            Text("-1-")
                            //GetHours()
                            GetClockScreen(clock)
                        }
                        Box(modifier = Modifier
                            .weight(3f)) {
                            Text("-2-")
                            GetTimeTable(timeTable)
                        }
                        Box(modifier = Modifier
                            .weight(1f)) {
                            Text("-3-")
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun GetClockScreen(clock: Clock) {
    clock.GetHours()
}
@Composable
private fun GetTimeTable(timeTable: TimeTable) {
    timeTable.ClickableGrid()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Vamz_semestralkaTheme {
    }
}