package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.geetagyan.databinding.FragmentHomeBinding
import com.example.geetagyan.datasource.room.savedChapters
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.view.adapters.AdapterChapters
import com.example.geetagyan.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    val viewmodel : MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentHomeBinding.inflate(layoutInflater)

        checkInternetConnectivity()
        changeStatusBarColor()

        binding.ivSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }


        return binding.root
    }

    private fun checkInternetConnectivity() {
        val networkManager =NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it ==true){
                binding.shimmerLayout.visibility =View.VISIBLE
                //binding.chaptersRV.visibility =View.VISIBLE
                getAllChapters()


            }else{

                binding.shimmerLayout.visibility =View.GONE
                binding.chaptersRV.visibility =View.GONE

                /*
                * show something here like please turn your internet on
                * */

            }

        }
    }

    private fun getAllChapters() {
        lifecycleScope.launch {
            viewmodel.getAllChapters().collect{chapterList->
                adapterChapters = AdapterChapters(::onChapterItemView, ::onDownloadClick, true)  // passing function as constructor to adapter
                binding.chaptersRV.adapter=adapterChapters
                adapterChapters.differ.submitList(chapterList)
                binding.shimmerLayout.visibility=View.GONE
                binding.chaptersRV.visibility=View.VISIBLE
            }
        }

    }


    private fun onChapterItemView(chaptersItem: ChaptersItem){

        val bundle = Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putString("chapterTitle",chaptersItem.name_translated)
        bundle.putString("chapterDes",chaptersItem.chapter_summary)
        bundle.putInt("versesCount",chaptersItem.verses_count)

        findNavController().navigate(R.id.action_homeFragment_to_verses_fragment,bundle)

    }

    fun onDownloadClick(chaptersItem: ChaptersItem){

        lifecycleScope.launch {
            viewmodel.getVerses(chaptersItem.chapter_number).collect{
                val verseList = arrayListOf<String>()

                for(currentVerse in it ){
                    for (verse in currentVerse.translations){
                        if(verse.language == "english"){
                            verseList.add(verse.description)
                            break
                        }
                    }
                }

                val savedchapters =savedChapters(
                    chapter_number = chaptersItem.chapter_number,
                    chapter_summary = chaptersItem.chapter_summary,
                    chapter_summary_hindi = chaptersItem.chapter_summary_hindi,
                    id=chaptersItem.id,
                    name = chaptersItem.name,
                    name_meaning = chaptersItem.name_meaning,
                    name_translated = chaptersItem.name_translated,
                    name_transliterated = chaptersItem.name_transliterated,
                    slug =chaptersItem.slug,
                    verses_count = chaptersItem.verses_count,
                    verses = verseList
                    )

                viewmodel.insertChapters(savedchapters)

            }
        }



    }
    private fun changeStatusBarColor() {

        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.splash)
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }
}