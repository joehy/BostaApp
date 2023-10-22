package com.example.joebosta.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.joebosta.MainActivity
import com.example.joebosta.R
import com.example.joebosta.adabter.ImageAdapter
import com.example.joebosta.databinding.FragmentAlbumdDetailsScreenBinding
import com.example.joebosta.models.Photo
import com.example.joebosta.viewmodel.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlbumdDetailsScreen : Fragment() {
    @Inject lateinit var imageAdapter: ImageAdapter
    private val viewModel: AlbumViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentAlbumdDetailsScreenBinding>(
            inflater,
            R.layout.fragment_albumd_details_screen,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.imagesRecyclerView.adapter=imageAdapter

        val albumTitle= arguments?.getString("album_title")
        if (albumTitle != null) {
            val actionBar = (requireActivity() as MainActivity).supportActionBar  // Replace YourActivity with your actual activity
            actionBar?.title = albumTitle
        }
        val albumId= arguments?.getInt("album_id")
        if (albumId != null) {
            viewModel.fetchAlbumImages(albumId)

        }
        binding?.searchBar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this example
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
               if (searchText.isEmpty()){
                   viewModel.fetchAlbumImages(albumId!!)
               }else{
                   viewModel.searchWithTitle(searchText)
               }

            }
            override fun afterTextChanged(s: Editable?) {
                // Not needed in this example
            }
        })
        imageAdapter.setOnItemClickListener(::onImageClicked)


        viewModel.displayedImages.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
            imageAdapter.differ.submitList(it)
            else
                Timber.v("album images list is empty")

        }


        return binding.root
    }
    private fun onImageClicked(photo: Photo) {

    }


}