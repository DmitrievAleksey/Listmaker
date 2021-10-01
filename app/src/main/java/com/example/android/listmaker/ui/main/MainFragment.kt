package com.example.android.listmaker.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.MainViewModel
import com.example.android.listmaker.databinding.MainFragmentBinding
import com.example.android.listmaker.ui.logotype.AssetsImageModel
import com.example.android.listmaker.ui.logotype.loadLogoImages
import java.util.*

class MainFragment : Fragment(), ClubsRecyclerViewAdapter.RecyclerViewClickListener {

    interface Callbacks {
        fun onClubSelected(clubID: UUID, name: String)
        fun onFootballClubEdit(clubID: UUID, name: String)
    }

    private var callbacks: Callbacks? = null
    private lateinit var assetsLogoImage: List<AssetsImageModel>
    private lateinit var binding: MainFragmentBinding
    private lateinit var clubsRecyclerView: RecyclerView
    private var adapter: ClubsRecyclerViewAdapter? =
        ClubsRecyclerViewAdapter(emptyList(), emptyList(), this)
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        assetsLogoImage = loadLogoImages(requireActivity().assets)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        clubsRecyclerView = binding.clubsRecyclerview
        clubsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.clubsRecyclerview.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.footballClubs.observe(viewLifecycleOwner, {
            clubs -> clubs.let {
                clubsRecyclerView.adapter =
                    ClubsRecyclerViewAdapter(clubs, assetsLogoImage, this)
        }
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun itemClicked(id: UUID, name: String) {
        callbacks?.onClubSelected(id, name)
    }

    override fun clubViewClicked(id: UUID, name: String) {
        callbacks?.onFootballClubEdit(id, name)
    }

}