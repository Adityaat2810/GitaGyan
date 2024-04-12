package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geetagyan.databinding.FragmentSettingsBinding

class settingsFragment : Fragment() {
    private lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =FragmentSettingsBinding.inflate(layoutInflater)

        binding.savedChapters.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedChaptersFragment)
        }

        binding.savedVerses.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedVersesFragment)
        }

        return binding.root
    }


}