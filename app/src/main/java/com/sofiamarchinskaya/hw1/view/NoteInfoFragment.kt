package com.sofiamarchinskaya.hw1.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.os.bundleOf
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NoteInfoPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NoteInfoPresenter
import com.sofiamarchinskaya.hw1.view.framework.NoteInfoView

/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment(),NoteInfoView {

    private lateinit var title: TextView
    private lateinit var text: TextView
    private var noteId =Constants.INVALID_ID
    private var isNewNote = false
    private lateinit var presenter:NoteInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_note_info, container, false).apply {
        title = findViewById(R.id.title)
        text = findViewById(R.id.text)
        title.text = arguments?.getString(Constants.TITLE)
        text.text = arguments?.getString(Constants.TEXT)
        noteId = arguments?.getLong(Constants.ID) ?: Constants.INVALID_ID
        presenter =NoteInfoPresenterImpl(this@NoteInfoFragment)
        if (arguments == null){
            activity?.invalidateOptionsMenu()
            isNewNote = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (isNewNote)
        inflater.inflate(R.menu.menu_add, menu)
    }

    companion object{
        fun newInstance(note: Note): NoteInfoFragment {
            val args = bundleOf(Constants.TITLE to note.title, Constants.TEXT to note.body,Constants.ID to note.id)
            val fragment = NoteInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSaveComplete() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                presenter.onSaveNote(title.text.toString(),text.text.toString(),noteId)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
