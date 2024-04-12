package com.example.geetagyan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.geetagyan.databinding.FragmentVerseDetailBinding
import com.example.geetagyan.models.Commentary
import com.example.geetagyan.models.Translation
import com.example.geetagyan.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import retrofit2.http.GET

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
        checkInternetConnectivity()
        return binding.root

    }

    private fun getVerseDetails() {
        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNumber,verseNumber).collect{verse->
                binding.sanskritText.text = verse.text
                binding.englishText.text =verse.transliteration
                binding.englishMeaning.text = verse.word_meanings
                Log.d("API",verse.toString())

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

                val englishCommentryList = arrayListOf<Commentary>()
                for(i in verse.commentaries){
                    if(i.language =="hindi" || i.language=="english"){
                        englishCommentryList.add(i)
                    }
                }
                val englishCommentryListSize = englishCommentryList.size
                if(englishCommentryList.isNotEmpty()){
                    binding.authorCommentary.text = englishCommentryList[0].author_name
                    binding.commentary.text = englishCommentryList[0].description

                    if(englishCommentryListSize ==1){
                        binding.nextCommentary.visibility =View.GONE
                    }

                    var i = 0
                    binding.nextCommentary.setOnClickListener {
                        if(i <englishCommentryListSize -1){
                            i++
                            binding.authorCommentary.text ="-by ${englishCommentryList[i].author_name}"
                            binding.commentary.text = englishCommentryList[i].description
                            binding.prevCommentary.visibility = View.VISIBLE

                            if(i == englishCommentryListSize-1){
                                binding.nextCommentary.visibility =View.GONE
                            }
                        }
                    }


                    binding.prevCommentary.setOnClickListener {
                        if(i>0){
                            i--
                            binding.authorCommentary.text ="-by ${englishCommentryList[i].author_name}"
                            binding.commentary.text = englishCommentryList[i].description
                            binding.nextCommentary.visibility =View.VISIBLE

                            if(i == 0){
                                binding.prevCommentary.visibility =View.GONE
                            }
                        }
                    }

                }
                binding.progressIndicator.visibility=View.GONE
                binding.tv.visibility =View.GONE
                binding.linearLayout.visibility=View.VISIBLE

            }
        }
    }

    private fun checkInternetConnectivity() {
        val networkManager =NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it ==true){
                getVerseDetails()
            }else{
                binding.progressIndicator.visibility=View.VISIBLE
                binding.linearLayout.visibility=View.GONE
                binding.tv.visibility =View.GONE
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