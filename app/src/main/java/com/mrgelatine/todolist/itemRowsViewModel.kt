package com.mrgelatine.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class itemRowsViewModel: ViewModel() {
    var data:MutableLiveData<MutableList<DataRow>> = MutableLiveData()
    var realm:Realm = Realm.open(RealmConfiguration.create(schema = setOf(NoteRealmObject::class)))
    val adapter = TODOLIistAdapter(this,realm)
    lateinit var curRow:DataRow
    init{
        data.value = realm.copyFromRealm(realm.query<NoteRealmObject>().find()).map{it.toDataRow(adapter)!!}.toMutableList()
    }
    fun addRow(flag:Boolean, name:String, info:String, date: Instant){
        val row = DataRow(flag,name, info, date, adapter)
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