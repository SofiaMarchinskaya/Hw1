package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sofiamarchinskaya.hw1.utils.BackupWorker
import com.sofiamarchinskaya.hw1.types.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNotesListBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.types.*
import com.sofiamarchinskaya.hw1.viewmodels.NotesListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

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
        setupWorker()
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
        setupSearch(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSearch(menu: Menu?) {
        (menu?.findItem(R.id.search_note)
            ?.actionView as SearchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.filter(newText)
                    return false
                }
            })
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
        with(viewModel) {
            onNoteItemClickEvent.observe(viewLifecycleOwner) {
                openAboutItemActivity(it)
            }
            onFabClickEvent.observe(viewLifecycleOwner) {
                openAddNoteFragment()
            }
            onLoadSuccessEvent.observe(viewLifecycleOwner) {
                makeToast(resources.getString(R.string.successfully_download))
            }
            onLoadFailureEvent.observe(viewLifecycleOwner) {
                makeToast(chooseExceptionMessage(it))
            }
            onShowProgressBarEvent.observe(viewLifecycleOwner) {
                showProgressBar()
            }
            onHideProgressBarEvent.observe(viewLifecycleOwner) {
                hideProgressBar()
            }
        }
    }

    private fun setupWorker() {
        val workerRequest =
            PeriodicWorkRequestBuilder<BackupWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireActivity().applicationContext)
            .enqueueUniquePeriodicWork(
                BackupWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workerRequest
            )
    }

    companion object {
        private const val TAG = "NotesList"
    }
}

