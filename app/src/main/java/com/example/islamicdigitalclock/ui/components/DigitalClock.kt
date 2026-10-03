package com.example.islamicdigitalclock.ui.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
@Composable
fun DigitalClock() {
    var t by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val n = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            t = "%02d:%02d:%02d".format(n.hour, n.minute, n.second)
            delay(1000)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(t, fontSize = 48.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp))
    }
}
