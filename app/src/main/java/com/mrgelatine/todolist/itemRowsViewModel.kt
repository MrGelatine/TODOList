package com.mrgelatine.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class itemRowsViewModel: ViewModel() {
    var data:MutableLiveData<MutableList<DataRow>> = MutableLiveData()
    val adapter = TODOLIistAdapter(this)
    lateinit var curRow:DataRow
    fun addRow(flag:Boolean, name:String, info:String){
        val row = DataRow(flag,name, info, adapter)
        if(data.value == null){
            data.value = mutableListOf(row)
        }else{
            data.value?.add(row)
        }
    }
    fun focus(row:DataRow){
        curRow = row
    }
}