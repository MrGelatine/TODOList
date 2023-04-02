package com.mrgelatine.todolist

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.realm.kotlin.Realm
import java.sql.Date
import java.time.Instant
import java.util.*

class ItemCreateFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_item_create, container, false)
        val viewModel: itemRowsViewModel by activityViewModels()
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            val name = binding.findViewById<EditText>(R.id.itemName).text.toString()
            val info = binding.findViewById<EditText>(R.id.itemInfo).text.toString()
            val date = Instant.now()
            val realmDate = date.toRealmInstant()
            viewModel.realm.writeBlocking {
                copyToRealm(NoteRealmObject().apply {
                    this.checked = false
                    this.date = realmDate
                    this.name = name
                    this.text = info
                })
            }
            viewModel.addRow(false, name, info, date)
            findNavController().popBackStack()
        }
        // Inflate the layout for this fragment
        return binding
    }
}