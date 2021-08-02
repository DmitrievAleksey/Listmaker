package com.example.android.listmaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.databinding.ClubViewHolderBinding

class ClubsRecyclerViewAdapter(var clubs: List<FootballClub>):
    RecyclerView.Adapter<ClubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        
        val binding = ClubViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ClubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.binding.itemName.text = clubs[position].footballClubName
    }

    override fun getItemCount(): Int {
        return clubs.size
    }
}