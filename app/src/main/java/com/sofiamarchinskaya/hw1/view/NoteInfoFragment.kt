package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNoteInfoBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.JsonLoadingState
import com.sofiamarchinskaya.hw1.states.JsonLoadingStates
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States
import com.sofiamarchinskaya.hw1.viewmodels.NoteInfoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment() {

    private lateinit var binding: FragmentNoteInfoBinding
    private val viewModel: NoteInfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteInfoBinding.inflate(inflater, container, false)
        arguments?.apply {
            viewModel.note.value =
                Note(getInt(Constants.ID), getString(Constants.TITLE), getString(Constants.TEXT))
        } ?: run {
            activity?.invalidateOptionsMenu()
            viewModel.note.value = Note(
                Constants.INVALID_ID,
                arguments?.getString(Constants.TITLE),
                arguments?.getString(Constants.TEXT)
            )
            viewModel.isNewNote = true
        }
        initLiveData()

        viewModel.onSaveSuccessEvent.observe(viewLifecycleOwner) {
            onSuccessfullySaved()
        }
        viewModel.onSaveAllowedEvent.observe(viewLifecycleOwner) {
            createSaveDialog()
        }
        viewModel.onSaveFailureEvent.observe(viewLifecycleOwner) {
            onSaveDisabled()
        }
        viewModel.note.observe(viewLifecycleOwner) {
            binding.title.setText(viewModel.note.value?.title)
            binding.text.setText(viewModel.note.value?.body)
        }
        binding.text.addTextChangedListener {
            viewModel.setNoteText(it.toString())
        }
        binding.title.addTextChangedListener {
            viewModel.setNoteTitle(it.toString())
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (viewModel.isNewNote)
            inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                val id = viewModel.note.value?.id
                viewModel.note.value = id?.let {
                    Note(
                        it,
                        binding.title.text.toString(),
                        binding.text.text.toString()
                    )
                }
                viewModel.checkNote()
                return true
            }
            R.id.load_json -> {
                viewModel.getJsonNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSaveDisabled() {
        Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
    }

    private fun onSuccessfullySaved() {
        Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    private fun createSaveDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val checkBoxView = layoutInflater.inflate(R.layout.check_box, null)
        val checkBox = checkBoxView.findViewById<CheckBox>(R.id.checkbox)
        val dialogFragment = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.dialog_title))
            setView(checkBoxView)
            setNegativeButton(
                getString(R.string.dialog_negative),
                null
            )
            setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                lifecycleScope.launch {
                    viewModel.onSaveNote(checkBox.isChecked)
                }
            }
            create()
        }
        activity?.supportFragmentManager?.let { dialogFragment.show() }
    }

    private fun setLoadedNote(note: Note) {
        binding.title.setText(note.title)
        binding.text.setText(note.body)
    }

    private fun initLiveData() {
        viewModel.savingState.observe(viewLifecycleOwner) {
            observeSavingState(it)
        }
        viewModel.noteFromJson.observe(viewLifecycleOwner) {
            observeNoteFromJson(it)
        }
    }

    private fun observeSavingState(savingState: SavingState) {
        when (savingState.state) {
            States.SAVED -> onSuccessfullySaved()
            States.ERROR -> onSaveDisabled()
            States.ALLOWED -> createSaveDialog()
            States.NOTHING -> {}//Nothing
        }
    }

    private fun observeNoteFromJson(jsonLoadingState: JsonLoadingState) {
        when (jsonLoadingState.state) {
            JsonLoadingStates.SUCCESS -> {
                jsonLoadingState.note?.let { note -> setLoadedNote(note) }
                makeToast(jsonLoadingState.state)
            }
            JsonLoadingStates.FAILED -> makeToast(jsonLoadingState.state)
            JsonLoadingStates.LOADING -> showProgressBar()
            JsonLoadingStates.FINISH -> hideProgressBar()
        }
    }

    private fun makeToast(state: JsonLoadingStates) {
        val msg =
            if (state == JsonLoadingStates.SUCCESS) resources.getString(R.string.successfully_download)
            else resources.getString(R.string.failed)
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.title.visibility = View.VISIBLE
        binding.text.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.title.visibility = View.INVISIBLE
        binding.text.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(note: Note): NoteInfoFragment =
            NoteInfoFragment().apply {
                arguments = bundleOf(
                    Constants.TITLE to note.title,
                    Constants.TEXT to note.body,
                    Constants.ID to note.id
                )
            }
    }
}