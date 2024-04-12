package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.geetagyan.databinding.FragmentVersesFragmentBinding
import com.example.geetagyan.view.adapters.AdapterVerses
import com.example.geetagyan.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class verses_fragment : Fragment() {

    private lateinit var binding:FragmentVersesFragmentBinding
    private val viewModel:MainViewModel by viewModels()
    private lateinit var versesAdapter:AdapterVerses
    private var chapterNumber  = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersesFragmentBinding.inflate(layoutInflater)
        getAndSetChapterDetails()
        onReadMoreClick()

        getData()

        return binding.root
    }

    private fun getData() {
        val bundle =arguments
        val showDataFromRoom = bundle?.getBoolean("showRoomData",false)

        if(showDataFromRoom == true){
            getDataFromRoom()
        }else{
            checkInternetConnectivity()
        }

    }

    private fun getDataFromRoom() {
        viewModel.getParticularChapter(chapterNumber).observe(viewLifecycleOwner){
            binding.chNumber.text = "Chapter ${it.chapter_number}"
            binding.chName.text = it.name_translated
            binding.chDes.text =it.chapter_summary
            binding.verseCount.text = it.verses_count.toString()

            showListInAdapter(it.verses!!)

        }
    }

    private fun onReadMoreClick() {
        var isExpanded = false
        binding.readMore.setOnClickListener {
            if(!isExpanded){
                binding.chDes.maxLines =50
                isExpanded =true
                binding.readMore.text ="Read less"
            }else{
                binding.chDes.maxLines =4
                isExpanded =false
                binding.readMore.text ="Read more"
            }

        }
    }

    private fun getAndSetChapterDetails() {
        // this line ,must remain in onCreate
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!
        binding.chNumber.text = "Chapter ${bundle?.getInt("chapterNumber")}"
        binding.chName.text = bundle?.getString("chapterTitle")
        binding.chDes.text = bundle?.getString("chapterDes")
        binding.verseCount.text ="Verse Count ${bundle?.getInt("versesCount")}"

    }

    private fun onVerseItemViewClick(verse:String,verseNumber:Int){
        val bundle = Bundle()
        bundle.putInt("chapterNum",chapterNumber)
        bundle.putInt("verseNum",verseNumber)

        findNavController().navigate(R.id.action_verses_fragment_to_verseDetail,bundle)

    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            viewModel.getVerses(chapterNumber).collect{

                val verseList = arrayListOf<String>()

                for(currentVerse in it ){
                    for (verse in currentVerse.translations){
                        if(verse.language == "english"){
                            verseList.add(verse.description)
                            break
                        }
                    }
                }

                showListInAdapter(verseList)


            }
        }

    }

    private fun showListInAdapter(verseList: List<String>) {
        versesAdapter = AdapterVerses(::onVerseItemViewClick)
        binding.versesRv.adapter = versesAdapter

        versesAdapter.differ.submitList(verseList)
        binding.shimmerLayout.visibility=View.GONE
        binding.versesRv.visibility=View.VISIBLE

    }

    private fun checkInternetConnectivity() {
        val networkManager =NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it ==true){
                binding.shimmerLayout.visibility =View.VISIBLE
                //binding.chaptersRV.visibility =View.VISIBLE
                getAllVerses()


            }else{

                binding.shimmerLayout.visibility =View.GONE
                binding.versesRv.visibility =View.GONE

                /*
                * show something here like please turn your internet on
                * */

            }

        }
    }

}