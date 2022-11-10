package com.dima_shpiklirnyi.testingwork.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.R
import com.dima_shpiklirnyi.testingwork.Screens.NotesList.NotesListFragment
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time
import com.dima_shpiklirnyi.testingwork.databinding.ItemNotesBinding

class AdapterListNotes : ListAdapter<NotesModel, AdapterListNotes.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNotesBinding.bind(view)
        fun bind(item: NotesModel, time: Time) {
            binding.notesTitle.text = item.title
            binding.noteDescription.text = item.description
            binding.noteTime.text = time.setTime(item.time)
        }

    }
    class Comparator : DiffUtil.ItemCallback<NotesModel>() {
        override fun areItemsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), time = Time())
    }

    override fun onViewAttachedToWindow(holder: Holder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { NotesListFragment.onClick( getItem(holder.adapterPosition) ) }
    }

    override fun onViewDetachedFromWindow(holder: Holder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener (null)
    }
}