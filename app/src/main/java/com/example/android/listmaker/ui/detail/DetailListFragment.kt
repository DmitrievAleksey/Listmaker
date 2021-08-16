package com.example.android.listmaker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.databinding.DetailListFragmentBinding
import java.util.*

private const val ARG_CLUB_ID = "club_id"
private const val ARG_PUT_EXTRA = "put_extra"

class DetailListFragment : Fragment() {

    companion object {

        fun newInstance(clubId: UUID): DetailListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CLUB_ID, clubId)
                putBoolean(ARG_PUT_EXTRA, true)
            }
            return DetailListFragment().apply { arguments = args }
        }
    }

    private lateinit var binding: DetailListFragmentBinding
    private lateinit var playersRecyclerView: RecyclerView
    private var clubId: UUID? = null
    private var adapter: PlayersRecyclerViewAdapter? =
        PlayersRecyclerViewAdapter(emptyList())
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.getBoolean(ARG_PUT_EXTRA, false) == true) {
            clubId = arguments?.getSerializable(ARG_CLUB_ID) as UUID
            viewModel.loadClubId(clubId!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailListFragmentBinding.inflate(inflater, container,false)
        playersRecyclerView = binding.playersRecyclerview
        playersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        playersRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (clubId != null) {
            binding.textViewNullClub.visibility = View.INVISIBLE
            binding.textViewNullPlayer.visibility = View.INVISIBLE
            binding.playersRecyclerview.visibility = View.INVISIBLE
            viewModel.playersOfClubLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.players.isEmpty()) {
                        binding.textViewNullPlayer.visibility = View.VISIBLE
                    } else {
                        binding.playersRecyclerview.visibility = View.VISIBLE
                        playersRecyclerView.adapter = PlayersRecyclerViewAdapter(it.players)
                    }
                }
            })
        } else {
            binding.playersRecyclerview.visibility = View.INVISIBLE
            binding.textViewNullPlayer.visibility = View.INVISIBLE
            binding.textViewNullClub.visibility = View.VISIBLE
        }
    }

}