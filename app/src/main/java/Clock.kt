import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Calendar

class Clock {
    @Composable
    fun GetHours()
    {
        var currentTimeMillis by remember { mutableLongStateOf(0L) }
        LaunchedEffect(Unit)
        {
            while (true)
            {
                delay(1000) // updatne sa kazdu sekundu
                currentTimeMillis = Calendar.getInstance().timeInMillis
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
                horizontalArrangement = Arrangement.Center)
            {
                ClockBox(hours)
                ClockBox(minutes)
                ClockBox(seconds)
            }
        }
    }

    @Composable
    private fun ClockBox(time: Long) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .padding(2.dp, 10.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color = Color.LightGray))
        {
            Text(
                text = String.format("%02d", time),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center))
        }
    }
}