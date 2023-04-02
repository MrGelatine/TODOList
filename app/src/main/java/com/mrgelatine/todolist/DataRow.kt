package com.mrgelatine.todolist

import android.graphics.Paint
import android.os.Build
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.navigation.findNavController
import java.lang.String.format
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
data class DataRow(val chcd:Boolean, val n:String, val text:String, val date:Instant, var parent: TODOLIistAdapter) {
    var checked: ObservableBoolean = ObservableBoolean()
    var name: ObservableField<String> = ObservableField<String>()
    var datePreview: ObservableField<String> = ObservableField<String>()

    init {
        this.checked.set(chcd)
        name.set(n)
        val diff = Duration.between(date,Instant.now()).seconds * 1000
        val oneSec = 1000L
        val oneMin: Long = 60 * oneSec
        val oneHour: Long = 60 * oneMin
        val oneDay: Long = 24 * oneHour
        val oneMonth: Long = 30 * oneDay
        val oneYear: Long = 365 * oneDay

        val diffMin: Long = diff / oneMin
        val diffHours: Long = diff / oneHour
        val diffDays: Long = diff / oneDay
        val diffMonths: Long = diff / oneMonth
        val diffYears: Long = diff / oneYear

        when {
            diffYears > 0 -> {
                datePreview.set("$diffYears years ago")
            }
            diffMonths > 0 && diffYears < 1 -> {
                datePreview.set(" ${(diffMonths - diffYears / 12)} months ago")
            }
            diffDays > 0 && diffMonths < 1 -> {
                datePreview.set(" ${(diffDays - diffMonths / 30)} days ago")
            }
            diffHours > 0 && diffDays < 1 -> {
                datePreview.set(" ${(diffHours - diffDays * 24)} hours ago")
            }
            diffMin > 0 && diffHours < 1 -> {
                datePreview.set(" ${(diffMin - diffHours * 60)} min ago")
            }
            diffMin < 1 -> {
                datePreview.set("just now")
            }
        }
    }
    fun remove(){
        parent.remove(this)
    }
    fun textCrossing(v: View){
        checked.set((v as CheckBox).isChecked)
    }
    fun focus(v:View){
        parent.focus(this)
        v.findNavController().navigate(R.id.itemInfoFragment)
    }
}