package com.example.android.listmaker.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.database.Player
import com.example.android.listmaker.databinding.PlayerViewHolderBinding

class PlayersRecyclerViewAdapter(private var players: List<Player>):
    RecyclerView.Adapter<PlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val binding = PlayerViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return PlayersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.binding.itemNamePlayer.text = players[position].playerName
    }

    override fun getItemCount(): Int {
        return players.size
    }
}