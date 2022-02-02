package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        initEvents()
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

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
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

    private fun initEvents() {
        viewModel.onSaveSuccessEvent.observe(viewLifecycleOwner) {
            it?.let {
                onSuccessfullySaved()
                activity?.sendBroadcast(Intent().apply {
                    action = Constants.NOTE_SENT
                    putExtra(Constants.TITLE, it.title)
                    putExtra(Constants.TEXT, it.body)
                })
            }
        }
        viewModel.onSaveAllowedEvent.observe(viewLifecycleOwner) {
            createSaveDialog()
        }
        viewModel.onSaveFailureEvent.observe(viewLifecycleOwner) {
            onSaveDisabled()
        }
        viewModel.onLoadFailureEvent.observe(viewLifecycleOwner) {
            makeToast(resources.getString(R.string.failed))
        }
        viewModel.onLoadSuccessEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                setLoadedNote(it)
            }
            makeToast(resources.getString(R.string.successfully_download))
        }
        viewModel.onShowProgressBarEvent.observe(viewLifecycleOwner) {
            showProgressBar()
        }
        viewModel.onHideProgressBarEvent.observe(viewLifecycleOwner) {
            hideProgressBar()
        }
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