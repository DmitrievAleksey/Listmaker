package com.example.android.listmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.databinding.MainActivityBinding
import com.example.android.listmaker.ui.main.MainFragment
import com.example.android.listmaker.ui.main.MainViewModel
import java.util.*

private const val INTENT_KEY = "club_UUID"

class MainActivity : AppCompatActivity(), MainFragment.Callbacks {

    private lateinit var binding: MainActivityBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        binding.addClubFabButton.setOnClickListener {
            showCreateListDialog()
        }
    }

    override fun onClubSelected(clubID: UUID) {
        // 1
        val detailIntent = Intent(this, DetailActivity::class.java)
        // 2
        detailIntent.putExtra(INTENT_KEY, clubID)
        // 3
        startActivity(detailIntent)
    }

    private fun showCreateListDialog() {
        // 1
        val dialogTitle = getString(R.string.name_of_club)
        val positiveButtonTitle = getString(R.string.create_list)
        // 2
        val builder = AlertDialog.Builder(this)
        val titleEditText = EditText(this)
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