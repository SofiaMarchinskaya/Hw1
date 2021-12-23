package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NoteInfoPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NoteInfoPresenter
import com.sofiamarchinskaya.hw1.view.framework.NoteInfoView

/**
 * Фрагмент для отображения деталей о заметке
 */
class NoteInfoFragment : Fragment(), NoteInfoView {

    private lateinit var title: TextView
    private lateinit var text: TextView
    private var noteId = Constants.INVALID_ID
    private var isNewNote = false
    private lateinit var presenter: NoteInfoPresenter
    private var isSaveDialogOpen = false

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
        noteId = arguments?.getLong(Constants.ID) ?: Constants.INVALID_ID
        presenter = NoteInfoPresenterImpl(this@NoteInfoFragment)
        if (arguments == null) {
            activity?.invalidateOptionsMenu()
            isNewNote = true
        }
        if (savedInstanceState?.getBoolean(Constants.DIALOG_STATE) == true) {
            createSaveDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (isNewNote)
            inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                presenter.checkNote(title.text.toString(), text.text.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putBoolean(Constants.DIALOG_STATE, isSaveDialogOpen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onSaveAllowed() {
        createSaveDialog()
    }

    override fun onSaveDisabled() {
        Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
    }

    override fun onSuccessfullySaved() {
        Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    private fun createSaveDialog() {
        isSaveDialogOpen = true
        val dialogFragment = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.dialog_title))
            setMessage(getString(R.string.dialog_message))
            setNegativeButton(
                getString(R.string.dialog_negative),
                { _, _ -> isSaveDialogOpen = false })
            setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                presenter.onSaveNote(
                    title.text.toString(),
                    text.text.toString(),
                    noteId
                )
                isSaveDialogOpen = false
            }
            create()
        }
        activity?.supportFragmentManager?.let { dialogFragment.show() }
    }

    companion object {
        fun newInstance(note: Note): NoteInfoFragment {
            val fragment = NoteInfoFragment()
            fragment.arguments = bundleOf(
                Constants.TITLE to note.title,
                Constants.TEXT to note.body,
                Constants.ID to note.id
            )
            return fragment
        }
    }
}
