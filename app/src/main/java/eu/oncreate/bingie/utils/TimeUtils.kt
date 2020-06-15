package eu.oncreate.bingie.utils

fun Long.minutesToHours(): Float = this.div(60f).round(2)

fun Float.hoursString(): String = String.format("%.2f", this)

fun Float.hoursToDaysString(hoursInDay: Float = 24f): String =
    String.format("%.2f", this.div(hoursInDay))

private fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return (this * multiplier).toInt() / multiplier
}
