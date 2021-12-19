package com.sofiamarchinskaya.hw1.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SaveDialogFragment(private var onPositiveClick: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Сохранение?")
                .setMessage("Сохранить изменения?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да", null)
        }
            .create()

    }

    
}
