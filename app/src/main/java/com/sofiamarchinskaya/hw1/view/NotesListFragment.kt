package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNotesListBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.FabState
import com.sofiamarchinskaya.hw1.states.FabStates
import com.sofiamarchinskaya.hw1.viewmodels.NotesListViewModel
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка заметок
 */
class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModel()
    private lateinit var notesListAdapter: NotesAdapter
    private lateinit var binding: FragmentNotesListBinding
    private lateinit var fabObserver: Observer<FabState>

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
                viewModel.onFabClicked()
            }
        }
        notesListAdapter =
            NotesAdapter(
                requireContext(),
                this::openAboutItemActivity,
                this::onMenuCreated,
                viewModel::longClick
            )
        with(binding) {
            notesList.adapter = notesListAdapter
            notesList.addItemDecoration(dividerItemDecoration)
        }
        registerForContextMenu(binding.notesList)
        activity?.invalidateOptionsMenu()
        lifecycleScope.launch {
            viewModel.updateNotesList()
        }
        initLiveData()
        return binding.root
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        viewModel.selectContextMenuItem(item.itemId)
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun openAddNoteFragment() =
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.host, NoteInfoFragment())?.addToBackStack(TAG)?.commit()

    private fun onMenuCreated(menu: ContextMenu?) =
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)

    private fun onShare(dataForExtra: String) =
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = Constants.TYPE
            putExtra(Intent.EXTRA_TEXT, dataForExtra)
        })

    private fun openAboutItemActivity(note: Note) =
        startActivity(NotesPagerActivity.getStartIntent(requireContext(), note))

    private fun initLiveData() {
        fabObserver = Observer {
            when (it.state) {
                FabStates.OnClicked -> openAddNoteFragment()
                FabStates.NotClicked -> {}
            }
        }
        viewModel.apply {
            fabState.observe(this@NotesListFragment, fabObserver)
            list.observe(this@NotesListFragment) {
                notesListAdapter.update(it)
            }
            contextMenuState.observe(this@NotesListFragment) {
                onShare(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.fabState.removeObserver(fabObserver)
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
