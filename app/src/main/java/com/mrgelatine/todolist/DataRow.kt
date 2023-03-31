package com.mrgelatine.todolist

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.navigation.findNavController

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

data class DataRow(val chcd:Boolean, val name:String, val info:String, var parent: TODOLIistAdapter) {
    var checked:ObservableBoolean = ObservableBoolean()
    var text:ObservableField<String> = ObservableField<String>()
    init{
        checked.set(chcd)
        text.set(name)
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