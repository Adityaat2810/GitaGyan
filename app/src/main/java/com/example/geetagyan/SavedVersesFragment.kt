package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.geetagyan.databinding.FragmentSavedVersesBinding
import com.example.geetagyan.view.adapters.AdapterVerses
import com.example.geetagyan.viewmodel.MainViewModel

class SavedVersesFragment : Fragment() {

    private lateinit var binding: FragmentSavedVersesBinding
    private val viewModel:MainViewModel by viewModels()
    private lateinit var adapterVerse:AdapterVerses

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedVersesBinding.inflate(layoutInflater)
        getVersesFromRoom()
        return binding.root


    }

    private fun getVersesFromRoom() {
        viewModel.getSavedVerses().observe(viewLifecycleOwner){
            val verseList = arrayListOf<String>()
            for (i in it){
                verseList.add(i.translations[0].description)
            }

            if(verseList.isEmpty()){
                binding.shimmerLayout.visibility =View.GONE
            }

            adapterVerse = AdapterVerses(::onVerseItemClicked,true)
            binding.versesRv.adapter = adapterVerse
            adapterVerse.differ.submitList(verseList)
            binding.shimmerLayout.visibility = View.GONE
            binding.versesRv.visibility =View.VISIBLE
        }

    }


    private fun onVerseItemClicked(verse: String, verseNumber: Int) {

    }

}
