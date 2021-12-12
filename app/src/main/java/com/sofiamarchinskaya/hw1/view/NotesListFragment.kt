package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.Note
import com.sofiamarchinskaya.hw1.models.NotesModel
import com.sofiamarchinskaya.hw1.presenters.NotesListPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesList

/**
 * Фрагмент для отображения списка заметок
 * */
class NotesListFragment : Fragment(), NotesList {

    private val presenter: NotesListPresenter = NotesListPresenterImpl(this, NotesModel)
    private lateinit var notesList: RecyclerView
    private var notesListAdapter: NotesAdapter =
        NotesAdapter(presenter::onItemClick, this::onMenuCreated, presenter::longClick)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_notes_list, container, false).apply {
        notesList = findViewById(R.id.notes_list)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        notesList.addItemDecoration(dividerItemDecoration)
        presenter.init()
        registerForContextMenu(notesList)
    }

    override fun openAboutItemFragment(note: Note) {
        val infoFragment = NoteInfoFragment()
        infoFragment.arguments =
            bundleOf(Pair(Constants.TITLE, note.title), Pair(Constants.TEXT, note.text))
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.host, infoFragment)?.addToBackStack(TAG)?.commit()
    }

    override fun onMenuCreated(menu: ContextMenu?) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun initAdapter(list: List<Note>) {
        notesListAdapter.update(list)
        notesList.adapter = notesListAdapter
    }

    override fun onShare(dataForExtra: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = Constants.TYPE
            putExtra(Intent.EXTRA_TEXT, dataForExtra)
        })

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                presenter.onShare()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
