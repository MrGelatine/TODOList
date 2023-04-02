package com.mrgelatine.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import io.realm.kotlin.Realm
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import java.sql.Date
import java.time.Instant
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class NoteRealmObject: RealmObject {
    var checked:Boolean = false
    var name:String=""
    var text:String = ""
    var date:RealmInstant? = null
    @RequiresApi(Build.VERSION_CODES.O)
    fun toDataRow(adapter:TODOLIistAdapter): DataRow? {
        return date?.let { DataRow(checked, name, text, it.toInstant(), adapter) }
    }
}
// model class that stores an Instant (kotlinx-datetime) field as a RealmInstant via a conversion
    @RequiresApi(Build.VERSION_CODES.O)
    fun Instant.toRealmInstant(): RealmInstant {
        val sec: Long = this.epochSecond
        // The value is always positive and lies in the range `0..999_999_999`.
        val nano: Int = this.nano.seconds.toInt(DurationUnit.SECONDS)
        return if (sec >= 0) { // For positive timestamps, conversion can happen directly
            RealmInstant.from(sec, nano)
        } else {
            // For negative timestamps, RealmInstant starts from the higher value with negative
            // nanoseconds, while Instant starts from the lower value with positive nanoseconds
            // TODO This probably breaks at edge cases like MIN/MAX
            RealmInstant.from(sec + 1, -1_000_000 + nano)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun RealmInstant.toInstant(): Instant {
        val sec: Long = this.epochSeconds
        // The value always lies in the range `-999_999_999..999_999_999`.
        // minus for timestamps before epoch, positive for after
        val nano: Int = this.nanosecondsOfSecond
        return if (sec >= 0) { // For positive timestamps, conversion can happen directly
            Instant.ofEpochSecond(sec, nano.toLong())
        } else {
            // For negative timestamps, RealmInstant starts from the higher value with negative
            // nanoseconds, while Instant starts from the lower value with positive nanoseconds
            // TODO This probably breaks at edge cases like MIN/MAX
            Instant.ofEpochSecond(sec - 1, 1_000_000 + nano.toLong())
        }
    }


