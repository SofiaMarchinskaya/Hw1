package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNotesListBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.*
import com.sofiamarchinskaya.hw1.viewmodels.NotesListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка заметок
 */
class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModel()
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
                viewModel.onFabClicked()
            }
        }
        initEvents()
        notesListAdapter =
            NotesAdapter(
                requireContext(),
                viewModel::onAboutItemClicked,
                ::onMenuCreated,
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
        when (item.itemId) {
            R.id.share -> {
                viewModel.onShareContextItemClick()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.launch_from_cloud -> {
                viewModel.getNotesFromCloud()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        with(viewModel) {
            list.observe(viewLifecycleOwner) {
                notesListAdapter.update(it)
            }
            contextMenuState.observe(viewLifecycleOwner) {
                onShare(it)
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun hideProgressBar() {
        binding.notesList.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.notesList.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun chooseExceptionMessage(exceptionType: ExceptionTypes?): String =
        if (exceptionType == ExceptionTypes.CLIENT_IS_OFFLINE)
            resources.getString(R.string.fail_to_connect)
        else
            resources.getString(R.string.problems_with_cloud)

    private fun initEvents() {
        viewModel.onNoteItemClickEvent.observe(viewLifecycleOwner) {
            openAboutItemActivity(it)
        }
        viewModel.onFabClickEvent.observe(viewLifecycleOwner) {
            openAddNoteFragment()
        }
        viewModel.onLoadSuccessEvent.observe(viewLifecycleOwner) {
            makeToast(resources.getString(R.string.successfully_download))
        }
        viewModel.onLoadFailureEvent.observe(viewLifecycleOwner) {
            makeToast(chooseExceptionMessage(it))
        }
        viewModel.onShowProgressBarEvent.observe(viewLifecycleOwner) {
            showProgressBar()
        }
        viewModel.onHideProgressBarEvent.observe(viewLifecycleOwner) {
            hideProgressBar()
        }
    }

    companion object {
        private const val TAG = "NotesList"
    }
}
