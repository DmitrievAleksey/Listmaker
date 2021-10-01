package com.example.android.listmaker.ui.logotype

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.databinding.AssetsLogoHolderBinding

class LogoRecycleViewAdapter(private val logoImages: List<AssetsImageModel>,
                             private val clickListener: RecyclerViewClickListener
                             ): RecyclerView.Adapter<LogoViewHolder>() {

    interface RecyclerViewClickListener {
        fun itemClicked(imageModel: AssetsImageModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogoViewHolder {
        val binding = AssetsLogoHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return LogoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogoViewHolder, position: Int) {
        holder.binding.imageAssetFile.setImageBitmap(logoImages[position].bitmap)
        holder.binding.imageAssetFile.setOnClickListener {
            clickListener.itemClicked(logoImages[position])
        }
    }

    override fun getItemCount(): Int {
        return logoImages.size
    }
}