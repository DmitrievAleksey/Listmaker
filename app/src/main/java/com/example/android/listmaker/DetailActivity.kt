package com.example.android.listmaker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.listmaker.databinding.DetailActivityBinding
import com.example.android.listmaker.ui.detail.DetailListFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clubID = intent.getSerializableExtra(INTENT_UUID) as UUID
        title = intent.getStringExtra(INTENT_NAME)

        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailListFragment.newInstance(clubID))
                .commitNow()
        }
    }
}