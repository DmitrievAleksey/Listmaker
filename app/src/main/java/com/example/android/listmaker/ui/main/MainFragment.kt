package com.example.android.listmaker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: MainFragmentBinding
    private lateinit var clubsRecyclerView: RecyclerView
    private var adapter: ClubsRecyclerViewAdapter? = ClubsRecyclerViewAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container,false)
        clubsRecyclerView = binding.clubsRecyclerview
        clubsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.clubsRecyclerview.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.footballClubs.observe(viewLifecycleOwner, Observer {
            clubs -> clubs.let {
                adapter = ClubsRecyclerViewAdapter(clubs)
                clubsRecyclerView.adapter = adapter
        }
        })
    }
}