package com.example.vamz_semestralka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamz_semestralka.ui.theme.Vamz_semestralkaTheme
import kotlinx.coroutines.delay
import java.util.Calendar

class MainActivity : ComponentActivity() {
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
                            GetHours()
                        }
                        Box(modifier = Modifier
                            .weight(3f)) {
                            Text("-2-")

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
fun GetHours() {
    var currentTimeMillis by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Update every second
            currentTimeMillis = Calendar.getInstance().getTimeInMillis()
        }
    }
    val seconds = (currentTimeMillis / 1000) % 60
    val minutes = (currentTimeMillis / (1000 * 60)) % 60
    val hours = (currentTimeMillis / (1000 * 60 * 60) % 24) + 2

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(1.dp, 10.dp),
        verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ClockBox(text = "$hours")
            ClockBox(text = "$minutes")
            ClockBox(text = "$seconds")
        }
    }
}

@Composable
private fun ClockBox(text: String) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .padding(2.dp, 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color.LightGray)
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Vamz_semestralkaTheme {
    }
}