package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.models.NoteModelImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NotesListPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesListView

/**
 * Фрагмент для отображения списка заметок
 */
class NotesListFragment : Fragment(), NotesListView {

    private lateinit var presenter: NotesListPresenter
    private lateinit var notesList: RecyclerView
    private lateinit var notesListAdapter: NotesAdapter
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_notes_list, container, false).apply {
        presenter = NotesListPresenterImpl(NoteModelImpl(), this@NotesListFragment, lifecycleScope)
        notesList = findViewById(R.id.notes_list)
        addButton = findViewById(R.id.fab)
        addButton.setOnClickListener {
            presenter.addNote()
        }
        notesListAdapter =
            NotesAdapter(
                requireContext(),
                presenter::onItemClick,
                this@NotesListFragment::onMenuCreated,
                presenter::longClick
            )
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        notesList.addItemDecoration(dividerItemDecoration)
        presenter.init()
        registerForContextMenu(notesList)
        activity?.invalidateOptionsMenu()
    }

    override fun openAboutItemActivity(note: Note) {
        startActivity(NotesPagerActivity.getStartIntent(requireContext(), note))
    }

    override fun onMenuCreated(menu: ContextMenu?) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun initAdapter(list: List<Note>) {
        notesListAdapter.update(list)
        notesList.adapter = notesListAdapter
    }

    override fun update(list: List<Note>) {
        notesListAdapter.update(list)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun openAddNoteFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.host, NoteInfoFragment())?.addToBackStack(TAG)?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
