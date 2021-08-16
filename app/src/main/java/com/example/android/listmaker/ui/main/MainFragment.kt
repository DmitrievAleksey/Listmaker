package com.example.android.listmaker.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.listmaker.R
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.databinding.MainFragmentBinding
import java.util.*

class MainFragment : Fragment(), ClubsRecyclerViewAdapter.RecyclerViewClickListener {

    interface Callbacks {
        fun onClubSelected(clubID: UUID, name: String)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        viewModel.footballClubs.observe(viewLifecycleOwner, Observer {
            clubs -> clubs.let {
                clubsRecyclerView.adapter = ClubsRecyclerViewAdapter(clubs, this)
        }
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_club -> {
                showCreateListDialog()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun itemClicked(id: UUID, name: String) {
        callbacks?.onClubSelected(id, name)
    }

    private fun showCreateListDialog() {
        // 1
        val dialogTitle = getString(R.string.name_of_club)
        val positiveButtonTitle = getString(R.string.create_list)
        // 2
        val builder = AlertDialog.Builder(requireContext())
        val titleEditText = EditText(requireContext())
        titleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(titleEditText)
        // 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            dialog.dismiss()

            val footballClub = FootballClub().apply {
                footballClubName =  titleEditText.text.toString()
            }
            viewModel.addFootballClub(footballClub)
        }
        // 4
        builder.create().show()
    }
}