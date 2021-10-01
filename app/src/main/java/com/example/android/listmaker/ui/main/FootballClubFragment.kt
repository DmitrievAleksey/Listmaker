package com.example.android.listmaker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.listmaker.MainViewModel
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.databinding.FootballClubFragmentBinding
import com.example.android.listmaker.ui.logotype.AssetsImageModel
import com.example.android.listmaker.ui.logotype.LogoPickerFragment
import com.example.android.listmaker.ui.logotype.loadLogoImages
import java.util.*

private const val ARG_CLUB_ID = "club_id"
private const val ARG_CLUB_NAME = "club_name"
private const val REQUEST_IMAGE = 0

class FootballClubFragment: Fragment(), LogoPickerFragment.Callbacks {

    companion object {
        fun newInstance(clubId: UUID, name: String): FootballClubFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CLUB_ID, clubId)
                putString(ARG_CLUB_NAME, name)
            }
            return FootballClubFragment().apply { arguments = args }
        }

        fun newInstance(name: String): FootballClubFragment {
            val args = Bundle().apply {
                putString(ARG_CLUB_NAME, name)
            }
            return FootballClubFragment().apply { arguments = args }
        }
    }

    private lateinit var binding: FootballClubFragmentBinding
    private lateinit var assetsLogoImage: List<AssetsImageModel>
    private lateinit var footballClub: FootballClub
    private var footballClubId: UUID? = null
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        footballClubId = arguments?.getSerializable(ARG_CLUB_ID) as UUID?
        viewModel.loadClubId(footballClubId)

        requireActivity().title = arguments?.getString(ARG_CLUB_NAME) as String

        assetsLogoImage = loadLogoImages(requireActivity().assets)
        footballClub = FootballClub()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FootballClubFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (footballClubId != null) {
            viewModel.clubLiveData.observe(viewLifecycleOwner, {club ->
                club?.let {
                    this.footballClub = club

                    binding.nameClubEditText.setText(footballClub.footballClubName)

                    if (footballClub.footballClubLogo.isNotEmpty()) {
                        binding.logoImageView.setImageBitmap(assetsLogoImage.first {
                            it.name == footballClub.footballClubLogo }.bitmap)
                    }
                }
            }
            )
        }
    }

    override fun onStart() {
        super.onStart()

        binding.logoImageView.setOnClickListener {
            LogoPickerFragment().apply {
                setTargetFragment(this@FootballClubFragment, REQUEST_IMAGE)
                show(this@FootballClubFragment.requireFragmentManager(), "Picker")
            }
        }

        binding.buttonSaveClub.setOnClickListener {

            if (binding.nameClubEditText.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Не указано название команды",
                    Toast.LENGTH_SHORT).show()
            } else {
                if (footballClubId != null) {
                    footballClub.footballClubName = binding.nameClubEditText.text.toString()
                    viewModel.saveFootballClub(footballClub)
                } else {
                    footballClub.footballClubName = binding.nameClubEditText.text.toString()
                    viewModel.addFootballClub(footballClub)

                }
                requireActivity().onBackPressed()
            }
        }

        binding.buttonCloseClub.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onImageSelected(imageModel: AssetsImageModel) {
        binding.logoImageView.setImageBitmap(imageModel.bitmap)
        footballClub.footballClubLogo = imageModel.name
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().title = "Listmaker"
    }
}