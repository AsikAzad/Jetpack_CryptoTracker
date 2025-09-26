package com.azad.cryptotracker.crypto.data.util

import java.time.ZoneId
import java.time.ZonedDateTime

//To convert the zone datetime to unified milliseconds
fun convertZoneTimeToMillis(zoneDateTime: ZonedDateTime): Long {
    return zoneDateTime
        .withZoneSameInstant(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()
}