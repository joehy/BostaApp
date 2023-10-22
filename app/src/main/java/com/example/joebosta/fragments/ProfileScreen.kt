package com.example.joebosta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.joebosta.R
import com.example.joebosta.adabter.AlbumAdapter
import com.example.joebosta.databinding.FragmentProfileScreenBinding
import com.example.joebosta.models.Album
import com.example.joebosta.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ProfileScreen : Fragment() {
    @Inject lateinit var albumAdapter: AlbumAdapter
    private val viewModel: UserProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using data binding
        val binding = DataBindingUtil.inflate<FragmentProfileScreenBinding>(
            inflater,
            R.layout.fragment_profile_screen,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.fetchUserWithAlbums(1)
        viewModel.userData.observe(viewLifecycleOwner) {
            binding.userName.text=it.name
            binding.userAddress.text = it.address.let { address ->
                "${address.city}, ${address.street}, ${address.zipcode}"
            }

        }

        binding.albumsRecyclerView.adapter=albumAdapter
        binding.albumsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        albumAdapter.setOnItemClickListener(::onAlbumClicked)
        viewModel.albumListData.observe(viewLifecycleOwner) {
            if (it != null) {
                albumAdapter.submitList(it)
                Timber.v("album Adapter itemCount : {${albumAdapter.itemCount}")
            } else {
                Timber.v("album list is empty")
            }
        }

        return binding.root
    }

    private fun onAlbumClicked(album: Album) {
        val action = ProfileScreenDirections.actionProfileScreenToAlbumdDetailsScreen2(album.id,album.title)
        findNavController().navigate(action)

    }
}
