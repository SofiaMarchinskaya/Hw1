package com.sofiamarchinskaya.hw1.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.sofiamarchinskaya.hw1.*
import com.sofiamarchinskaya.hw1.presenters.NoteInfoPresenter

class NoteInfoFragment : Fragment(), NoteInfo {
    private val presenter: NoteInfoPresenter = NoteInfoPresenterImpl(this, SaveModelImpl())
    private lateinit var title: TextView
    private lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_info, container, false)
        title = view.findViewById(R.id.title)
        text = view.findViewById(R.id.text)
        title.text = arguments?.getString(Constants.TITLE)
        text.text = arguments?.getString(Constants.TEXT)
        return view
    }

    override fun onSaveComplete() {
        Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    override fun onFailed() {
        Toast.makeText(requireContext(), getString(R.string.no_content), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


}