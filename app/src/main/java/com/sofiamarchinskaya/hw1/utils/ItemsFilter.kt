package com.sofiamarchinskaya.hw1.utils

import com.sofiamarchinskaya.hw1.models.entity.Note

class ItemsFilter : QueryFilter<Note> {
    override fun filter(query: String, list: List<Note>?): List<Note>? =
        list?.filter {
            it.title?.contains(query, ignoreCase = true) ?: false
                    || it.body?.contains(query, ignoreCase = true) ?: false
        }
}