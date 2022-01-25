package com.sofiamarchinskaya.hw1.states

import com.sofiamarchinskaya.hw1.models.entity.Note

class ListItemState(val state: ListItemStates, val note: Note? = null)

enum class ListItemStates {
    OnClicked, NotClicked
}