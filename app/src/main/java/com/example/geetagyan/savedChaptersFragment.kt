package com.example.geetagyan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.geetagyan.databinding.FragmentSavedChaptersBinding
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.view.adapters.AdapterChapters
import com.example.geetagyan.viewmodel.MainViewModel

class savedChaptersFragment : Fragment() {

    private lateinit var binding: FragmentSavedChaptersBinding
    private lateinit var adapterChapters: AdapterChapters
    private val viewModel:MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedChaptersBinding.inflate(layoutInflater)

        getSavedChapters()

        return binding.root
    }

    private fun getSavedChapters() {
        viewModel.getSavedChapters().observe(viewLifecycleOwner){

            val chapterList = arrayListOf<ChaptersItem>()

            for (i in it ){
                val chaptersItem = ChaptersItem(
                    i.chapter_number,
                    i.chapter_summary,
                    i.chapter_summary_hindi,
                    i.id,
                    i.name,
                    i.name_meaning,
                    i.name_translated,
                    i.name_transliterated,
                    i.slug,
                    i.verses_count
                )

                chapterList.add(chaptersItem)
            }

            if(chapterList.isEmpty()){
                binding.shimmerLayout.visibility =View.GONE
                binding.chaptersRV.visibility =View.GONE
            }

            adapterChapters = AdapterChapters(::onChapterItemViewClicked,::onFavourateClicked,false)
            binding.chaptersRV.adapter =adapterChapters
            adapterChapters.differ.submitList(chapterList)

            binding.chaptersRV.visibility =View.VISIBLE
            binding.shimmerLayout.visibility = View.GONE
        }

    }

    fun onChapterItemViewClicked(chaptersItem: ChaptersItem){
        val bundle =Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putBoolean("showRoomData",true)
        findNavController().navigate(R.id.action_savedChaptersFragment_to_verses_fragment,bundle)


    }

    fun onFavourateClicked(chaptersItem: ChaptersItem){

    }


}