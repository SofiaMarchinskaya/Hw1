package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.FragmentNoteInfoBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
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
        val dialogFragment = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.dialog_title))
            setMessage(getString(R.string.dialog_message))
            setNegativeButton(
                getString(R.string.dialog_negative),
                null
            )
            setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                lifecycleScope.launch {
                    viewModel.onSaveNote()
                }
            }
            create()
        }
        activity?.supportFragmentManager?.let { dialogFragment.show() }
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
