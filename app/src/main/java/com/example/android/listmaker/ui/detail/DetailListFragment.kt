package com.example.android.listmaker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.R
import com.example.android.listmaker.database.Player
import com.example.android.listmaker.databinding.DetailListFragmentBinding
import com.example.android.listmaker.MainViewModel
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
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.getBoolean(ARG_PUT_EXTRA, false) == true) {
            clubId = arguments?.getSerializable(ARG_CLUB_ID) as UUID
            viewModel.loadClubId(clubId)
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
            viewModel.playersOfClubLiveData.observe(viewLifecycleOwner, {
                if (it != null) {
                    binding.textViewNullClub.visibility = View.INVISIBLE
                    binding.textViewNullPlayer.visibility = View.INVISIBLE
                    binding.playersRecyclerview.visibility = View.INVISIBLE

                    if (it.players.isEmpty()) {
                        binding.textViewNullPlayer.visibility = View.VISIBLE
                    } else {
                        binding.playersRecyclerview.visibility = View.VISIBLE
                        playersRecyclerView.adapter = PlayersRecyclerViewAdapter(it.players)
                    }
                }
            })
        } else {
            binding.textViewNullClub.visibility = View.VISIBLE
            binding.textViewNullPlayer.visibility = View.INVISIBLE
            binding.playersRecyclerview.visibility = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()

        binding.addPlayerFabButton.setOnClickListener {
            clubId?.let { uuid -> showCreateTaskDialog(uuid) }
        }
    }

    private fun showCreateTaskDialog(clubID: UUID) {
        //1
        val taskEditText = EditText(requireContext())
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        //2
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.name_of)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_club) { dialog, _ ->
                // 3
                val player = Player().apply {
                    clubId = clubID
                    playerName = taskEditText.text.toString()
                }
                // 4
                viewModel.addPlayer(player)
                //5
                dialog.dismiss()
            }
            //6
            .create()
            .show()
    }
}