package com.mrgelatine.todolist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import java.time.LocalDateTime
import java.time.ZoneId

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemInfoFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().popBackStack()
        }
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: itemRowsViewModel by activityViewModels()
        val time = LocalDateTime.ofInstant(viewModel.curRow.date, ZoneId.systemDefault());
        val binding = inflater.inflate(R.layout.fragment_item_info, container, false)
        val dateInfo = "${time.hour}:${time.minute} ${time.dayOfMonth} ${time.month} ${time.year}"
        binding.findViewById<TextView>(R.id.itemDatePreview).text = dateInfo
        binding.findViewById<TextView>(R.id.itemInfoPreview).text = viewModel.curRow.text

        return binding
    }

}