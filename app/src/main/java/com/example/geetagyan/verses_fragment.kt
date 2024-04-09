package com.example.geetagyan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.geetagyan.databinding.FragmentVersesFragmentBinding
import com.example.geetagyan.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class verses_fragment : Fragment() {

    private lateinit var binding:FragmentVersesFragmentBinding
    private val viewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersesFragmentBinding.inflate(layoutInflater)

        getAllVerses()
        return binding.root
    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            viewModel.getVerses(4).collect{
                for(i in it ){
                    Log.d("TAg",i.toString())
                }
            }
        }

    }

}