package com.sofiamarchinskaya.hw1.utils

interface QueryFilter<T> {

    fun filter(query: String, list: List<T>?): List<T>?
}