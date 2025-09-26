package com.azad.cryptotracker.crypto.data.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun convertMillisToZoneTime(millis: Long): ZonedDateTime {
    return Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
}