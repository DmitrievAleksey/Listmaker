package com.example.android.listmaker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.android.listmaker.database.Player
import com.example.android.listmaker.databinding.DetailActivityBinding
import com.example.android.listmaker.ui.detail.DetailListFragment
import com.example.android.listmaker.ui.detail.DetailViewModel
import java.util.*

private const val INTENT_UUID = "club_UUID"
private const val INTENT_NAME = "club_name"

class DetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context, clubID: UUID, name: String): Intent {
            return Intent(packageContext, DetailActivity::class.java).apply {
                putExtra(INTENT_UUID, clubID)
                putExtra(INTENT_NAME, name)
            }
        }
    }

    private lateinit var binding: DetailActivityBinding
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clubID = intent.getSerializableExtra(INTENT_UUID) as UUID
        title = intent.getStringExtra(INTENT_NAME)

        binding = DetailActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailListFragment.newInstance(clubID))
                .commitNow()
        }

        binding.addPlayerFabButton.setOnClickListener {
            showCreateTaskDialog(clubID)
        }
    }

    private fun showCreateTaskDialog(clubID: UUID) {
        //1
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        //2
        AlertDialog.Builder(this)
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