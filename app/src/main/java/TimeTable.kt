import androidx.compose.foundation.background
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class TimeTable {
    data class ClassBlock(val subject: Subjects? = null, val teachName: String? = null, val t: LectureType? = null, val classroom: String? = null)
    private val numberOfDays = 5
    private val numberOfBlocks = 13
    private var block = MutableList(this.numberOfDays * this.numberOfBlocks) {ClassBlock()}
    @Composable
    fun Table(navController: NavController) {
        var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
        var items by remember { mutableStateOf(block)}
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0 until numberOfDays) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (column in 0 until numberOfBlocks) {
                        val index = row * numberOfBlocks + column
                        val item = items[index]
                        GridItem(item, onClick = { selectedItemIndex = index })
                    }
                }
            }

            selectedItemIndex?.let {index ->
                val selectedItem = items[index]
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (selectedItem.subject != null) {
                            Text(
                                text = selectedItem.subject.getName() ?: "",
                                modifier = Modifier.padding(top = 16.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = selectedItem.teachName ?: "",
                                modifier = Modifier.padding(top = 10.dp),
                                fontSize = 18.sp,
                            )
                            Text(
                                text = "Room: " + (selectedItem.classroom ?: ""),
                                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                                fontSize = 18.sp,
                            )
                        }

                    }
                }
                Row (modifier = Modifier.padding(top = 20.dp)) {
                    Button(onClick = {
                        navController.navigate("editor/$index")
                    }) {
                    Text(text = "Edit")
                    }

                    Button(onClick = {
                        //copyNext(selectedItemIndex!!, selectedItem)
                        items = items.toMutableList().apply { set(index+1, selectedItem) }
                        block = items
                        selectedItemIndex = null
                    }) {
                        Text(text = "->")
                    }

                    Button(onClick = {
                        items = items.toMutableList().apply { set(index, ClassBlock()) }
                        block = items
                        selectedItemIndex = null
                    }) {
                    Text(text = "Delete")
                    }
                }
                Row (modifier = Modifier.padding(top = 5.dp)) {
                    Button(onClick = {

                    }) {
                        Text(text = "Notify")
                    }
                }
            }
        }
    }

    @Composable
    fun GridItem(item: ClassBlock, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .size(28.dp, 58.dp) // velkost blocku
                .clip(RoundedCornerShape(3.dp))
                .background(getColor(item.t))
                .clickable { onClick() }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.subject?.shorter ?: "",
                fontSize = 10.sp,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    private fun getColor(type: LectureType?): Color {
        return when (type) {
            LectureType.PREDNASKA -> Color.Red.copy(alpha = 0.3f)
            LectureType.CVIKO -> Color.Blue.copy(alpha = 0.3f)
            LectureType.LABAK -> Color.Green.copy(alpha = 0.3f)
            else -> Color.LightGray
        }
    }

    fun copyNext(index: Int, newBlock: ClassBlock) {
        if (index != numberOfBlocks * numberOfDays)
            block[index+1] = newBlock
    }
    fun replaceBlock(index: Int, newBlock: ClassBlock) {
        block[index] = newBlock
    }
}