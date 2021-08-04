package com.example.android.listmaker.ui.main

import android.content.Context
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
import java.util.*

class MainFragment : Fragment(), ClubsRecyclerViewAdapter.RecyclerViewClickListener {

    interface Callbacks {
        fun onClubSelected(clubID: UUID)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private var callbacks: Callbacks? = null
    private lateinit var binding: MainFragmentBinding
    private lateinit var clubsRecyclerView: RecyclerView
    private var adapter: ClubsRecyclerViewAdapter? =
        ClubsRecyclerViewAdapter(emptyList(), this)
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

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
                adapter = ClubsRecyclerViewAdapter(clubs, this)
                clubsRecyclerView.adapter = adapter
        }
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun itemClicked(id: UUID) {
        callbacks?.onClubSelected(id)
    }
}