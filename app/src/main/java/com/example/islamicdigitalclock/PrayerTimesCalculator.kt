package com.example.islamicdigitalclock
import com.batoulapps.adhan.*
import com.batoulapps.adhan.data.DateComponents
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
data class PrayerTime(val name: String, val time: String)
object PrayerTimesCalculator {
    private val coordinates = Coordinates(31.5204, 74.3587)
    private val params = CalculationMethod.KARACHI.parameters.apply { madhab = Madhab.HANAFI }
    fun getTodayPrayerTimes(): List<PrayerTime> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dc = DateComponents(today.year, today.monthNumber, today.dayOfMonth)
        val pt = PrayerTimes(coordinates, dc, params)
        return listOf(
            PrayerTime("فجر", fmt(pt.fajr)),
            PrayerTime("طلوع آفتاب", fmt(pt.sunrise)),
            PrayerTime("ظہر", fmt(pt.dhuhr)),
            PrayerTime("عصر", fmt(pt.asr)),
            PrayerTime("مغرب", fmt(pt.maghrib)),
            PrayerTime("عشاء", fmt(pt.isha))
        )
    }
    private fun fmt(instant: kotlinx.datetime.Instant): String {
        val l = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "%02d:%02d".format(l.hour, l.minute)
    }
}
