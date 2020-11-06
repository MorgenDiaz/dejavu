package com.webslinger.dejavu.presentation.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.viewmodel.SelectBeforePictureViewModel
import com.webslinger.dejavu.databinding.SelectBeforePictureFragmentBinding

class SelectBeforePictureFragment : Fragment() {
    private lateinit var dataBinding: SelectBeforePictureFragmentBinding
    private lateinit var viewModel: SelectBeforePictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.select_before_picture_fragment,
            container,
            false
        )
        
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectBeforePictureViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = SelectBeforePictureFragment()
    }
}