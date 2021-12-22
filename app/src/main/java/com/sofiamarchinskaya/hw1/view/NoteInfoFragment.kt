package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NoteInfoViewModel
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States

/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var text: TextView
    private val viewModel by lazy { ViewModelProvider(this)[NoteInfoViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_note_info, container, false).apply {
        title = findViewById(R.id.title)
        text = findViewById(R.id.text)
        title.text = arguments?.getString(Constants.TITLE)
        text.text = arguments?.getString(Constants.TEXT)
        viewModel.noteId = arguments?.getLong(Constants.ID) ?: Constants.INVALID_ID
        if (arguments == null) {
            activity?.invalidateOptionsMenu()
            viewModel.isNewNote = true
        }
        viewModel.savingState.observe(this@NoteInfoFragment) {
            when (it.state) {
                States.SAVING -> {}
                States.SAVED -> onSuccessfullySaved()
                States.ERROR -> onSaveDisabled()
                States.ALLOWED -> onSaveAllowed()
                States.NOTHING -> {}//Nothing
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (viewModel.isNewNote)
            inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                viewModel.checkNote(title.text.toString(), text.text.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onSaveAllowed() {
        createSaveDialog()
    }

    fun onSaveDisabled() {
        Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
    }

    fun onSuccessfullySaved() {
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
                viewModel.onSaveNote(
                    title.text.toString(),
                    text.text.toString()
                )
            }
            create()
        }
        activity?.supportFragmentManager?.let { dialogFragment.show() }
    }

    companion object {
        fun newInstance(note: Note): NoteInfoFragment {
            val args = bundleOf(
                Constants.TITLE to note.title,
                Constants.TEXT to note.body,
                Constants.ID to note.id
            )
            val fragment = NoteInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
