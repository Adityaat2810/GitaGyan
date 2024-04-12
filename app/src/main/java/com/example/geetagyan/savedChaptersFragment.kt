package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geetagyan.databinding.FragmentSavedChaptersBinding
import com.example.geetagyan.databinding.FragmentSettingsBinding

class savedChaptersFragment : Fragment() {

    private lateinit var binding: FragmentSavedChaptersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_saved_chapters, container, false)
    }


}