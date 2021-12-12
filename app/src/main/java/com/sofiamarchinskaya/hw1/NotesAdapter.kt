package com.sofiamarchinskaya.hw1

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private var onClick: (Note) -> Unit,
    private var onMenuCreated: (ContextMenu?) -> Unit,
    private var onItemLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var list: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(list: List<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val text = itemView.findViewById<TextView>(R.id.text)

        fun bind(data: Note) {
            title.text = data.title
            text.text = data.text
            itemView.apply {
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

