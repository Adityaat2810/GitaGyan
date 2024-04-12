package com.example.geetagyan.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.geetagyan.databinding.ItemViewChaptersBinding
import com.example.geetagyan.models.ChaptersItem

class AdapterChapters(
    val onChapterItemView: (ChaptersItem) -> Unit,
    val onDownloadClick: (ChaptersItem) -> Unit
) :RecyclerView.Adapter<AdapterChapters.ChaptersViewHolder>() {

    class ChaptersViewHolder(val binding:ItemViewChaptersBinding):ViewHolder(binding.root)

    // diff util is better approach to show list in recyclerview whenever new data
    // added compare previous and new list
    val diffUtil=object :DiffUtil.ItemCallback<ChaptersItem>(){
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
           return oldItem==newItem
        }

    }

    val differ =AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        return ChaptersViewHolder(ItemViewChaptersBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        val chapter = differ.currentList[position]
        holder.binding.apply {
            number.text ="Chapter ${chapter.chapter_number}"
            name.text =chapter.name_translated
            des.text=chapter.chapter_summary
            verseCount.text=chapter.verses_count.toString()
            // des name number
        }

        holder.binding.ll.setOnClickListener {
            // fuction will be call from here now
            onChapterItemView(chapter)  // passing the current chapter
        }

        holder.binding.apply {
            download.setOnClickListener {
                onDownloadClick(chapter)
            }
        }

        }
    }

