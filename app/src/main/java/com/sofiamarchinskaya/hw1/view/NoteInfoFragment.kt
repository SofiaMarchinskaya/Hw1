package com.sofiamarchinskaya.hw1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sofiamarchinskaya.hw1.*

/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_note_info, container, false).apply {
        title = findViewById(R.id.title)
        text = findViewById(R.id.text)
        title.text = arguments?.getString(Constants.TITLE)
        text.text = arguments?.getString(Constants.TEXT)
    }
}
