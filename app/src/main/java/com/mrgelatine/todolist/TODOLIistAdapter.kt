package com.mrgelatine.todolist

import android.content.Context
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrgelatine.todolist.databinding.RowBinding
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class TODOLIistAdapter(val vm:itemRowsViewModel, val realm:Realm): RecyclerView.Adapter<TODOListViewHolder>() {
    var recyclerRows:MutableList<DataRow> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TODOListViewHolder {
        val binding:RowBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
        R.layout.row, parent, false);

        return TODOListViewHolder(binding);
    }

    override fun onBindViewHolder(holder: TODOListViewHolder, position: Int) {
        holder.bind(recyclerRows[position]);
    }
    fun remove(row: DataRow){
        val ind = recyclerRows.indexOf(row)
        recyclerRows.removeAt(ind)
        notifyItemRemoved(ind)
        realm.writeBlocking {
            val writeTransactionItems = query<NoteRealmObject>("name == $0 AND text == $1",row.name.get()!!,row.text).find()
            delete(writeTransactionItems.first())
        }

    }
    fun refresh(data: MutableList<DataRow>){
        recyclerRows = data
    }

    override fun getItemCount(): Int {
        return recyclerRows.size
    }
    fun focus(row:DataRow){
        vm.focus(row)
    }
}

class TODOListViewHolder(var rowBinding: RowBinding) :
    RecyclerView.ViewHolder(rowBinding.root) {

    fun bind(obj: Any?) {
        rowBinding.setVariable(BR.model, obj)
        rowBinding.executePendingBindings()
    }
}