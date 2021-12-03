package com.sofiamarchinskaya.hw1

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val list: List<Note>,
    private val onClick: (Note) -> Unit,
    private val onMenuCreated: (ContextMenu?) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val text = itemView.findViewById<TextView>(R.id.text)

        fun bind(data: Note) {
            title.text = data.title
            text.text = data.text
            itemView.setOnClickListener { onClick.invoke(data) }
            itemView.setOnCreateContextMenuListener { menu, _, _ -> onMenuCreated.invoke(menu) }
            itemView.setOnLongClickListener {
                pos = this.adapterPosition
                false
            }
        }
    }
}

