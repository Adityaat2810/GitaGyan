package com.example.geetagyan.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.geetagyan.databinding.VersesItemBinding

class AdapterVerses(val onVerseItemClick: (String, Int) -> Unit) :RecyclerView.Adapter<AdapterVerses.VerseViewHolder>() {
    class VerseViewHolder(val binding:VersesItemBinding):ViewHolder(binding.root)


    val diffUtil =object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
           return oldItem == newItem
        }

    }

    val differ =AsyncListDiffer(this , diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseViewHolder {
        return VerseViewHolder(VersesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        val verse = differ.currentList[position]
        holder.binding.name.text = "VERSE ${position+1}"
        holder.binding.des.text = verse

        holder.binding.ll.setOnClickListener {
            onVerseItemClick(verse , position+1)
        }
    }
}