package com.puskal.core.extension

import java.text.DecimalFormat

/**
 * Created by Puskal Khadka on 3/18/2023.
 */

val decimalFormat = DecimalFormat("#.#")
fun Long.formattedCount(): String {
    return if (this < 10000) {
        this.toString()
    } else if (this < 1000000) {
        "${decimalFormat.format(this.div(1000))}K"
    } else if (this < 1000000000) {
        "${decimalFormat.format(this.div(1000000))}M"
    } else {
        "${decimalFormat.format(this.div(1000000000))}B"
    }
}

fun randomUploadDate(): String = "${(1..24).random()}h"


fun Pair<String, String>.getFormattedInternationalNumber() = "${this.first}-${this.second}".trim()