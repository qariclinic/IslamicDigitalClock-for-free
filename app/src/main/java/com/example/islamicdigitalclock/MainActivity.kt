package com.example.islamicdigitalclock
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.islamicdigitalclock.ui.components.DigitalClock
import com.example.islamicdigitalclock.ui.components.EventHighlight
import com.example.islamicdigitalclock.ui.components.PrayerTimesList
import com.example.islamicdigitalclock.ui.theme.IslamicDigitalClockTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IslamicDigitalClockTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}
@Composable
fun MainScreen() {
    val hijriDate = remember { HijriDateHelper.getCurrentHijriDate() }
    val gregorianDate = remember { HijriDateHelper.getCurrentGregorianDate() }
    val events = remember { IslamicEventsHelper.getTodayEvents() }
    val prayerTimes = remember { PrayerTimesCalculator.getTodayPrayerTimes() }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Made by Mufti Hafiz Muhammad Shoaib Khan Alai",
            fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        DigitalClock()
        Spacer(Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("ہجری تاریخ: $hijriDate", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("شمسی تاریخ: $gregorianDate", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(16.dp))
        if (events.isNotEmpty()) {
            EventHighlight(events = events)
            Spacer(Modifier.height(16.dp))
        }
        PrayerTimesList(prayerTimes = prayerTimes)
    }
}
