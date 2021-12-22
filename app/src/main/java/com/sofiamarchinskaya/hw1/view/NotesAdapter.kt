package com.sofiamarchinskaya.hw1.view

import android.content.Context
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.NoteItemBinding
import com.sofiamarchinskaya.hw1.models.entity.Note

class NotesAdapter(
    context: Context,
    private var onClick: (Note) -> Unit,
    private var onMenuCreated: (ContextMenu?) -> Unit,
    private var onItemLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var list: List<Note> = ArrayList()
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            NoteItemBinding.inflate(inflater, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(list: List<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Note) {
            binding.title.text = data.title
            binding.text.text = data.body
            binding.root.apply {
                setOnClickListener { onClick.invoke(data) }
                setOnCreateContextMenuListener { menu, _, _ -> onMenuCreated.invoke(menu) }
                setOnLongClickListener {
                    onItemLongClick.invoke(data)
                    false
                }
            }
        }
    }
}

