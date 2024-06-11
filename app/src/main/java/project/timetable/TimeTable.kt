package project.timetable

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.LectureType
import project.Subjects

class TimeTable
{
    data class ClassBlock(val subject: Subjects? = null, val teachName: String? = null, val t: LectureType? = null, val classroom: String? = null)
    private val numberOfDays = 5
    private val numberOfBlocks = 13
    private var block = MutableList(this.numberOfDays * this.numberOfBlocks) { ClassBlock() }

    @Composable
    fun Table(navController: NavController, context: Context)
    {
        var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
        var items by rememberSaveable { mutableStateOf(block) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            for (row in 0 until numberOfDays)
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    var timeNum = 7
                    for (column in 0 until numberOfBlocks)
                    {
                        Column ()
                        {
                            Row()
                            {
                                if (row == 0) {
                                    Text(
                                        text = (timeNum).toString() + ":00",
                                        fontSize = 6.sp
                                    )
                                }
                            }
                            timeNum += 1
                            Row()
                            {
                                val index = row * numberOfBlocks + column
                                val item = items[index]
                                GridItem(item, onClick = { selectedItemIndex = index })
                            }
                        }
                    }
                }
            }

            selectedItemIndex?.let {index ->
                val selectedItem = items[index]
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center)
                    {
                        if (selectedItem.subject != null)
                        {
                            Text(
                                text = selectedItem.subject.getName() ?: "",
                                modifier = Modifier.padding(top = 16.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold)
                            Text(
                                text = selectedItem.teachName ?: "",
                                modifier = Modifier.padding(top = 10.dp),
                                fontSize = 18.sp)
                            Text(
                                text = "Miestnosť: " + (selectedItem.classroom ?: ""),
                                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                                fontSize = 18.sp)
                        }
                    }
                }

                Row (modifier = Modifier.padding(top = 20.dp))
                {
                    Button(onClick =
                    {
                        navController.navigate("editor/$index")
                        //saveToLocal(context)
                    }) {
                    Text(text = "Pridaj")
                    }

                    Button(onClick =
                    {
                        items = items.toMutableList().apply { set(index+1, selectedItem) }
                        block = items
                        selectedItemIndex = null })
                    {
                        Text(text = "->")
                    }

                    Button(onClick =
                    {
                        items = items.toMutableList().apply { set(index, ClassBlock()) }
                        block = items
                        selectedItemIndex = null
                        //saveToLocal(context)
                    })
                    {
                    Text(text = "Zruš")
                    }
                }
                Row ( modifier = Modifier.padding(top = 5.dp) )
                {
                    Button(onClick =
                    {
                        navController.navigate("notification_screen")
                        //saveToLocal(context)
                    })
                    {
                        Text(text = "Pridať upozornenie")
                    }
                }
            }
        }
    }

    @Composable
    fun GridItem(item: ClassBlock, onClick: () -> Unit)
    {
        Box(
            modifier = Modifier
                .size(27.dp, 58.dp)
                .clip(RoundedCornerShape(3.dp))
                .border(BorderStroke(2.dp, Color.Gray.copy(alpha = 0.3f)))
                .background(getColor(item.t))
                .clickable { onClick() }
                .padding(8.dp),
            contentAlignment = Alignment.Center)
        {
            Text(
                text = item.subject?.shorter ?: "",
                style = TextStyle(
                    fontSize = 9.sp,
                    lineHeight = 11.sp
                ),
                modifier = Modifier.fillMaxSize())
        }
    }

    private fun getColor(type: LectureType?): Color
    {
        return when (type)
        {
            LectureType.PREDNASKA -> Color.Red.copy(alpha = 0.3f)
            LectureType.CVIKO -> Color.Blue.copy(alpha = 0.3f)
            LectureType.LABAK -> Color.Green.copy(alpha = 0.3f)
            else -> Color.LightGray
        }
    }

    fun replaceBlock(index: Int, newBlock: ClassBlock)
    {
        block[index] = newBlock
    }

    fun saveToLocal(context: Context) {
        val sharedPreferences = context.getSharedPreferences("TimeTablePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(block)
        editor.putString("time_table", json)
        editor.apply()
    }

    fun loadFromLocal(context: Context) {
        val sharedPreferences = context.getSharedPreferences("TimeTablePrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("time_table", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<ClassBlock>>() {}.type
            block = Gson().fromJson(json, type)
        }
    }
}