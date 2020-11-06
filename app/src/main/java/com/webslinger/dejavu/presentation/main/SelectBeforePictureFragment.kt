package com.webslinger.dejavu.presentation.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.viewmodel.SelectBeforePictureViewModel

class SelectBeforePictureFragment : Fragment() {

    companion object {
        fun newInstance() = SelectBeforePictureFragment()
    }

    private lateinit var viewModel: SelectBeforePictureViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.select_before_picture_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectBeforePictureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}