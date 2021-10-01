package com.example.android.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.android.listmaker.databinding.MainActivityBinding
import com.example.android.listmaker.ui.detail.DetailListFragment
import com.example.android.listmaker.ui.main.FootballClubFragment
import com.example.android.listmaker.ui.main.MainFragment
import java.util.*

class MainActivity : AppCompatActivity(), MainFragment.Callbacks {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_club -> {
                if (binding.mainFragmentContainer == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,
                            FootballClubFragment.newInstance( "Новый клуб"))
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.detail_list_fragment_container,
                            FootballClubFragment.newInstance( "Новый клуб"))
                        .addToBackStack(null)
                        .commit()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onClubSelected(clubID: UUID, name: String) {

        if (binding.mainFragmentContainer == null) {
            val detailIntent = DetailActivity.newIntent(this, clubID, name)
            startActivity(detailIntent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_list_fragment_container, DetailListFragment.newInstance(clubID))
                .commit()
        }
    }

    override fun onFootballClubEdit(clubID: UUID, name: String) {

        if (binding.mainFragmentContainer == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                    FootballClubFragment.newInstance(clubID, name))
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.detail_list_fragment_container,
                    FootballClubFragment.newInstance(clubID, name))
                .addToBackStack(null)
                .commit()
        }
    }
}