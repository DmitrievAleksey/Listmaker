package com.example.android.listmaker.ui.logotype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.databinding.LogoPickerFragmentBinding

class LogoPickerFragment: DialogFragment(),
    LogoRecycleViewAdapter.RecyclerViewClickListener {

    interface Callbacks {
        fun onImageSelected(imageModel: AssetsImageModel)
    }

    private lateinit var binding: LogoPickerFragmentBinding
    private lateinit var recycleFilePicker: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logoImages = loadLogoImages(requireActivity().assets)
        binding = LogoPickerFragmentBinding.inflate(inflater, container, false)
        recycleFilePicker = binding.recycleFilePicker
        recycleFilePicker.layoutManager = GridLayoutManager(requireContext(),3)
        recycleFilePicker.adapter = LogoRecycleViewAdapter(logoImages, this)

        return binding.root
    }

    override fun itemClicked(imageModel: AssetsImageModel) {
        targetFragment?.let { fragment -> (fragment as Callbacks).onImageSelected(imageModel) }
        dismiss()
    }

}