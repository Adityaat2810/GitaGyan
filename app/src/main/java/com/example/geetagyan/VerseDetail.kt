package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.geetagyan.databinding.FragmentVerseDetailBinding
import com.example.geetagyan.models.Translation
import com.example.geetagyan.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class VerseDetail : Fragment() {

    private lateinit var binding: FragmentVerseDetailBinding
    private val viewModel:MainViewModel by viewModels()
    var chapterNumber = 0
    var verseNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseDetailBinding.inflate(layoutInflater)
        getAndSetChapterAndVerseNumber()
        getVerseDetails()
        return binding.root

    }

    private fun getVerseDetails() {
        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNumber,verseNumber).collect{verse->
                binding.sanskritText.text = verse.text
                binding.englishText.text =verse.transliteration
                binding.englishMeaning.text = verse.word_meanings

                val enslishTransaltionList = arrayListOf<Translation>()
                for(i in verse.translations){
                    if(i.language =="english"){
                        enslishTransaltionList.add(i)
                    }
                }

                val englishTranslationSize = enslishTransaltionList.size
                if(enslishTransaltionList.isNotEmpty()){
                    binding.authorTranslation.text ="-by ${enslishTransaltionList[0].author_name}"
                    binding.translation.text = enslishTransaltionList[0].description
                    if(englishTranslationSize ==1) binding.nextTransaltion.visibility =View.GONE

                    var i =0 ;

                    binding.nextTransaltion.setOnClickListener {
                        if(i <englishTranslationSize -1){
                            i++
                            binding.authorTranslation.text ="-by ${enslishTransaltionList[i].author_name}"
                            binding.translation.text = enslishTransaltionList[i].description
                            binding.prevTranslation.visibility = View.VISIBLE

                            if(i == englishTranslationSize-1){
                                binding.nextTransaltion.visibility =View.GONE
                            }
                        }
                    }


                    binding.prevTranslation.setOnClickListener {
                        if(i>0){
                            i--
                            binding.authorTranslation.text ="-by ${enslishTransaltionList[i].author_name}"
                            binding.translation.text = enslishTransaltionList[i].description
                            binding.nextTransaltion.visibility =View.VISIBLE

                            if(i == 0){
                                binding.prevTranslation.visibility =View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAndSetChapterAndVerseNumber() {
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNum")!!
        verseNumber= bundle?.getInt("verseNum")!!

        binding.chNumber.text = "BG ${chapterNumber}.${verseNumber}"
    }

}