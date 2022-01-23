package com.sofiamarchinskaya.hw1.states

class SavingState(val state: States)

enum class States {
    SAVED, ERROR, ALLOWED, NOTHING
}