package com.webslinger.dejavu.presentation.main


import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.webslinger.dejavu.R
import com.webslinger.dejavu.application.viewmodel.TakeAfterPictureViewModel
import com.webslinger.dejavu.databinding.TakeAfterPictureFragmentBinding

class TakeAfterPictureFragment : Fragment() {
    private lateinit var dataBinding: TakeAfterPictureFragmentBinding
    private lateinit var viewModel: TakeAfterPictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.take_after_picture_fragment, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TakeAfterPictureViewModel::class.java)
        // TODO: Use the ViewModel
        arguments?.let {
            val beforePictureUri: Uri = it.get("BEFORE_PICTURE_PATH") as Uri
            Glide.with(this)
                .load(beforePictureUri)
                .into(dataBinding.beforePictureOverlay)

        }

        dataBinding.beforePictureOverlay.imageAlpha = 30
    }


    companion object {
        fun newInstance() = TakeAfterPictureFragment()
    }
}