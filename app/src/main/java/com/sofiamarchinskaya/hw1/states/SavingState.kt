package com.sofiamarchinskaya.hw1.states

class SavingState(val state: States)

enum class States {
    SAVING, SAVED, ERROR, ALLOWED, NOTHING
}