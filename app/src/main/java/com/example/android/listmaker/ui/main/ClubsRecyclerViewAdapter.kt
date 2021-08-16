package com.example.android.listmaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.databinding.ClubViewHolderBinding
import java.util.*

class ClubsRecyclerViewAdapter(
    private var clubs: List<FootballClub>, private val clickListener: RecyclerViewClickListener
    ): RecyclerView.Adapter<ClubViewHolder>() {

    interface RecyclerViewClickListener {
        fun itemClicked(id: UUID, name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        
        val binding = ClubViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ClubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val team = clubs[position]
        holder.binding.itemNameClub.text = team.footballClubName
        holder.itemView.setOnClickListener {
            clickListener.itemClicked(team.footballClubId, team.footballClubName)
        }
    }

    override fun getItemCount(): Int {
        return clubs.size
    }
}