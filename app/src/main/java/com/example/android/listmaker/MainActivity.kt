package com.example.android.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.listmaker.databinding.MainActivityBinding
import com.example.android.listmaker.ui.detail.DetailListFragment
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

    override fun onClubSelected(clubID: UUID, name: String) {

        if (binding.mainFragmentContainer == null) {
            val detailIntent = DetailActivity.newIntent(this, clubID, name)
            startActivity(detailIntent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_list_fragment_container, DetailListFragment.newInstance(clubID))
                .commitNow()
        }
    }
}