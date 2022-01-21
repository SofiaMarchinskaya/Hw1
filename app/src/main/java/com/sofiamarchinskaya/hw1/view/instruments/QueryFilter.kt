package com.sofiamarchinskaya.hw1.view.instruments

interface QueryFilter<T> {
    fun filter(query: String?, list: List<T>?): List<T>?
}