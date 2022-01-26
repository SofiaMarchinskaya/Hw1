package com.sofiamarchinskaya.hw1.states

import com.sofiamarchinskaya.hw1.models.entity.Note

class JsonLoadingState(val state: JsonLoadingStates, val note: Note? = null)

enum class JsonLoadingStates {
    SUCCESS, FAILED, FINISH,LOADING
}