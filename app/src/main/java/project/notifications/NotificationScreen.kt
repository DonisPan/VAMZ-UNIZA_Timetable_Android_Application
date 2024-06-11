package project.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NotificationScreen(navController: NavController, service: TimeTableNotifications) {
    var message by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var expandDays by rememberSaveable { mutableStateOf(false) }
    var selectedDay by rememberSaveable { mutableStateOf("") }
    val daysOfWeek = listOf("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota", "Nedeľa")
    selectedDay = daysOfWeek[0]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Pridať upozornenie", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }
        // MESSAGE
        item {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Správa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // TIME
        item {
            TextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Čas vo formáte HH:MM") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // DAYS OF THE WEEK
        item {
            Box {
                Text(
                    text = selectedDay,
                    modifier = Modifier
                        .clickable { expandDays = true }
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expandDays,
                    onDismissRequest = { expandDays = false }
                ) {
                    daysOfWeek.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day) },
                            onClick = {
                                selectedDay = day
                                expandDays = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        // SAVE
        item {
            Button(
                onClick = {
                    service.showNotification(message, time)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pridať upozornenie")
            }
        }
    }
}
