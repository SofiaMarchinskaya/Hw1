package com.sofiamarchinskaya.hw1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.presenters.NotesListPresenter

class NotesListFragment : Fragment(), NotesList {

    private val presenter: NotesListPresenter = NotesListPresenterImpl(this, NotesModel)
    private lateinit var notesList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        notesList = view.findViewById(R.id.notes_list)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        notesList.addItemDecoration(dividerItemDecoration)
        presenter.init()
        registerForContextMenu(notesList)
        return view
    }

    override fun openAboutItemFragment(note: Note) {
        val infoFragment = NoteInfoFragment()
        infoFragment.arguments = Bundle().apply {
            putString(Constants.TITLE, note.title)
            putString(Constants.TEXT, note.text)
        }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.host, infoFragment)?.addToBackStack(TAG)?.commit()
    }

    override fun onMenuCreated(menu: ContextMenu?) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun initAdapter(list: List<Note>) {
        notesList.adapter = NotesAdapter(list, this::openAboutItemFragment, this::onMenuCreated)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (notesList.adapter as NotesAdapter).pos
        when (item.itemId) {
            R.id.share -> {
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    type = Constants.TYPE
                    putExtra(Intent.EXTRA_TEXT, presenter.getDataToExtra(pos))
                })
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        const val TAG = "NotesList"
    }
}
