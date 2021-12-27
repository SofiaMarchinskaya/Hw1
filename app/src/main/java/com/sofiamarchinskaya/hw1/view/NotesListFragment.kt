package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNotesListBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NotesListViewModel


/**
 * Фрагмент для отображения списка заметок
 */
class NotesListFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[NotesListViewModel::class.java] }
    private lateinit var notesListAdapter: NotesAdapter
    private lateinit var binding: FragmentNotesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            openAddNoteFragment()
        }
        notesListAdapter =
            NotesAdapter(
                requireContext(),
                this::openAboutItemActivity,
                this::onMenuCreated,
                viewModel::longClick
            )
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        binding.notesList.addItemDecoration(dividerItemDecoration)
        binding.notesList.adapter = notesListAdapter
        viewModel.list.observe(this) {
            notesListAdapter.update(it)
        }
        registerForContextMenu(binding.notesList)
        activity?.invalidateOptionsMenu()
        return binding.root
    }


    private fun openAboutItemActivity(note: Note) {
        val intent = Intent(context, NotesPagerActivity::class.java).apply {
            putExtra(Constants.TITLE, note.title)
            putExtra(Constants.TEXT, note.body)
            putExtra(Constants.ID, note.id)
        }
        startActivity(intent)
    }

    private fun onMenuCreated(menu: ContextMenu?) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    private fun onShare(dataForExtra: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = Constants.TYPE
            putExtra(Intent.EXTRA_TEXT, dataForExtra)
        })

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                onShare(viewModel.getDataToExtra())
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun openAddNoteFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.host, NoteInfoFragment())?.addToBackStack(TAG)?.commit()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateNotesList()
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
