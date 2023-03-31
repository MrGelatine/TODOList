package com.mrgelatine.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mrgelatine.todolist.databinding.FragmentItemsListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsListFragment() : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding:FragmentItemsListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_items_list, container, false);
        val viewModel: itemRowsViewModel by activityViewModels()

        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.itemCreateFragment)
        }
        viewModel.data.observe(viewLifecycleOwner,Observer<MutableList<DataRow>>{
            viewModel.adapter.refresh(it)
        })
        binding.adapter = viewModel.adapter
        return binding.root
    }
}