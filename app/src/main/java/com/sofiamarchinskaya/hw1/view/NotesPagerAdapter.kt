package com.sofiamarchinskaya.hw1.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofiamarchinskaya.hw1.models.entity.Note

class NotesPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onSave: (Long) -> Unit
) : FragmentStateAdapter(fragmentActivity) {
    private var list: List<Note> = ArrayList()

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment =
        NoteInfoFragment.newInstance(list[position]).apply {
            setOnSave(onSave)
        }

    fun update(list: List<Note>) {
        this.list = list
        notifyDataSetChanged()
    }
}