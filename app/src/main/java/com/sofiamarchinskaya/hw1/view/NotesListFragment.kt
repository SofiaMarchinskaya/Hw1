package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNotesListBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.viewmodels.NotesListViewModel
import kotlinx.coroutines.launch

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
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))
        binding = FragmentNotesListBinding.inflate(inflater, container, false).apply {
            fab.setOnClickListener {
                openAddNoteFragment()
            }
        }
        notesListAdapter =
            NotesAdapter(
                requireContext(),
                this::openAboutItemActivity,
                this::onMenuCreated,
                viewModel::longClick
            )
        binding.notesList.adapter = notesListAdapter
        binding.notesList.addItemDecoration(dividerItemDecoration)
        viewModel.list.observe(this) {
            notesListAdapter.update(it)
        }
        registerForContextMenu(binding.notesList)
        activity?.invalidateOptionsMenu()
        lifecycleScope.launch {
            viewModel.updateNotesList()
        }
        return binding.root
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

    private fun onMenuCreated(menu: ContextMenu?) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    private fun onShare(dataForExtra: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = Constants.TYPE
            putExtra(Intent.EXTRA_TEXT, dataForExtra)
        })

    }

    private fun openAboutItemActivity(note: Note) {
        startActivity(NotesPagerActivity.getStartIntent(requireContext(), note))
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
