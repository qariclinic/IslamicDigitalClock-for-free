package com.example.islamicdigitalclock
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Calendar
import java.util.GregorianCalendar
object HijriDateHelper {
    fun getCurrentHijriDate(): String {
        val g = GregorianCalendar()
        val h = Calendar.getInstance()
        // Umm al-Qura تقویم
        val hijriYear = convertToHijriYear(g.get(Calendar.YEAR),
            g.get(Calendar.MONTH) + 1, g.get(Calendar.DAY_OF_MONTH))
        return "${hijriYear[0]}/${hijriYear[1]}/${hijriYear[2]}"
    }
    fun getCurrentGregorianDate(): String {
        val t = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return "${t.dayOfMonth}/${t.monthNumber}/${t.year}"
    }
    // سادہ ہجری تبدیلی (تقریبی)
    private fun convertToHijriYear(y: Int, m: Int, d: Int): IntArray {
        val jd = gregorianToJD(y, m, d)
        val l = jd - 1948440 + 10632
        val n = (l - 1) / 10631
        val l2 = l - 10631 * n + 354
        val j = ((10985 - l2) / 5316) * ((50 * l2) / 17719) +
                (l2 / 5670) * ((43 * l2) / 15238)
        val l3 = l2 - ((30 - j) / 15) * ((17719 * j) / 50) -
                (j / 16) * ((15238 * j) / 43) + 29
        val hm = (24 * l3) / 709
        val hd = l3 - (709 * hm) / 24
        val hy = 30 * n + j - 30
        return intArrayOf(hy, hm, hd)
    }
    private fun gregorianToJD(y: Int, m: Int, d: Int): Int {
        var year = y; var month = m
        if (month <= 2) { year--; month += 12 }
        val a = year / 100
        val b = 2 - a + a / 4
        return (365.25 * (year + 4716)).toInt() + (30.6001 * (month + 1)).toInt() + d + b - 1524
    }
}
