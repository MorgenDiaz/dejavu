package com.webslinger.dejavu.presentation.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.viewmodel.SelectBeforePictureViewModel
import com.webslinger.dejavu.databinding.SelectBeforePictureFragmentBinding

class SelectBeforePictureFragment : Fragment() {
    private lateinit var dataBinding: SelectBeforePictureFragmentBinding
    private lateinit var viewModel: SelectBeforePictureViewModel

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            findNavController().navigate(
                R.id.takeAfterPictureFragment,
                bundleOf(Pair("BEFORE_PICTURE_PATH", it))
            )
        }

    }

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

        dataBinding.selectBeforePictureButton.setOnClickListener {
            getContent.launch("image/*")
        }

    }

    companion object {
        fun newInstance() = SelectBeforePictureFragment()
    }
}